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

package org.evilco.flowerpot.api.chat.message.interaction;

import lombok.Getter;
import lombok.Setter;
import org.evilco.flowerpot.api.chat.message.BaseChatMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the action performed when clicking the corresponding message.
 */
public class MessageHoverEvent {

	/**
	 * Defines the action type to perform.
	 */
	@Getter
	@Setter
	protected MessageHoverAction action = null;

	/**
	 * Defines the action content.
	 */
	@Getter
	@Setter
	protected List<BaseChatMessage> value = null;

	/**
	 * Constructs a new MessageHoverEvent instance.
	 * @param action The action.
	 * @param value The content.
	 */
	public MessageHoverEvent (MessageHoverAction action, List<BaseChatMessage> value) {
		this.setAction (action);
		this.setValue (value);
	}

	/**
	 * Constructs a new MessageHoverEvent instance.
	 * @param action The action.
	 */
	public MessageHoverEvent (MessageHoverAction action) {
		this (action, new ArrayList<BaseChatMessage> ());
	}
}