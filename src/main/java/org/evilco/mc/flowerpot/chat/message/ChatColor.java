package org.evilco.mc.flowerpot.chat.message;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum ChatColor {
	BLACK ('0'),
	DARK_BLUE ('1'),
	DARK_GREEN ('2'),
	DARK_AQUA ('3'),
	DARK_RED ('4'),
	DARK_PURPLE ('5'),
	GOLD ('6'),
	GRAY ('7'),
	DARK_GRAY ('8'),
	BLUE ('9'),
	GREEN ('a'),
	AQUA ('b'),
	RED ('c'),
	LIGHT_PURPLE ('d'),
	YELLOW ('e'),
	WHITE ('f'),
	OBFUSCATED ('k'),
	BOLD ('l'),
	STRIKETHROUGH ('m'),
	UNDERLINE ('n'),
	ITALIC ('o'),
	RESET ('r');

	/**
	 * Stores a reverse lookup map for chat colors.
	 */
	private static Map<Character, ChatColor> characterMap;

	/**
	 * Static Initialization
	 */
	static {
		characterMap = Maps.uniqueIndex (ImmutableList.copyOf (values ()), new Function<ChatColor, Character> () {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Character apply (ChatColor handshakeState) {
				return handshakeState.character;
			}
		});
	}

	/**
	 * Defines the color prefix character.
	 */
	public static final Character CHARACTER = '\u00A7';

	/**
	 * Defines the color code pattern.
	 */
	public static final Pattern PATTERN = Pattern.compile ("(i?)" + String.valueOf (CHARACTER) + "[0-9A-FK-OR]");

	/**
	 * Defines the color character.
	 */
	public final Character character;

	/**
	 * Constructs a new ChatColor.
	 * @param colorCharacter
	 */
	private ChatColor (Character colorCharacter) {
		this.character = colorCharacter;
	}

	/**
	 * Returns the corresponding chat color.
	 * @param character
	 * @return
	 */
	public static ChatColor valueOf (Character character) {
		return characterMap.get (character);
	}

	/**
	 * Strips all color characters from a message.
	 * @param message
	 * @return
	 */
	public String strip (String message) {
		if (message == null) return null;
		return PATTERN.matcher (message).replaceAll ("");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString () {
		return CHARACTER + String.valueOf (this.character);
	}
}
