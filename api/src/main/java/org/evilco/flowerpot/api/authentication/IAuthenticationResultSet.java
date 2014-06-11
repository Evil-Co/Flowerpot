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

package org.evilco.flowerpot.api.authentication;

import java.util.UUID;

/**
 * Represents an authentication result.
 */
public interface IAuthenticationResultSet {

	/**
	 * Returns the shared key associated with this user's connection.
	 * @return The shared key.
	 */
	public byte[] getSharedKey ();

	/**
	 * Returns the globally unique user identifier returned by the service.
	 * @return The user identifier.
	 */
	public UUID getUserID ();

	/**
	 * Returns the username initially chosen.
	 * @return The username.
	 */
	public String getUsername ();
}