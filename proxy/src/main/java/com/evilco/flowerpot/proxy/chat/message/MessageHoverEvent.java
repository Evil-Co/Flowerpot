package com.evilco.flowerpot.proxy.chat.message;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MessageHoverEvent {

	/**
	 * Stores the hover action.
	 */
	protected MessageHoverAction action;

	/**
	 * Stores the value.
	 */
	protected BaseMessage[] value;

	/**
	 * Constructs a new MessageHoverEvent.
	 * @param action
	 * @param value
	 */
	public MessageHoverEvent (MessageHoverAction action, BaseMessage[] value) {
		this.action = action;
		this.value = value;
	}

	/**
	 * Returns the hover action.
	 * @return
	 */
	public MessageHoverAction getAction () {
		return this.action;
	}

	/**
	 * Returns the hover value.
	 * @return
	 */
	public BaseMessage[] getValue () {
		return this.value;
	}

	/**
	 * Sets a new action.
	 * @param action
	 */
	public void setAction (MessageHoverAction action) {
		this.action = action;
	}

	/**
	 * Sets a new value array.
	 * @param value
	 */
	public void setValue (BaseMessage[] value) {
		this.value = value;
	}
}
