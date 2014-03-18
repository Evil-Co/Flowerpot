package com.evilco.flowerpot.proxy.authentication;

import org.apache.commons.io.IOUtils;

import javax.net.ssl.X509TrustManager;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class AbstractTrustManager implements X509TrustManager {

	/**
	 * Stores all intermediate certificates.
	 */
	protected final Set<X509Certificate> intermediateCertificates = new HashSet<> ();

	/**
	 * Stores all registered root certificates.
	 */
	protected final Set<X509Certificate> rootCertificates = new HashSet<> ();

	/**
	 * Constructs a new AbstractTrustManager.
	 */
	public AbstractTrustManager () {
		super ();
	}

	/**
	 * Adds a new intermediate certificate.
	 * @param certificate
	 */
	public void addIntermediateCertificate (X509Certificate certificate) {
		this.intermediateCertificates.add (certificate);
	}

	/**
	 * Adds all intermediate certificates from a file.
	 * @param inputStream
	 * @throws java.security.cert.CertificateException
	 * @throws java.io.IOException
	 */
	public void addIntermediateCertificate (InputStream inputStream) throws CertificateException, IOException {
		for (X509Certificate certificate : this.decodeCertificateStream (inputStream)) {
			this.addIntermediateCertificate (certificate);
		}
	}

	/**
	 * Adds a new root certificate.
	 * @param certificate
	 */
	public void addRootCertificate (X509Certificate certificate) {
		this.rootCertificates.add (certificate);
	}

	/**
	 * Adds all root certificates from a file.
	 * @param inputStream
	 * @throws CertificateException
	 * @throws IOException
	 */
	public void addRootCertificate (InputStream inputStream) throws CertificateException, IOException {
		for (X509Certificate certificate : this.decodeCertificateStream (inputStream)) {
			this.addRootCertificate (certificate);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkClientTrusted (X509Certificate[] x509Certificates, String s) throws CertificateException {
		throw new UnsupportedOperationException ("Client certificates cannot be verified with this TrustManager implementation");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkServerTrusted (X509Certificate[] x509Certificates, String s) throws CertificateException {
		// check general validity
		for (X509Certificate certificate : x509Certificates) {
			// verify certificate validity
			certificate.checkValidity ();

			// verify extensions
			if (certificate.hasUnsupportedCriticalExtension ()) throw new CertificateException ("Critical extension not supported by current platform");
		}

		// verify chain
		try {
			// create selector
			X509CertSelector certSelector = new X509CertSelector ();

			// set main certificate
			certSelector.setCertificate (x509Certificates[0]);

			// create trust anchor list
			Set<TrustAnchor> anchorSet = new HashSet<> ();

			for (X509Certificate certificate : this.rootCertificates) {
				anchorSet.add (new TrustAnchor (certificate, null));
			}

			// create verification parameters
			PKIXBuilderParameters builderParameters = new PKIXBuilderParameters (anchorSet, certSelector);

			// enable revocation checks
			builderParameters.setRevocationEnabled (true);

			// add intermediate certificates
			builderParameters.addCertStore (CertStore.getInstance ("Collection", new CollectionCertStoreParameters (intermediateCertificates)));
			builderParameters.addCertStore (CertStore.getInstance ("Collection", new CollectionCertStoreParameters (Arrays.asList (x509Certificates))));

			// create path builder
			CertPathBuilder certPathBuilder = CertPathBuilder.getInstance ("PKIX");
			certPathBuilder.build (builderParameters);
		} catch (InvalidAlgorithmParameterException ex) {
			throw new CertificateException ("Could not validate certificate chain: Unsupported platform");
		} catch (NoSuchAlgorithmException ex) {
			throw new CertificateException ("Could not validate certificate chain: Unsupported platform");
		} catch (CertPathBuilderException ex) {
			throw new CertificateException (ex.getMessage (), ex);
		}
	}

	/**
	 * Decodes all certificates found in an input stream.
	 * @param inputStream
	 * @return
	 * @throws CertificateException
	 * @throws IOException
	 */
	protected Set<X509Certificate> decodeCertificateStream (InputStream inputStream) throws CertificateException, IOException {
		Set<X509Certificate> certificates = new HashSet<> ();

		try {
			// create buffered input stream
			BufferedInputStream bufferedInputStream = new BufferedInputStream (inputStream);

			// create certificate factory
			CertificateFactory certificateFactory = CertificateFactory.getInstance ("x.509");

			// import certificate
			while (bufferedInputStream.available () > 0) {
				certificates.add (((X509Certificate) certificateFactory.generateCertificate (bufferedInputStream)));
			}
		} finally {
			IOUtils.closeQuietly (inputStream);
		}

		return certificates;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public X509Certificate[] getAcceptedIssuers () {
		return new X509Certificate[0];
	}
}
