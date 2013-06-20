package com.imaginea.xmltojson;

import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlToJsonConverter {
	private final static Logger LOGGER = Logger
			.getLogger(XmlToJsonConverter.class.getName());

	public static void main(String[] args) throws Exception {
		try {
			JSONObject object=new JSONObject();
			XmlParser parser = new XmlParser();
			URL resource = XmlParser.class.getClassLoader().getResource("sample.xml");
			File file = new File(resource.getPath());
			Document document = parser.createDocument(file);
			Element root = document.getDocumentElement();
			object = parser.generator.createJson(root);
			System.out.println(object.toString());
		} catch (Exception exception) {
			LOGGER.log(Level.SEVERE, "Exception:", exception.getMessage());
			throw new Exception("Exception", exception);
		}
	}

}
