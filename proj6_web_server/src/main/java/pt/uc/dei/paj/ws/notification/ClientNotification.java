package pt.uc.dei.paj.ws.notification;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;

import pt.uc.dei.paj.dto.NotificationDto;

@ClientEndpoint(decoders = MessageNotificationDecoder.class)
public class ClientNotification {

	private static final Logger logger = Logger.getLogger("ClientNotification");
	private Session session;
	 client = ClientManager.createClient();
	
	public void connectToServer() {
		try {
			client.connectToServer(this, new URI("ws://localhost:8080/proj6_data_server/notification"));
			logger.log(Level.INFO, "Connect to server.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		logger.log(Level.INFO, "Client Notification - Connection opened.");
	}
	
	@OnClose
	public void onClose(Session session) {
		
	}
	
	@OnMessage
	public void onMessage(NotificationDto notification) {
		NotificationEndpoint.send(session, notification);
	}
	
	@OnError
	public void onError(Session session, Throwable t) {
		this.session = null;
	}
}
