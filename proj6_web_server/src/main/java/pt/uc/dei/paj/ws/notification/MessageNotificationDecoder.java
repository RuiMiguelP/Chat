package pt.uc.dei.paj.ws.notification;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pt.uc.dei.paj.dto.NotificationDto;

public class MessageNotificationDecoder implements Decoder.Text<NotificationDto> {

	private static final Logger logger = Logger.getLogger("MessageNotificationDecoder");

	@Override
	public void init(EndpointConfig config) {
		logger.log(Level.INFO, "init method called");
	}

	@Override
	public void destroy() {
		logger.log(Level.INFO, "destroy method called");
	}

	@Override
	public NotificationDto decode(String jsonMessage) throws DecodeException {
		ObjectMapper mapper = new ObjectMapper();
		NotificationDto notification = new NotificationDto();
		try {
			notification = mapper.readValue(jsonMessage, NotificationDto.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return notification;
	}

	@Override
	public boolean willDecode(String jsonMessage) {
		// TODO Auto-generated method stub
		try {
			// Check if incoming message is valid JSON
			Json.createReader(new StringReader(jsonMessage)).readObject();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
