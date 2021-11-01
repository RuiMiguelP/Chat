package pt.uc.dei.paj.beans;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import pt.uc.dei.paj.ws.notification.ClientNotification;
import pt.uc.dei.paj.ws.stats.ClientStatsGenerator;

@Startup
@Singleton
public class ClientNotificationConnectionBean {

	private ClientNotification webServer = new ClientNotification();
	
	/**
	 * Establishes the connection with the dataServer at startup
	 */
	@PostConstruct
	public void connect() {
		System.out.println("Start new notification connection!");
		webServer.connectToServer();
		
	}
}
