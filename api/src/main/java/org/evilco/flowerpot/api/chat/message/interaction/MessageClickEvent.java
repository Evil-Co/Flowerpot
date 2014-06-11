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

package org.evilco.flowerpot.api.chat.message.interaction;

import com.google.gson.annotations.SerializedName;

/**
 * Provides a list of possible message click actions.
 */
public enum MessageClickEvent {
	@SerializedName ("open_file")
	OPEN_FILE,
	@SerializedName ("open_url")
	OPEN_URL,
	@SerializedName ("run_command")
	RUN_COMMAND,
	@SerializedName ("suggest_command")
	SUGGEST_COMMAND
}