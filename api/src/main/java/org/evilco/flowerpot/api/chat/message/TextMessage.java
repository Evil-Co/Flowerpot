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
import lombok.Setter;

/**
 * Represents a text based chat message.
 */
public class TextMessage extends BaseChatMessage {

	/**
	 * Stores the message content.
	 */
	@Getter
	@Setter
	protected String text = null;

	/**
	 * Constructs a new empty TextMessage instance.
	 */
	public TextMessage () {
		super ();
	}

	/**
	 * Constructs a new TextMessage instance.
	 * @param text The message content.
	 */
	public TextMessage (String text) {
		super ();

		this.setText (text);
	}
}