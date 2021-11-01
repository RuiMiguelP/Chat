package pt.uc.dei.paj.ws.stats;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;

import pt.uc.dei.paj.dto.StatsDto;

@ClientEndpoint(decoders = {MessageStatsDecoder.class})
public class ClientStatsGenerator {

	private static final Logger logger = Logger.getLogger("ClientStatsGenerator");
	private static Session session;
	ClientManager client = ClientManager.createClient();

	/**
	 * Connect to DataServerEndpointWs.
	 */
	public void connectToServer() {
		try {
			client.connectToServer(this, new URI("ws://localhost:8080/proj6_data_server/dashboard"));
			logger.log(Level.INFO, "Connected");
		} catch(Exception e) {
			logger.log(Level.SEVERE, "Connection failed");
		}
	}
	
	@OnOpen
	public void onOpen(Session session) {
		ClientStatsGenerator.session = session;
	}
	
	@OnMessage
	public static void onMessage(HashMap<String, ArrayList<StatsDto>> message) {
		try {
			ClientStatsEndpoint.send(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	
	@OnClose
	public void closeConnection(Session session) {
		try {
			ClientStatsGenerator.session.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnError
	public void handleError(Session session, Throwable t) {
		try {
			ClientStatsGenerator.session.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.log(Level.INFO, t.toString());
			e.printStackTrace();
		}
	}
}

