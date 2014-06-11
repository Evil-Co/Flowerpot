/*
 * Copyright (C) 2014 Johannes Donath <johannesd@evil-co.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.evilco.flowerpot.api.chat.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.evilco.flowerpot.api.chat.message.interaction.MessageClickEvent;
import org.evilco.flowerpot.api.chat.message.interaction.MessageHoverEvent;
import org.evilco.flowerpot.api.chat.message.json.TextMessageSerializer;
import org.evilco.flowerpot.api.chat.message.json.TranslationMessageSerializer;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a chat message.
 */
public abstract class BaseChatMessage {

	/**
	 * Indicates whether the message will be displayed in bold.
	 */
	@Setter
	protected Boolean bold = null;

	/**
	 * Defines the action performed when the message is clicked.
	 */
	@Setter
	protected MessageClickEvent clickEvent = null;

	/**
	 * Defines the color the message will be displayed in.
	 */
	@Setter
	protected ChatColor color = null;

	/**
	 * Stores a list of messages which will be appended to the message.
	 */
	@Getter
	@Setter
	protected List<BaseChatMessage> extra = null;

	/**
	 * Stores the gson instance used to (de-)serialize messages.
	 */
	protected static final Gson gson;

	/**
	 * Defines the action performed when a user hovers over the message.
	 */
	@Setter
	protected MessageHoverEvent hoverEvent = null;

	/**
	 * Indicates whether the message will be displayed in italic.
	 */
	@Setter
	protected Boolean italic = null;

	/**
	 * Indicates whether the message will be displayed obfuscated (unreadable).
	 */
	@Setter
	protected Boolean obfuscated = null;

	/**
	 * Stores the parent message.
	 */
	@Getter
	@Setter
	protected transient BaseChatMessage parent = null;

	/**
	 * Indicates whether the message will be displayed striked through.
	 */
	@Setter
	protected Boolean strikethrough = null;

	/**
	 * Indicates whether the message will be displayed with an underline.
	 */
	@Setter
	protected Boolean underlined = null;

	/**
	 * Static Initialization
	 */
	static {
		// create builder
		GsonBuilder builder = new GsonBuilder ();

		// add type adapters
		builder.registerTypeAdapter (TextMessage.class, new TextMessageSerializer ());
		builder.registerTypeAdapter (TranslationMessage.class, new TranslationMessageSerializer ());

		// create Gson instance
		gson = builder.create ();
	}

	/**
	 * Constructs a new BaseChatMessage instance.
	 */
	public BaseChatMessage () { }

	/**
	 * Copies a BaseChatMessage instance.
	 * @param message The original message.
	 */
	public BaseChatMessage (BaseChatMessage message) {
		this.setBold (message.getBold ());
		this.setClickEvent (message.getClickEvent ());
		this.setHoverEvent (message.getHoverEvent ());
		this.setItalic (message.getItalic ());
		this.setObfuscated (message.getObfuscated ());
		this.setUnderlined (message.getUnderlined ());
		this.setStrikethrough (message.getStrikethrough ());

		// copy extra
		for (BaseChatMessage child : message.getExtra ()) {
			this.addExtra (child.copy ());
		}

		// normalize
		this.normalize ();
	}

	/**
	 * Adds a new message to the stack.
	 * @param message The message.
	 */
	public void addExtra (BaseChatMessage message) {
		// create array
		if (this.extra == null) this.extra = new ArrayList<BaseChatMessage> ();

		// append message
		this.extra.add (message);

		// set parent
		message.setParent (this);
	}

	/**
	 * Copies the message.
	 * @return A copied version of the message.
	 */
	@SneakyThrows
	public BaseChatMessage copy () {
		try {
			// get constructor
			Constructor<? extends BaseChatMessage> constructor = this.getClass ().getDeclaredConstructor (this.getClass ());

			// create new instance
			return constructor.newInstance (this);
		} catch (NoSuchMethodException ex) {
			throw new UnsupportedOperationException ("The message implementation " + this.getClass ().getName () + " does not have a copy constructor.");
		}
	}

	/**
	 * Checks whether the message will be displayed in bold.
	 * @return True if the style is bold.
	 */
	public Boolean getBold () {
		if (this.parent != null && this.bold == null) return this.parent.getBold ();
		return bold;
	}

	/**
	 * Returns the action to perform when the message is clicked.
	 * @return The action type.
	 */
	public MessageClickEvent getClickEvent () {
		if (this.parent != null && this.clickEvent == null) return this.parent.getClickEvent ();
		return clickEvent;
	}

	/**
	 * Returns the color to display the message in.
	 * @return The message color.
	 */
	public ChatColor getColor () {
		if (this.parent != null && this.color == null) return this.parent.getColor ();
		return color;
	}

	/**
	 * Returns the action to perform when the message is hovered.
	 * @return The action type.
	 */
	public MessageHoverEvent getHoverEvent () {
		if (this.parent != null && this.hoverEvent == null) return this.parent.getHoverEvent ();
		return hoverEvent;
	}

	/**
	 * Checks whether the message will be displayed in italic.
	 * @return True if the style is italic.
	 */
	public Boolean getItalic () {
		if (this.parent != null && this.italic == null) return this.parent.getItalic ();
		return italic;
	}

	/**
	 * Checks whether the message will be displayed obfuscated.
	 * @return True if the style is obfuscated.
	 */
	public Boolean getObfuscated () {
		if (this.parent != null && this.obfuscated == null) return this.parent.getObfuscated ();
		return obfuscated;
	}

	/**
	 * Checks whether the message will be displayed with an underline.
	 * @return True if the style is underlined.
	 */
	public Boolean getUnderlined () {
		if (this.parent != null && this.underlined == null) return this.parent.getUnderlined ();
		return underlined;
	}

	/**
	 * Checks whether the message will be displayed with a line through.
	 * @return True if the style is striketrhough.
	 */
	public Boolean getStrikethrough () {
		if (this.parent != null && this.strikethrough == null) return this.parent.getStrikethrough ();
		return strikethrough;
	}

	/**
	 * Normalizes the message structure.
	 */
	public void normalize () {
		// skip normalization
		if (this.extra == null) return;

		// normalize
		for (BaseChatMessage message : this.extra) {
			message.setParent (this);
		}
	}

	/**
	 * Serializes a message.
	 * @return The json version of the message.
	 */
	public String serialize () {
		return gson.toJson (this);
	}

	/**
	 * Un-Serializes a message.
	 * @param json The json message.
	 * @param type The message type.
	 * @param <T> The message type.
	 * @return The original message.
	 */
	protected static <T extends BaseChatMessage> T unserialize (String json, Class<T> type) {
		return gson.fromJson (json, type);
	}
}