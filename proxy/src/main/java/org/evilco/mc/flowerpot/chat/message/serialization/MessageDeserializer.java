package org.evilco.mc.flowerpot.chat.message.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.evilco.mc.flowerpot.chat.message.BaseMessage;
import org.evilco.mc.flowerpot.chat.message.TextMessage;
import org.evilco.mc.flowerpot.chat.message.TranslatableMessage;

import java.lang.reflect.Type;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MessageDeserializer implements JsonDeserializer<BaseMessage> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BaseMessage deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		// string only
		if (json.isJsonPrimitive ()) return (new TextMessage (json.getAsString ()));

		// translation
		if (json.getAsJsonObject ().has ("translate")) return context.deserialize (json, TranslatableMessage.class);

		// text
		return context.deserialize (json, TextMessage.class);
	}
}
