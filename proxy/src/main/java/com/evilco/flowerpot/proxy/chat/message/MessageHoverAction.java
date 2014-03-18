package com.evilco.flowerpot.proxy.chat.message;

import com.google.gson.annotations.SerializedName;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum MessageHoverAction {
	@SerializedName ("show_achievement")
	SHOW_ACHIEVEMENT,
	@SerializedName ("show_item")
	SHOW_ITEM,
	@SerializedName ("show_text")
	SHOW_TEXT
}