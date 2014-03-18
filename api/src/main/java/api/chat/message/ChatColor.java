package api.chat.message;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum ChatColor {
	@SerializedName ("black")
	BLACK ('0'),
	@SerializedName ("dark_blue")
	DARK_BLUE ('1'),
	@SerializedName ("dark_green")
	DARK_GREEN ('2'),
	@SerializedName ("dark_aqua")
	DARK_AQUA ('3'),
	@SerializedName ("dark_red")
	DARK_RED ('4'),
	@SerializedName ("dark_purple")
	DARK_PURPLE ('5'),
	@SerializedName ("gold")
	GOLD ('6'),
	@SerializedName ("gray")
	GRAY ('7'),
	@SerializedName ("dark_gray")
	DARK_GRAY ('8'),
	@SerializedName ("blue")
	BLUE ('9'),
	@SerializedName ("green")
	GREEN ('a'),
	@SerializedName ("aqua")
	AQUA ('b'),
	@SerializedName ("red")
	RED ('c'),
	@SerializedName ("light_purple")
	LIGHT_PURPLE ('d'),
	@SerializedName ("yellow")
	YELLOW ('e'),
	@SerializedName ("white")
	WHITE ('f'),

	// All colors as of this point are here for legacy purposes.

	@SerializedName ("obfuscated")
	OBFUSCATED ('k'),
	@SerializedName ("bold")
	BOLD ('l'),
	@SerializedName ("strikethrough")
	STRIKETHROUGH ('m'),
	@SerializedName ("underline")
	UNDERLINE ('n'),
	@SerializedName ("italic")
	ITALIC ('o'),
	@SerializedName ("reset")
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
