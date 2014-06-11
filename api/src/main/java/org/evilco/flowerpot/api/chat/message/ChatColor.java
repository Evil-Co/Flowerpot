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

package org.evilco.flowerpot.api.chat.message;

import com.google.gson.annotations.SerializedName;

/**
 * Provides a list of possible message colors.
 */
public enum ChatColor {
	@SerializedName ("aqua")
	AQUA,
	@SerializedName ("black")
	BLACK,
	@SerializedName ("blue")
	BLUE,
	@SerializedName ("dark_aqua")
	DARK_AQUA,
	@SerializedName ("dark_blue")
	DARK_BLUE,
	@SerializedName ("dark_gray")
	DARK_GRAY,
	@SerializedName ("dark_green")
	DARK_GREEN,
	@SerializedName ("dark_purple")
	DARK_PURPLE,
	@SerializedName ("dark_red")
	DARK_RED,
	@SerializedName ("gold")
	GOLD,
	@SerializedName ("gray")
	GRAY,
	@SerializedName ("green")
	GREEN,
	@SerializedName ("light_purple")
	LIGHT_PURPLE,
	@SerializedName ("red")
	RED,
	@SerializedName ("yellow")
	YELLOW,
	@SerializedName ("white")
	WHITE
}