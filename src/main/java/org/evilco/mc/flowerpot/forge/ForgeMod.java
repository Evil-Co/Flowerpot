package org.evilco.mc.flowerpot.forge;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ForgeMod {

	/**
	 * Defines the mod identification.
	 */
	public String modid;

	/**
	 * Defines the version number.
	 */
	public String version;

	/**
	 * Constructs a new ForgeMod.
	 * @param modid
	 * @param version
	 */
	public ForgeMod (String modid, String version) {
		this.modid = modid;
		this.version = version;
	}
}
