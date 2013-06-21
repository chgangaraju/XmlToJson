package com.imaginea.xmltojson;

import org.json.simple.JSONObject;
import org.w3c.dom.Element;

public class XmlToJsonConverter {
	public static void main(String[] args) throws Exception {
		XmlParser parser = new XmlParser();
		Element root = parser.getDocumentRoot();
		JSONObject object = parser.generator.createJson(root);
		System.out.println(object.toString());
	}
}
