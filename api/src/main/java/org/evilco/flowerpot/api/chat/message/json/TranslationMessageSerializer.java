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

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.evilco.flowerpot.api.chat.message.BaseChatMessage;
import org.evilco.flowerpot.api.chat.message.TranslationMessage;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Provides a serialization adapter for translations.
 */
public class TranslationMessageSerializer extends BaseChatMessageSerializer<TranslationMessage> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TranslationMessage deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		// de-serialize base message
		TranslationMessage message = super.deserialize (json, typeOfT, context);

		// get object
		JsonObject object = json.getAsJsonObject ();

		// get type tokens
		Type withType = (new TypeToken<List<BaseChatMessage>> () { }).getType ();

		// set properties
		if (object.has ("translate")) message.setTranslate (object.get ("translate").getAsString ());
		if (object.has ("with")) message.setWith (((List<BaseChatMessage>) context.deserialize (object.get ("with"), withType)));

		// return finished object
		return message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JsonElement serialize (TranslationMessage src, Type typeOfSrc, JsonSerializationContext context) {
		// get base message serialization
		JsonObject object  = super.serialize (src, typeOfSrc, context).getAsJsonObject ();

		// append properties
		if (src.getTranslate () != null) object.add ("translate", context.serialize (src.getTranslate ()));
		if (src.getWith () != null) object.add ("with", context.serialize (src.getWith ()));

		// return finished object
		return object;
	}
}