package com.imaginea.xmltojson;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {
	private final Logger LOGGER = Logger.getLogger(XmlToJsonConverter.class
			.getName());

	public Element getDocumentRoot() throws Exception {
		URL resource = XmlParser.class.getClassLoader().getResource(
				"sample.xml");
		File file = new File(resource.getPath());
		Document document = createDocument(file);
		Element root = document.getDocumentElement();
		return root;
	}

	public Document createDocument(File file) throws Exception {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			return document;
		} catch (Exception exception) {
			LOGGER.log(Level.SEVERE, "Exception:", exception.getMessage());
			throw new Exception("Exception: " + exception.getMessage());
		}
	}

	public List<Element> getElementListByTag(Node parentNode, String tagName) {
		List<Element> list = new LinkedList<Element>();
		NodeList nodeList = parentNode.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if ((nodeList.item(i).getNodeType() == Node.ELEMENT_NODE)
					&& (nodeList.item(i).getNodeName().equals(tagName)))
				list.add((Element) nodeList.item(i));
		}
		return list;
	}
}