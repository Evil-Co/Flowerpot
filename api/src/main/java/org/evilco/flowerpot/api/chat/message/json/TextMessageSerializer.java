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

package org.evilco.flowerpot.api.chat.message.json;

import com.google.gson.*;
import org.evilco.flowerpot.api.chat.message.TextMessage;

import java.lang.reflect.Type;

/**
 * Provides a serialization adapter for text based messages.
 */
public class TextMessageSerializer extends BaseChatMessageSerializer<TextMessage> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TextMessage deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		// de-serialize base message
		TextMessage message = super.deserialize (json, typeOfT, context);

		// get object
		JsonObject object = json.getAsJsonObject ();

		// read properties
		if (object.has ("text")) message.setText (object.get ("text").getAsString ());

		// return finished object
		return message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JsonElement serialize (TextMessage src, Type typeOfSrc, JsonSerializationContext context) {
		// get base serialization
		JsonObject object = ((JsonObject) super.serialize (src, typeOfSrc, context));

		// append messages
		object.add ("text", context.serialize (src.getText ()));

		// return finished object
		return object;
	}
}