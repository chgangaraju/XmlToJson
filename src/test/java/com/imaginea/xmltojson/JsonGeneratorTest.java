package com.imaginea.xmltojson;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.Test;
import org.w3c.dom.Element;

public class JsonGeneratorTest extends TestCase {
	private JsonGenerator generator;
	private XmlParser parser;

	public JsonGeneratorTest() {
		generator = new JsonGenerator();
		parser = new XmlParser();
	}

	@Test
	public void testProcessChildNodes() throws Exception {
		Element element = parser.getDocumentRoot();
		if (element.hasChildNodes()) {
			JSONObject parent = new JSONObject();
			generator.processChildNodes(element, parent);
			assertNotNull(parent);
		}
	}

	@Test
	public void testCreateJson() throws Exception {
		Element element = parser.getDocumentRoot();
		JSONObject object = generator.createJson(element);
		assertNotNull(object);
	}
}
