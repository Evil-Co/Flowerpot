package org.evilco.mc.flowerpot.metrics;

import org.evilco.mc.flowerpot.FlowerpotServer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MetricsManager extends TimerTask {

	/**
	 * Defines the delay between each ping (10 minutes).
	 */
	public static final int DELAY = 600000;

	/**
	 * Stores the current metrics service.
	 */
	protected IMetricsService service;

	/**
	 * Stores the parent timer.
	 */
	protected Timer timer = null;

	/**
	 * Constructs a new MetricsManager.
	 */
	public MetricsManager (IMetricsService service) {
		this.service = service;
		final MetricsManager instance = this;

		// create timer instance
		this.timer = new Timer ();

		// schedule
		this.schedule ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run () {
		// synchronize thread to prevent collisions
		synchronized (FlowerpotServer.getInstance ().getConfiguration ()) {
			// check for opt-out
			if (FlowerpotServer.getInstance ().getConfiguration ().getMetricsOptedOut ()) return;

			// post metrics
			this.service.postMetrics (FlowerpotServer.getInstance ().getConfiguration ().getMetricsIdentifier (), 0); // TODO: Add proper player count
		}
	}

	/**
	 * Shedules a run.
	 */
	public void schedule () {
		this.timer.scheduleAtFixedRate (this, 0, DELAY);
	}
}