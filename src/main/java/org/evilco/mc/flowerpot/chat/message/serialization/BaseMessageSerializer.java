package org.evilco.mc.flowerpot.chat.message.serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.evilco.mc.flowerpot.chat.message.BaseMessage;
import org.evilco.mc.flowerpot.chat.message.ChatColor;
import org.evilco.mc.flowerpot.chat.message.MessageClickEvent;
import org.evilco.mc.flowerpot.chat.message.MessageHoverEvent;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class BaseMessageSerializer<T extends BaseMessage> implements JsonSerializer<T>, JsonDeserializer<T> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		// create object
		JsonObject jsonObject = json.getAsJsonObject ();

		// create instance of type
		BaseMessage message = null;

		try {
			message = ((Class<?>) typeOfT).asSubclass (BaseMessage.class).newInstance ();
		} catch (Exception ex) {
			throw new JsonParseException (ex);
		}

		// create type tokens
		Type extraType = new TypeToken<List<BaseMessage>> () { }.getType ();

		// set base properties
		if (jsonObject.has ("bold")) message.setBold (jsonObject.get ("bold").getAsBoolean ());
		if (jsonObject.has ("clickEvent")) message.setClickEvent (((MessageClickEvent) context.deserialize (jsonObject.get ("clickEvent"), MessageClickEvent.class)));
		if (jsonObject.has ("color")) message.setColor (((ChatColor) context.deserialize (jsonObject.get ("color"), ChatColor.class)));
		if (jsonObject.has ("extra")) message.setExtra (((List<BaseMessage>) context.deserialize (jsonObject.get ("extra"), extraType)));
		if (jsonObject.has ("hoverEvent")) message.setHoverEvent (((MessageHoverEvent) context.deserialize (jsonObject.get ("hoverEvent"), MessageHoverEvent.class)));
		if (jsonObject.has ("italic")) message.setItalic (jsonObject.get ("italic").getAsBoolean ());
		if (jsonObject.has ("obfuscated")) message.setObfuscated (jsonObject.get ("obfuscated").getAsBoolean ());
		if (jsonObject.has ("strikethrough")) message.setStrikethrough (jsonObject.get ("strikethrough").getAsBoolean ());
		if (jsonObject.has ("underlined")) message.setUnderlined (jsonObject.get ("underlined").getAsBoolean ());

		// return finished object
		return ((T) message);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JsonElement serialize (T src, Type typeOfSrc, JsonSerializationContext context) {
		// create empty object
		JsonObject object = new JsonObject ();

		// set base properties
		if (src.isBold ()) object.add ("bold", new JsonPrimitive (src.isBold ()));
		if (src.getClickEvent () != null) object.add ("clickEvent", context.serialize (src.getClickEvent ()));
		if (src.getColor () != null) object.add ("color", context.serialize (src.getColor ()));
		if (src.getExtra () != null && src.getExtra ().size () > 0) object.add ("extra", context.serialize (src.getExtra ()));
		if (src.getHoverEvent () != null) object.add ("hoverEvent", context.serialize (src.getHoverEvent ()));
		if (src.isItalic ()) object.add ("italic", new JsonPrimitive (src.isItalic ()));
		if (src.isObfuscated ()) object.add ("obfuscated", new JsonPrimitive (src.isObfuscated ()));
		if (src.hasStrikethrough ()) object.add ("strikethrough", new JsonPrimitive (src.hasStrikethrough ()));
		if (src.hasUnderline ()) object.add ("underlined", new JsonPrimitive (src.hasUnderline ()));

		// return finished object.
		return object;
	}
}
