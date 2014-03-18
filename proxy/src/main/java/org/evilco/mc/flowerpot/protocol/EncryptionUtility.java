package org.evilco.mc.flowerpot.protocol;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class EncryptionUtility {

	/**
	 * Defines the algorithm used for actual data encryption.
	 */
	public static final String ALGORITHM_ENCRYPTION = "AES";

	/**
	 * Defines the encryption algorithm settings.
	 */
	public static final String ALGORITHM_ENCRYPTION_SETTINGS = "/CFB8/NoPadding";

	/**
	 * Defines the algorithm to use for key exchange.
	 */
	public static final String ALGORITHM_KEY_EXCHANGE = "RSA";

	/**
	 * Defines the key exchange algorithm settings.
	 */
	public static final String ALGORITHM_KEY_EXCHANGE_SETTINGS = "";

	/**
	 * Defines the session hash algorithm.
	 */
	public static final String ALGORITHM_SESSION_HASH = "SHA-1";

	/**
	 * Decrypts data with a shared key.
	 * @param key
	 * @param data
	 * @return
	 */
	public static byte[] decryptData (SecretKey key, byte[] data) {
		try {
			// get cipher
			Cipher cipher = getEncryptionCipher ();

			// initialize cipher
			cipher.init (Cipher.DECRYPT_MODE, key);

			// decrypt data
			return cipher.doFinal (data);
		} catch (InvalidKeyException ex) {
			return null;
		} catch (IllegalBlockSizeException ex) {
			return null;
		} catch (BadPaddingException ex) {
			return null;
		}
	}

	/**
	 * Encrypts data with a shared key.
	 * @param key
	 * @param data
	 * @return
	 */
	public static byte[] encryptData (SecretKey key, byte[] data) {
		try {
			// get cipher
			Cipher cipher = getEncryptionCipher ();

			// initialize cipher
			cipher.init (Cipher.ENCRYPT_MODE, key);

			// encrypt data
			return cipher.doFinal (data);
		} catch (InvalidKeyException ex) {
			return null;
		} catch (IllegalBlockSizeException ex) {
			return null;
		} catch (BadPaddingException ex) {
			return null;
		}
	}

	/**
	 * Encrypts a shard key.
	 * @param key
	 * @param sharedKey
	 * @return
	 */
	public static byte[] encryptKey (Key key, byte[] sharedKey) {
		try {
			// get cipher
			Cipher cipher = getKeyExchangeCipher ();

			// initialize cipher
			cipher.init (Cipher.ENCRYPT_MODE, key);

			// encrypt data
			return cipher.doFinal (sharedKey);
		} catch (InvalidKeyException ex) {
			return null;
		} catch (IllegalBlockSizeException ex) {
			return null;
		} catch (BadPaddingException ex) {
			return null;
		}
	}

	/**
	 * Decrypts a shared key.
	 * @param key
	 * @param sharedKey
	 * @return
	 */
	public static byte[] decryptKey (Key key, byte[] sharedKey) {
		try {
			// get cipher
			Cipher cipher = getKeyExchangeCipher ();

			// initialize cipher
			cipher.init (Cipher.DECRYPT_MODE, key);

			// decrypt data
			return cipher.doFinal (sharedKey);
		} catch (InvalidKeyException ex) {
			return null;
		} catch (IllegalBlockSizeException ex) {
			return null;
		} catch (BadPaddingException ex) {
			return null;
		}
	}

	/**
	 * Returns the complete cipher name for the encryption algorithm.
	 * @return
	 */
	protected static String getEncryptionCipherName () {
		return ALGORITHM_ENCRYPTION + ALGORITHM_ENCRYPTION_SETTINGS;
	}

	/**
	 * Returns the complete cipher name for the key exchange algorithm.
	 * @return
	 */
	protected static String getKeyExchangeCiperName () {
		return ALGORITHM_KEY_EXCHANGE + ALGORITHM_KEY_EXCHANGE_SETTINGS;
	}

	/**
	 * Returns the encryption cipher.
	 * @return
	 */
	public static Cipher getEncryptionCipher () {
		try {
			return Cipher.getInstance (getEncryptionCipherName ());
		} catch (NoSuchAlgorithmException ex) {
			return null;
		} catch (NoSuchPaddingException ex) {
			return null;
		}
	}

	/**
	 * Returns the key exchange cipher.
	 * @return
	 */
	public static Cipher getKeyExchangeCipher () {
		try {
			return Cipher.getInstance (getKeyExchangeCiperName ());
		} catch (NoSuchAlgorithmException ex) {
			return null;
		} catch (NoSuchPaddingException ex) {
			return null;
		}
	}
}
