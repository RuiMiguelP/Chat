package pt.uc.dei.paj.ws.stats;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pt.uc.dei.paj.dto.StatsDto;

public class MessageStatsDecoder implements Decoder.Text<HashMap<String, ArrayList<StatsDto>>> {

	private static final Logger logger = Logger.getLogger("MessageStatsDecoder");

	@Override
	public void init(EndpointConfig config) {
		logger.log(Level.INFO, "init method called");
	}

	@Override
	public void destroy() {
		logger.log(Level.INFO, "destroy method called");
	}

	@Override
	public HashMap<String, ArrayList<StatsDto>> decode(String jsonMessage) throws DecodeException {

		HashMap<String, ArrayList<StatsDto>> map = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();

		// Convert Map to JSON
		try {
			map = mapper.readValue(jsonMessage, new TypeReference<HashMap<String, ArrayList<StatsDto>>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return map;
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
