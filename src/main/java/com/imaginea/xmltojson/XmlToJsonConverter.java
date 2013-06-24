package com.imaginea.xmltojson;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;

public class XmlToJsonConverter {
	private final static Logger LOGGER = Logger
			.getLogger(XmlToJsonConverter.class.getName());

	public static void main(String[] args) throws Exception {
		XmlParser parser = new XmlParser();
		JsonGenerator generator = new JsonGenerator();
		Element root = parser.getDocumentRoot();
		JSONObject object = new JSONObject();
		try {
			object.accumulate(root.getNodeName(), generator.createJson(root));
		} catch (JSONException jsonException) {
			LOGGER.log(Level.SEVERE, "JSON Exception:",jsonException.getMessage());
			throw new JSONException("JSON Exception: "+ jsonException.getMessage());
		}
		System.out.println(object.toString());
	}
}