package pt.uc.dei.paj.ws.stats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import pt.uc.dei.paj.dto.StatsDto;


@ServerEndpoint(value = "/dashboard", encoders = { MessageStatsEncoder.class })
public class ClientStatsEndpoint {
	
		private static final Logger logger = Logger.getLogger("ClientStatsEndpoint");
		
		static Queue<Session> queue = new ConcurrentLinkedQueue<>(); 
		
		public static void send(HashMap<String, ArrayList<StatsDto>> message) throws IOException {

			try { /* Send updates to all open WebSocket sessions */ 
				for (Session session : queue) {
					session.getBasicRemote().sendObject(message); 
				} 
			}
			catch (IOException e) { 
				logger.log(Level.INFO, e.toString()); 
			} catch (EncodeException e) {
				e.printStackTrace();
			} 
		}

		@OnOpen
		public void openConnection(Session session) {
			/* Register this connection in the queue */
			queue.add(session);
			logger.log(Level.INFO, "Connection opened.");
		}

		@OnClose
		public void closedConnection(Session session) {
			/* Remove this connection from the queue */
			queue.remove(session);
			logger.log(Level.INFO, "Connection closed.");
		}

		@OnError
		public void error(Session session, Throwable t) {
			/* Remove this connection from the queue */
			queue.remove(session);
			logger.log(Level.INFO, t.toString());
			logger.log(Level.INFO, "Connection error.");
		}
}
