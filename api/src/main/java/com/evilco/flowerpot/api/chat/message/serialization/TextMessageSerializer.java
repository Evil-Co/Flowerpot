package com.evilco.flowerpot.api.chat.message.serialization;

import com.evilco.flowerpot.api.chat.message.TextMessage;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class TextMessageSerializer extends BaseMessageSerializer<TextMessage> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TextMessage deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		TextMessage message = super.deserialize (json, TextMessage.class, context);

		// get object
		JsonObject jsonObject = json.getAsJsonObject ();

		// get text
		if (jsonObject.has ("text")) message.setText (jsonObject.get ("text").getAsString ());

		// return finished message
		return message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JsonElement serialize (TextMessage src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject object = ((JsonObject) super.serialize (src, typeOfSrc, context));

		// append text
		object.add ("text", new JsonPrimitive (src.getText ()));

		// return finished object
		return object;
	}
}
