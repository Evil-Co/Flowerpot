package org.evilco.mc.flowerpot.protocol.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {

	/**
	 * Defines the SSL implementation to use.
	 */
	public static final String SSL_IMPLEMENTATION_NAME = "TLS";

	/**
	 * Stores the HTTP callback (if any).
	 */
	protected final HttpClientCallback callback;

	/**
	 * Indicates whether SSL is enabled for this connection.
	 */
	protected final boolean sslEnabled;

	/**
	 * Stores the SSL trust manager.
	 */
	protected final TrustManager trustManager;

	/**
	 * Constructs a new HttpChannelInitializer.
	 * @param callback
	 * @param sslEnabled
	 * @todo Callbacks and SSL should not be passed in the constructor. The initializer should be reusable.
	 */
	public HttpChannelInitializer (HttpClientCallback callback, boolean sslEnabled, TrustManager trustManager) {
		super ();

		this.callback = callback;
		this.sslEnabled = sslEnabled;
		this.trustManager = trustManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initChannel (SocketChannel ch) throws Exception {
		// add timeout handler
		ch.pipeline ().addLast (new ReadTimeoutHandler (SimpleHttpClient.TIMEOUT, TimeUnit.MILLISECONDS));

		// initialize SSL
		if (this.sslEnabled) {
			// get context
			SSLContext sslContext = SSLContext.getInstance (SSL_IMPLEMENTATION_NAME);

			// initialize context
			sslContext.init (null, new TrustManager[] {
				this.trustManager
			}, null);

			// create SSL engine instance
			SSLEngine sslEngine = sslContext.createSSLEngine ();
			sslEngine.setUseClientMode (true);

			// append SSL to handler list
			ch.pipeline ().addLast (new SslHandler (sslEngine));
		}

		// add HTTP codec
		ch.pipeline ().addLast (new HttpClientCodec ());

		// add our handler
		ch.pipeline ().addLast (new HttpClientHandler ());
	}

	/**
	 * Handles HTTP responses.
	 */
	public class HttpClientHandler extends SimpleChannelInboundHandler<HttpObject> {

		/**
		 * Stores the HTTP response.
		 */
		protected StringBuffer buffer = new StringBuffer ();

		/**
		 * Stores the internal logging instance.
		 */
		protected final Logger logger = LogManager.getLogger (HttpClientHandler.class);

		/**
		 * Stores the response code.
		 */
		protected int responseCode = -1;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception {
			// log error
			logger.fatal ("An HTTP request did not finish successfully. An exception occurred while reading the answer.", cause);

			// issue callback
			try {
				callback.error (-1);
			} finally {
				ctx.channel ().close ();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void messageReceived (ChannelHandlerContext ctx, HttpObject msg) throws Exception {
			// handle HTTP headers
			if (msg instanceof HttpResponse) {
				// debug log
				logger.debug ("Received an answer for previous HTTP request.");

				// cast to target
				HttpResponse httpResponse = ((HttpResponse) msg);

				// get response code
				this.responseCode = httpResponse.getStatus ().code ();

				// no content sent?
				if (this.responseCode == HttpResponseStatus.NO_CONTENT.code ()) { // XXX: This is sadly not an enum value
					callback.finish (null);
					logger.debug ("Received an empty HTTP response.");
					return;
				}

				// error?!
				if (this.responseCode != HttpResponseStatus.OK.code ()) {
					callback.error (this.responseCode);
					logger.warn ("The HTTP request did not finish successfully (received status %s instead of expected code %s).", this.responseCode, HttpResponseStatus.OK.code ());
					return;
				}

				// debug log
				logger.debug ("The query was successful. Waiting for contents ...");
			}

			// handle HTTP contents
			if (msg instanceof HttpContent) {
				logger.trace ("Received an HTTP answer part. Processing data ...");

				// cast to target
				HttpContent httpContent = ((HttpContent) msg);

				// append message to buffer
				this.buffer.append (httpContent.content ().toString (Charset.forName (SimpleHttpClient.CHARSET)));

				// end of stream?
				if (msg instanceof LastHttpContent) {
					logger.debug ("Received end of answer.");
					callback.finish (this.buffer.toString ());
				}
			}
		}
	}
}
