package org.evilco.mc.flowerpot.chat.message;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MessageClickEvent {

	/**
	 * Defines the click action.
	 */
	protected MessageClickAction action = MessageClickAction.RUN_COMMAND;

	/**
	 * Defines the value.
	 */
	protected String value;

	/**
	 * Constructs a new MessageClickEvent.
	 * @param action
	 * @param value
	 */
	public MessageClickEvent (MessageClickAction action, String value) {
		this.action = action;
		this.value = value;
	}

	/**
	 * Returns the click action.
	 * @return
	 */
	public MessageClickAction getAction () {
		return this.action;
	}

	/**
	 * Returns the value.
	 * @return
	 */
	public String getValue () {
		return this.value;
	}

	/**
	 * Sets a new action.
	 * @param value
	 */
	public void setAction (MessageClickAction value) {
		this.action = value;
	}

	/**
	 * Sets a new value.
	 * @param value
	 */
	public void setValue (String value) {
		this.value = value;
	}
}
