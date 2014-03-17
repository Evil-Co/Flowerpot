package org.evilco.mc.flowerpot;

import com.evilco.event.EventManager;
import com.google.common.io.BaseEncoding;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.flowerpot.authentication.IAuthenticationService;
import org.evilco.mc.flowerpot.authentication.yggdrasil.YggdrasilAuthenticationService;
import org.evilco.mc.flowerpot.configuration.IProxyConfiguration;
import org.evilco.mc.flowerpot.configuration.xml.XmlProxyConfiguration;
import org.evilco.mc.flowerpot.metrics.IMetricsService;
import org.evilco.mc.flowerpot.metrics.MetricsManager;
import org.evilco.mc.flowerpot.metrics.mcstats.MCStatsMetricsService;
import org.evilco.mc.flowerpot.protocol.EncryptionUtility;
import org.evilco.mc.flowerpot.protocol.packet.event.ClientPacketHandler;
import org.evilco.mc.flowerpot.protocol.packet.event.PacketManager;
import org.evilco.mc.flowerpot.protocol.packet.event.ServerPacketHandler;
import org.evilco.mc.flowerpot.server.MinecraftServer;
import org.evilco.mc.flowerpot.server.listener.ServerListener;
import org.evilco.mc.flowerpot.user.UserManager;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class FlowerpotServer {

	/**
	 * Stores the build ID.
	 */
	public static final String BUILD;

	/**
	 * Defines the default server encryption key size.
	 */
	public static final int SERVER_ENCRYPTION_KEY_SIZE = 1024;

	/**
	 * Stores the application logger.
	 */
	protected static final Logger logger = LogManager.getFormatterLogger (FlowerpotServer.class);

	/**
	 * Defines the Minecraft version.
	 */
	public static final String MINECRAFT_VERSION;

	/**
	 * Defines the Minecraft version template.
	 */
	public static final String MINECRAFT_VERSION_TEMPLATE = "Flowerpot %s (%s)";

	/**
	 * Defines the server version.
	 */
	public static final String VERSION;

	/**
	 * Stores the selected authentication service.
	 */
	protected IAuthenticationService authenticationService;

	/**
	 * Stores the event manager for the proxy.
	 */
	protected EventManager eventManager;

	/**
	 * Stores the currently active metrics manager.
	 */
	protected MetricsManager metricsManager;

	/**
	 * Stores the PacketManager instance.
	 */
	protected PacketManager packetManager;

	/**
	 * Stores the translation bundle.
	 */
	public ResourceBundle translation;

	/**
	 * Static Initialization
	 */
	static {
		String version = "git-Flowerpot-SNAPSHOT";
		String build = "dirty";
		String mcVersion = "SNAPSHOT";

		try {
			JarFile jar = new JarFile (FlowerpotServer.class.getProtectionDomain ().getCodeSource ().getLocation ().getFile ());

			Attributes attributes = jar.getManifest ().getMainAttributes ();
			build = attributes.getValue ("Implementation-Build");
			version = "git-Flowerpot-" + build;
			mcVersion = attributes.getValue ("Implementation-MinecraftVersion");
		} catch (IOException ex) { }

		BUILD = build;
		MINECRAFT_VERSION = mcVersion;
		VERSION = version;
	}

	/**
	 * Stores the current proxy configuration.
	 */
	protected IProxyConfiguration configuration;

	/**
	 * Stores the current proxy instance.
	 */
	protected static FlowerpotServer instance = null;

	/**
	 * Indicates whether the server is still alive.
	 */
	protected boolean isAlive = false;

	/**
	 * Stores the encryption key.
	 */
	protected KeyPair serverKeyPair = null;

	/**
	 * Stores the worker thread group.
	 */
	protected EventLoopGroup threadGroupWorker;

	/**
	 * Stores the server user manager.
	 */
	protected UserManager userManager;

	/**
	 * Constructs a new FlowerpotServer.
	 * @param configuration
	 */
	public FlowerpotServer (IProxyConfiguration configuration) {
		// log startup
		logger.info ("Flowerpot " + VERSION + " (git-" + BUILD + ")");
		logger.info ("Copyright (C) 2014 Evil-Co <http://www.evil-co.org>");
		logger.info ("---------------------------------------------------");

		// generate encryption key
		this.generateEncryptionKeyPair ();
		this.loadTranslation ();

		// store arguments
		this.authenticationService = new YggdrasilAuthenticationService ();
		this.configuration = configuration;
		this.packetManager = new PacketManager ();
		this.eventManager = new EventManager ();
		this.userManager = new UserManager ();

		// create thread groups
		this.threadGroupWorker = new NioEventLoopGroup ();

		// register default handlers
		this.packetManager.registerHandler (new ClientPacketHandler ());
		this.packetManager.registerHandler (new ServerPacketHandler ());

		// enable metrics
		MCStatsMetricsService metricsService = new MCStatsMetricsService ();
		metricsService.setServerDetail ("Flowerpot", VERSION);
		this.metricsManager = new MetricsManager (metricsService);
	}

	/**
	 * Starts the server.
	 * @throws Exception
	 */
	public void bind () throws Exception {
		this.isAlive = true;

		// log
		logger.info ("Binding " + this.configuration.getListenerList ().size () + " listeners ...");

		// bind listeners
		for (ServerListener listener : this.configuration.getListenerList ()) {
			listener.bind (this.threadGroupWorker);
		}
	}

	/**
	 * Generates a new encryption key pair.
	 */
	protected void generateEncryptionKeyPair () {
		logger.info ("Generating a new " + SERVER_ENCRYPTION_KEY_SIZE + " bit encryption key. This might take a while ...");

		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance (EncryptionUtility.ALGORITHM_KEY_EXCHANGE);
			generator.initialize (SERVER_ENCRYPTION_KEY_SIZE);
			this.serverKeyPair = generator.generateKeyPair ();
		} catch (Exception ex) {
			logger.fatal ("Could not generate the encryption key pair. This could cause connectivity issues!", ex);
		}

		logger.info ("Finished generating the encryption key.");
	}

	/**
	 * Returns the current authentication service.
	 * @return
	 */
	public IAuthenticationService getAuthenticationService () {
		return this.authenticationService;
	}

	/**
	 * Returns the current configuration.
	 * @return
	 */
	public IProxyConfiguration getConfiguration () {
		return this.configuration;
	}

	/**
	 * Returns an encoded server icon.
	 * @return
	 */
	public String getEncodedServerIcon () {
		return "data:image/png;base64," + BaseEncoding.base64 ().encode (this.configuration.getServerIcon ());
	}

	/**
	 * Returns the proxy event manager.
	 * @return
	 */
	public EventManager getEventManager () {
		return this.eventManager;
	}

	/**
	 * Returns the server instance.
	 * @return
	 */
	public static FlowerpotServer getInstance () {
		return instance;
	}

	/**
	 * Returns the current encryption key pair.
	 * @return
	 */
	public KeyPair getKeyPair () {
		return this.serverKeyPair;
	}

	/**
	 * Returns the application logger.
	 * @return
	 */
	public static Logger getLogger () {
		return logger;
	}

	/**
	 * Returns the Minecraft version string.
	 * @return
	 */
	public static String getMinecraftVersionString () {
		return String.format (MINECRAFT_VERSION_TEMPLATE, MINECRAFT_VERSION, BUILD);
	}

	/**
	 * Returns the current packet manager.
	 * @return
	 */
	public PacketManager getPacketManager () {
		return this.packetManager;
	}

	/**
	 * Returns the translation.
	 * @param name
	 * @param arguments
	 * @return
	 */
	public String getTranslation (String name, Object... arguments) {
		try {
			return MessageFormat.format (this.translation.getString (name), arguments);
		} catch (MissingResourceException ex) { }

		return name;
	}

	/**
	 * Loads the server translation.
	 */
	protected void loadTranslation () {
		try {
			this.translation = ResourceBundle.getBundle ("messages");
		} catch (Exception ex) {
			this.translation = ResourceBundle.getBundle ("messages", Locale.ENGLISH);
		}
	}

	/**
	 * Returns the server user manager.
	 * @return
	 */
	public UserManager getUserManager () {
		return this.userManager;
	}

	/**
	 * Main Entry Point
	 * @param arguments
	 */
	public static void main (String[] arguments) throws Exception {
		// TODO: Add proper implementation here
		instance = new FlowerpotServer (XmlProxyConfiguration.newInstance (new File ("flowerpot.xml")));
		instance.bind ();

		while (true) {
			try {
				Thread.sleep (500);
			} catch (Exception ex) { }
		}
	}
}
