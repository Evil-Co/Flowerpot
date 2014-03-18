package com.evilco.flowerpot.api.chat.message;

import com.google.gson.annotations.SerializedName;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum MessageClickAction {
	@SerializedName ("open_file")
	OPEN_FILE,
	@SerializedName ("open_url")
	OPEN_URL,
	@SerializedName ("run_command")
	RUN_COMMAND,
	@SerializedName ("suggest_command")
	SUGGEST_COMMAND
}
