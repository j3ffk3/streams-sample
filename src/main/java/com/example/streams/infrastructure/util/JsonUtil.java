package com.example.streams.infrastructure.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	/**
	 * String to JsonNode
	 * 
	 * @param jsonString
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public static JsonNode stringToJsonNode(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = null;
		try {
			jsonNode = mapper.readTree(jsonString);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonNode;
	}

	/**
	 * Object To String
	 * 
	 * @param object
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String ObjectToSting(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}
}
