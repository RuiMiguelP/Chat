package pt.uc.dei.paj.beans;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import pt.uc.dei.paj.ws.stats.ClientStatsGenerator;

@Startup
@Singleton
public class ClientConnectionBean {

	private ClientStatsGenerator webServer = new ClientStatsGenerator();
	
	/**
	 * Establishes the connection with the dataServer at startup
	 */
	@PostConstruct
	public void connect() {
		System.out.println("Start new connection!");
		webServer.connectToServer();
		
	}
}
