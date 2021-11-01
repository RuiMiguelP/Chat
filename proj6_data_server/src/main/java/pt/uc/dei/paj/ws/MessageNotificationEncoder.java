package pt.uc.dei.paj.ws;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
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
	 * Method that takes an object and turns it into a json object.
	 */
	@Override
	public String encode(NotificationDto notification) throws EncodeException {

		ObjectMapper mapper = new ObjectMapper();
		String json = "";

		try {
			json = mapper.writeValueAsString(notification);
		} catch (JsonProcessingException e) {
			logger.log(Level.INFO, "encode " + e);
			e.printStackTrace();
		}

		return json;
	}
}
