package com.imaginea.xmltojson;

import java.io.File;
import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlParserTest extends TestCase {
	private XmlParser parser;

	public XmlParserTest() {
		parser = new XmlParser();
	}

	@Test
	public void testGetDocumentRoot() throws Exception {
		Element element = parser.getDocumentRoot();
		assertNotNull(element);
	}

	@Test
	public void testCreateDocument() throws Exception {
		URL resource = XmlParser.class.getClassLoader().getResource(
				"sample.xml");
		File file = new File(resource.getPath());
		Document document = parser.createDocument(file);
		assertNotNull(document);
	}

	@Test
	public void testGetElementsByTagName() throws Exception {
		Element element = parser.getDocumentRoot();
		String tagName = element.getTagName();
		List<Element> list =parser.getElementListByTag(element, tagName);
		assertNotNull(list);
	}
}
