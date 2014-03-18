package org.evilco.mc.flowerpot.forge;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ForgeModInformation {

	/**
	 * Defines the mod API name.
	 */
	public String type = "FML";

	/**
	 * Defines the mod list.
	 */
	public ForgeMod[] modList = new ForgeMod[0];

	/**
	 * Constructs a new ForgeModInformation instance.
	 */
	public ForgeModInformation () { }

	/**
	 * Constructs a new ForgeModInformation instance.
	 * @param modList
	 */
	public ForgeModInformation (ForgeMod[] modList) {
		this.modList = modList;
	}
}
