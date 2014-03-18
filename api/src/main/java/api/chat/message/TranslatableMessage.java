package api.chat.message;

import java.util.ArrayList;
import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class TranslatableMessage extends BaseMessage {

	/**
	 * Stores the translation name.
	 */
	protected String translate;

	/**
	 * Stores the translation arguments.
	 */
	protected List<BaseMessage> with = new ArrayList<> ();

	/**
	 * Constructs a new TranslatableMessage.
	 */
	public TranslatableMessage () {
		super ();
	}

	/**
	 * Constructs a new TranslatableMessage.
	 * @param name
	 */
	public TranslatableMessage (String name) {
		super ();
		this.translate = name;
	}

	/**
	 * Constructs a new TranslatableMessage.
	 * @param name
	 * @param with
	 */
	public TranslatableMessage (String name, List<BaseMessage> with) {
		this (name);
		this.with = with;
	}

	/**
	 * Adds a new argument.
	 * @param message
	 */
	public void addArgument (BaseMessage message) {
		this.with.add (message);
		message.setParent (this);
	}

	/**
	 * Returns the translation name.
	 * @return
	 */
	public String getName () {
		return this.translate;
	}

	/**
	 * Returns the translation arguments.
	 * @return
	 */
	public List<BaseMessage> getWith () {
		return this.with;
	}

	/**
	 * Sets a new name.
	 * @param name
	 */
	public void setName (String name) {
		this.translate = name;
	}

	/**
	 * Sets a new list of arguments.
	 * @param with
	 */
	public void setWith (List<BaseMessage> with) {
		this.with = with;
	}
}