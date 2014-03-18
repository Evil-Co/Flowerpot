package com.evilco.flowerpot.api.chat.message.serialization;

import com.evilco.flowerpot.api.chat.message.BaseMessage;
import com.evilco.flowerpot.api.chat.message.TranslatableMessage;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class TranslatableMessageSerializer extends BaseMessageSerializer<TranslatableMessage> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TranslatableMessage deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		TranslatableMessage message = super.deserialize (json, typeOfT, context);

		// get object
		JsonObject jsonObject = json.getAsJsonObject ();

		// create type tokens
		Type withType = new TypeToken<List<BaseMessage>> () { }.getType ();

		// set name
		if (jsonObject.has ("translate")) message.setName (jsonObject.get ("translate").getAsString ());
		if (jsonObject.has ("with")) message.setWith (((List<BaseMessage>) context.deserialize (jsonObject.get ("with"), withType)));

		// return finished object
		return message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JsonElement serialize (TranslatableMessage src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject object = ((JsonObject) super.serialize (src, typeOfSrc, context));

		// set properties
		object.add ("translate", new JsonPrimitive (src.getName ()));
		if (src.getWith () != null && src.getWith ().size () > 0) object.add ("with", context.serialize (src.getWith ()));

		// return finished object
		return object;
	}
}
