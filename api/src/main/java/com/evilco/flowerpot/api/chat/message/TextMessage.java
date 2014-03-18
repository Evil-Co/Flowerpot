package com.evilco.flowerpot.api.chat.message;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class TextMessage extends BaseMessage {

	/**
	 * Stores the text to display.
	 */
	protected String text = null;

	/**
	 * Constructs a new TextMessage.
	 */
	public TextMessage () {
		super ();
	}

	/**
	 * Constructs a new TextMessage.
	 * @param text
	 */
	public TextMessage (String text) {
		super ();
		this.setText (text);
	}

	/**
	 * Returns the current text.
	 * @return
	 */
	public String getText () {
		return this.text;
	}

	/**
	 * Sets a new text.
	 * @param value
	 */
	public void setText (String value) {
		this.text = value;
	}
}
