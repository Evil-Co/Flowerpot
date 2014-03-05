package org.evilco.mc.flowerpot.authentication;

import com.google.gson.Gson;
import io.netty.channel.EventLoop;
import org.evilco.mc.flowerpot.protocol.EncryptionUtility;
import org.evilco.mc.flowerpot.protocol.http.HttpClientCallback;
import org.evilco.mc.flowerpot.protocol.http.SimpleHttpClient;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class YggdrasilAuthenticationService implements IAuthenticationService {

	/**
	 * Defines the authentication URL.
	 */
	public static final String AUTHENTICATION_URL = "https://sessionserver.mojang.com/session/minecraft/hasJoined?username=%s&serverId=%s";

	/**
	 * Stores an internal Gson instance.
	 */
	protected static final Gson gson = new Gson ();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void authenticate (String username, byte[] serverID, byte[] sharedKey, byte[] publicKey, EventLoop eventLoop, final AuthenticationCallback callback) throws Exception {
		// get session hash
		String sessionHash = this.getSessionHash (serverID, sharedKey, publicKey);

		// create query URL
		String authenticationURL = String.format (AUTHENTICATION_URL, username, sessionHash);

		// query server
		SimpleHttpClient.get (authenticationURL, eventLoop, new HttpClientCallback () {

			@Override
			public void error (int responseCode) {
				callback.error ();
			}

			@Override
			public void finish (String content) {
				// decode JSON
				YggdrasilAuthenticationResult result = gson.fromJson (content, YggdrasilAuthenticationResult.class);

				// verify state
				if (result == null) {
					callback.error ();
					return;
				}

				// call callback
				callback.success (result.id);
			}

		}, new YggdrasilAuthenticationTrustManager ());
	}

	/**
	 * Returns the correct session hash.
	 * @param serverID
	 * @param sharedKey
	 * @param publicKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String getSessionHash (byte[] serverID, byte[] sharedKey, byte[] publicKey) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance (EncryptionUtility.ALGORITHM_SESSION_HASH);

		// get temporary byte array
		byte[][] hashArray = new byte[][] {
			serverID,
			sharedKey,
			publicKey
		};

		// create hash
		for (byte[] part : hashArray) {
			messageDigest.update (part);
		}

		try {
			return URLEncoder.encode (new BigInteger (messageDigest.digest ()).toString (16), "UTF-8");
		} catch (Exception ex) {
			return null;
		}
	}
}
