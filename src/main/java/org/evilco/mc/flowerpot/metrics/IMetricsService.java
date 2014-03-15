package org.evilco.mc.flowerpot.metrics;

import java.util.UUID;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IMetricsService {

	/**
	 * Sets server details.
	 * @param branding
	 * @param version
	 */
	public void setServerDetail (String branding, String version);

	/**
	 * Adds plugin details.
	 * @param name
	 * @param version
	 */
	public void addPluginDetail (String name, String version);

	/**
	 * Posts the metrics.
	 */
	public void postMetrics (UUID serverID, int playerCount);

	/**
	 * Resets all plugin details.
	 */
	public void resetPluginDetail ();
}
