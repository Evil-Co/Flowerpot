package com.evilco.flowerpot.proxy.protocol.http;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface HttpClientCallback {

	/**
	 * Callback for failures.
	 * @param responseCode
	 */
	public void error (int responseCode);

	/**
	 * Callback for successfully finished HTTP actions.
	 * @param content
	 */
	public void finish (String content);
}
