package org.evilco.mc.flowerpot.chat.message;

import java.util.ArrayList;
import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class BaseMessage {

	/**
	 * Indicates whether the message is written in bold.
	 */
	protected Boolean bold = null;

	/**
	 * Stores the assigned click event.
	 */
	protected MessageClickEvent clickEvent = null;

	/**
	 * Stores the message color.
	 */
	protected ChatColor color = null;

	/**
	 * Stores child text elements which inherit the text format.
	 */
	protected List<BaseMessage> extra = null;

	/**
	 * Stores the hover event.
	 */
	protected MessageHoverEvent hoverEvent = null;

	/**
	 * Indicates whether the message is written in italic.
	 */
	protected Boolean italic = null;

	/**
	 * Indicates whether the text shows up obfuscated.
	 */
	protected Boolean obfuscated = null;

	/**
	 * Stores the parent message.
	 */
	protected transient BaseMessage parent = null;

	/**
	 * Indicates whether the message has a line through it.
	 */
	protected Boolean strikethrough = null;

	/**
	 * Indicates whether the message has an underline.
	 */
	protected Boolean underlined = null;

	/**
	 * Adds a new child element.
	 * @param value
	 */
	public void addExtra (BaseMessage value) {
		// create list
		if (this.extra == null) this.setExtra (new ArrayList<BaseMessage> ());

		value.setParent (this);
		this.extra.add (value);
	}

	/**
	 * Returns the click event.
	 * @return
	 */
	public MessageClickEvent getClickEvent () {
		if (this.clickEvent == null && this.parent != null) return this.parent.getClickEvent ();
		return this.clickEvent;
	}

	/**
	 * Returns the chat color.
	 * @return
	 */
	public ChatColor getColor () {
		if (this.color == null && this.parent != null) return this.parent.getColor ();
		return this.color;
	}

	/**
	 * Returns the hover event.
	 * @return
	 */
	public MessageHoverEvent getHoverEvent () {
		if (this.hoverEvent == null && this.parent != null) return this.parent.getHoverEvent ();
		return this.hoverEvent;
	}

	/**
	 * Checks whether this message has any formatting applied.
	 * @return
	 */
	public boolean hasFormatting () {
		return (this.bold != null && this.italic != null && this.obfuscated != null && this.strikethrough != null && this.underlined != null);
	}

	/**
	 * Checks whether the element is displayed with a line through or not.
	 * @return
	 */
	public boolean hasStrikethrough () {
		if (this.strikethrough == null && this.parent != null) return this.parent.hasStrikethrough ();
		return this.strikethrough;
	}

	/**
	 * Checks whether the element is displayed with an underline or not.
	 * @return
	 */
	public boolean hasUnderline () {
		if (this.underlined == null && this.parent != null) return this.parent.hasUnderline ();
		return this.underlined;
	}

	/**
	 * Checks whether the element is displayed bold or not.
	 * @return
	 */
	public boolean isBold () {
		if (this.bold == null && this.parent != null) return this.parent.isBold ();
		return this.bold;
	}

	/**
	 * Checks whether the element is displayed italic or not.
	 * @return
	 */
	public boolean isItalic () {
		if (this.italic == null && this.parent != null) return this.parent.isItalic ();
		return this.italic;
	}

	/**
	 * Checks whether the element is displayed obfuscated or not.
	 * @return
	 */
	public boolean isObfuscated () {
		if (this.obfuscated == null && this.parent != null) return this.parent.isObfuscated ();
		return this.obfuscated;
	}

	/**
	 * Sets the message format.
	 * @param value
	 */
	public void setBold (boolean value) {
		this.bold = value;
	}

	/**
	 * Sets a new click event.
	 * @param value
	 */
	public void setClickEvent (MessageClickEvent value) {
		this.clickEvent = value;
	}

	/**
	 * Sets a new message color.
	 * @param value
	 */
	public void setColor (ChatColor value) {
		this.color = value;
	}

	/**
	 * Sets new list of child elements.
	 * @param value
	 */
	public void setExtra (List<BaseMessage> value) {
		// update parents
		for (BaseMessage message : value) {
			message.setParent (this);
		}

		// set value
		this.extra = value;
	}

	/**
	 * Sets a new hover event.
	 * @param value
	 */
	public void setHoverEvent (MessageHoverEvent value) {
		this.hoverEvent = value;
	}

	/**
	 * Sets the message format.
	 * @param value
	 */
	public void setItalic (boolean value) {
		this.italic = value;
	}

	/**
	 * Sets the message format.
	 * @param value
	 */
	public void setObfuscated (boolean value) {
		this.obfuscated = value;
	}

	/**
	 * Sets a new parent message.
	 * @param value
	 */
	public void setParent (BaseMessage value) {
		this.parent = value;
	}

	/**
	 * Sets the message format.
	 * @param value
	 */
	public void setStrikethrough (boolean value) {
		this.strikethrough = value;
	}

	/**
	 * Sets the message format.
	 * @param value
	 */
	public void setUnderlined (boolean value) {
		this.underlined = value;
	}
}
