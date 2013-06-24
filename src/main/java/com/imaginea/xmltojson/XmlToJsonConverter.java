package com.imaginea.xmltojson;

import org.json.JSONObject;
import org.w3c.dom.Element;

public class XmlToJsonConverter {
	
		
	public static void main(String[] args) throws Exception {
		XmlParser parser= new XmlParser();
		JsonGenerator generator= new JsonGenerator();
		Element root = parser.getDocumentRoot();
		JSONObject object = new JSONObject();
		object.accumulate(root.getNodeName(), generator.createJson(root));
		System.out.println(object.toString());
	}
}