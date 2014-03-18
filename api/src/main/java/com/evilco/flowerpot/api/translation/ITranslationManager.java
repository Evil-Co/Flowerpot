package com.evilco.flowerpot.api.translation;

import java.util.Locale;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface ITranslationManager {

	/**
	 * Returns a translation string.
	 * @param translation
	 * @param arguments
	 */
	public void get (String translation, Object... arguments);

	/**
	 * Loads a new translation (falling back to English (US) if not available).
	 * @param locale
	 */
	public void load (Locale locale);
}