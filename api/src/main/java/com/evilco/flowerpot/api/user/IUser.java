package com.evilco.flowerpot.api.user;

import com.evilco.flowerpot.api.chat.IChatChannel;
import com.evilco.flowerpot.api.network.packet.IPacket;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IUser {

	/**
	 * Returns the currently active chat channel.
	 * @return
	 */
	public IChatChannel getActiveChatChannel ();

	/**
	 * Returns the display name.
	 * @return
	 */
	public String getDisplayName ();

	/**
	 * Returns the username.
	 * @return
	 */
	public String getName ();

	/**
	 * Returns the internal userID.
	 * @return
	 */
	public String getUserID ();

	/**
	 * Sends a packet to the client.
	 * @param packet
	 */
	public void sendPacket (IPacket packet);

	/**
	 * Sets a new display name.
	 * @param displayName
	 */
	public void setDisplayName (String displayName);
}