package pt.uc.dei.paj.scheduler;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import pt.uc.dei.paj.ws.StatsBean;


@Singleton
public class StatsScheduler {

	@Inject
	private StatsBean statsBean;

	/**
	 * Updates dashboard data every minute
	 */
	@Schedule(persistent = false, hour = "*", minute = "*", second = "*/30")
	public void updateStats() {

		statsBean.calcStats();
	}
}
