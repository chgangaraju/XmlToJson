package com.imaginea.xmltojson;

import java.util.HashMap;
import java.util.Map;

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
	public void testGetAttributesAsJson() throws Exception {
		XmlParser parser = new XmlParser();
		Element element = parser.getDocumentRoot();
		if (element.hasAttributes()) {
			JSONObject attributes = generator.getAttributesAsJson(element);
			assertNotNull(attributes);
		}
	}

	@Test
	public void testIterate() throws Exception {
		Element element = parser.getDocumentRoot();
		if (element.hasAttributes()) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject parent=new JSONObject();
			JSONObject attributes = generator.getAttributesAsJson(element);
			generator.putInMap(attributes, map);
			generator.iterate(parent, map);
			assertNotNull(parent);
		}
	}

	@Test
	public void testPutInMap() throws Exception {
		Element element = parser.getDocumentRoot();
		if (element.hasAttributes()) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject attributes = generator.getAttributesAsJson(element);
			generator.putInMap(attributes, map);
			assertNotNull(map);
		}
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
