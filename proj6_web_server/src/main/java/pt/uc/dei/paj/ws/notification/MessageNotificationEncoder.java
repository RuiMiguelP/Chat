package pt.uc.dei.paj.ws.notification;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pt.uc.dei.paj.dto.NotificationDto;

public class MessageNotificationEncoder implements Encoder.Text<NotificationDto> {

	private static final Logger logger = Logger.getLogger("MessageNotificationEncoder");

	@Override
	public void init(EndpointConfig config) {
		logger.log(Level.INFO, "init method called");
	}

	@Override
	public void destroy() {
		logger.log(Level.INFO, "destroy method called");
	}

	/**
	 * Method that takes an object (NotificationDto) and turns it into a json
	 * object.
	 */
	@Override
	public String encode(NotificationDto notification) throws EncodeException {
		// TODO Auto-generated method stub

		ObjectMapper mapper = new ObjectMapper();
		String json = "";

		try {
			json = mapper.writeValueAsString(notification);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return json;
	}
}
