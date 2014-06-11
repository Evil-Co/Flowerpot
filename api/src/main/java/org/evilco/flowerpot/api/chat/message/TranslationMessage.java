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

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a translatable message.
 */
public class TranslationMessage extends BaseChatMessage {

	/**
	 * Stores the translation name.
	 */
	@Getter
	@Setter
	protected String translate = null;

	/**
	 * Stores the translation arguments.
	 */
	@Getter
	@Setter
	protected List<BaseChatMessage> with = null;

	/**
	 * Constructs a new TranslationMessage instance.
	 * @param translate The translation string.
	 * @param with The translation arguments.
	 */
	public TranslationMessage (@NonNull String translate, @NonNull List<BaseChatMessage> with) {
		this.setTranslate (translate);
		this.setWith (with);
	}

	/**
	 * Constructs a new TranslationMessage instance.
	 * @param translate The translation string.
	 */
	public TranslationMessage (String translate) {
		this (translate, new ArrayList<BaseChatMessage> ());
	}

	/**
	 * Constructs a new TranslationMessage instance.
	 * @param message The message to copy.
	 */
	public TranslationMessage (TranslationMessage message) {
		super (message);

		this.setTranslate (message.getTranslate ());

		// copy children
		for (BaseChatMessage child : message.getWith ()) {
			this.addWith (child.copy ());
		}

		// normalize
		this.normalize ();
	}

	/**
	 * Adds a new translation argument.
	 * @param message The message to add.
	 */
	public void addWith (BaseChatMessage message) {
		if (this.with == null) this.with = new ArrayList<BaseChatMessage> ();
		this.with.add (message);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void normalize () {
		super.normalize ();

		// skip execution
		if (this.with == null) return;

		// fix parents
		for (BaseChatMessage message : this.with) {
			message.setParent (this);
		}
	}
}