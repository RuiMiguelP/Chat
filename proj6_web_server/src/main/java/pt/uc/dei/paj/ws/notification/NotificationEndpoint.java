package pt.uc.dei.paj.ws.notification;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import pt.uc.dei.paj.dto.NotificationDto;

@ServerEndpoint(value = "/notifications/{userId}", encoders= {MessageNotificationEncoder.class})
public class NotificationEndpoint {

	private static final Logger logger = Logger.getLogger("NotificationEndpoint");
	private static ConcurrentHashMap<Session, String> sessionClient = new ConcurrentHashMap<Session, String>();
	
	public static void send(Session session, NotificationDto notification) {
		for (Map.Entry<Session, String> clientSession: NotificationEndpoint.sessionClient.entrySet()) {
			
			logger.log(Level.INFO, "User target " + notification.getUserTarget() + " Session value " + clientSession.getValue());

			if (clientSession.getValue().equals(String.valueOf(notification.getUserTarget()))) {
				
				try {
					clientSession.getKey().getBasicRemote().sendObject(notification);
				} catch (IOException e) {
					logger.log(Level.INFO, e.toString());
				} catch (EncodeException e) {
					System.out.println("EncodeException" + e);
					e.printStackTrace();
				}
			}
		}
	}
	
	@OnOpen
	public void openConnection(Session session, @PathParam("userId") String userId) {
		NotificationEndpoint.sessionClient.put(session, userId);
		logger.log(Level.INFO, "Connection opened.");
	}
	
	@OnClose
	public void closedConnection(Session session) {
		NotificationEndpoint.sessionClient.remove(session);
		logger.log(Level.INFO, "Connection closed.");
	}
	
	@OnError
	public void error(Session session, Throwable t) {
		NotificationEndpoint.sessionClient.remove(session);
		logger.log(Level.INFO, t.toString());
		logger.log(Level.INFO, "Connection error.");
	}
	
	@OnMessage
	public void textMessage(Session session, String message) {
		System.out.println("From: " + session.getId() + " Message " + message);
	}
	
}
