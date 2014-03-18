package com.evilco.flowerpot.proxy.authentication.yggdrasil;

import com.evilco.flowerpot.proxy.FlowerpotServer;
import com.evilco.flowerpot.proxy.authentication.AbstractTrustManager;

import java.io.IOException;
import java.security.cert.CertificateException;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class YggdrasilAuthenticationTrustManager extends AbstractTrustManager {

	/**
	 * Constructs a new YggdrasilAuthenticationTrustManager.
	 * @throws CertificateException
	 * @throws IOException
	 */
	public YggdrasilAuthenticationTrustManager () throws CertificateException, IOException {
		super ();

		// add built-in certificates
		this.addRootCertificate (FlowerpotServer.class.getResourceAsStream ("/certificates/yggdrasil.crt"));
	}


}
