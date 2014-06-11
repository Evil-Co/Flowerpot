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

import io.netty.channel.EventLoop;

/**
 * Provides a standardized interface for authentication systems.
 */
public interface IAuthenticationService {

	/**
	 * Tries to authenticate a user.
	 * @param username The username chosen by the user.
	 * @param serverID The globally known server identifier.
	 * @param sharedKey The encryption shared key.
	 * @param publicKey The public key.
	 * @param callback The authentication callback.
	 * @param eventLoop The event loop to work in.
	 */
	public void authenticateUser (String username, byte[] serverID, byte[] sharedKey, byte[] publicKey, IAuthenticationCallback callback, EventLoop eventLoop);
}