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
import com.google.gson.reflect.TypeToken;
import org.evilco.flowerpot.api.chat.message.BaseChatMessage;
import org.evilco.flowerpot.api.chat.message.ChatColor;
import org.evilco.flowerpot.api.chat.message.interaction.MessageClickEvent;
import org.evilco.flowerpot.api.chat.message.interaction.MessageHoverEvent;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Provides a serialization adapter for messages.
 */
public abstract class BaseChatMessageSerializer<T extends BaseChatMessage> implements JsonSerializer<T>, JsonDeserializer<T> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		// get object
		JsonObject object = json.getAsJsonObject ();

		// create variable
		BaseChatMessage message = null;

		// create instance
		try {
			message = ((Class<?>) typeOfT).asSubclass (BaseChatMessage.class).newInstance ();
		} catch (Exception ex) {
			throw new JsonParseException ("Could not construct the appropriate message instance of type " + ((Class<?>) typeOfT).getName () + ": " + ex.getMessage (), ex);
		}

		// prepare list type token
		Type extraType = (new TypeToken<List<BaseChatMessage>> () { }).getType ();

		// set properties
		if (object.has ("bold")) message.setBold (object.get ("bold").getAsBoolean ());
		if (object.has ("clickEvent")) message.setClickEvent (((MessageClickEvent) context.deserialize (object.get ("clickEvent"), MessageClickEvent.class)));
		if (object.has ("color")) message.setColor (((ChatColor) context.deserialize (object.get ("color"), ChatColor.class)));
		if (object.has ("extra")) message.setExtra (((List<BaseChatMessage>) context.deserialize (object.get ("extra"), extraType)));
		if (object.has ("hoverEvent")) message.setHoverEvent (((MessageHoverEvent) context.deserialize (object.get ("hoverEvent"), MessageHoverEvent.class)));
		if (object.has ("italic")) message.setItalic (object.get ("italic").getAsBoolean ());
		if (object.has ("obfuscated")) message.setObfuscated (object.get ("obfuscated").getAsBoolean ());
		if (object.has ("strikethrough")) message.setStrikethrough (object.get ("strikethrough").getAsBoolean ());
		if (object.has ("underlined")) message.setUnderlined (object.get ("underlined").getAsBoolean ());

		// normalize
		message.normalize ();

		// return finished instance
		return ((T) message);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JsonElement serialize (T src, Type typeOfSrc, JsonSerializationContext context) {
		// create empty object
		JsonObject object = new JsonObject ();

		// set properties
		if (src.getBold ()) object.add ("bold", context.serialize (src.getBold ()));
		if (src.getClickEvent () != null) object.add ("clickEvent", context.serialize (src.getClickEvent ()));
		if (src.getColor () != null) object.add ("color", context.serialize (src.getColor ()));
		if (src.getExtra () != null) object.add ("extra", context.serialize (src.getExtra ()));
		if (src.getHoverEvent () != null) object.add ("hoverEvent", context.serialize (src.getHoverEvent ()));
		if (src.getItalic ()) object.add ("italic", context.serialize (src.getItalic ()));
		if (src.getObfuscated ()) object.add ("obfuscated", context.serialize (src.getObfuscated ()));
		if (src.getStrikethrough ()) object.add ("strikethrough", context.serialize (src.getStrikethrough ()));
		if (src.getUnderlined ()) object.add ("underlined", context.serialize (src.getUnderlined ()));

		// return finished object
		return object;
	}
}