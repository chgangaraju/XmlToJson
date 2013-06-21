package com.imaginea.xmltojson;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {
	JsonGenerator generator;

	public XmlParser() {
		generator = new JsonGenerator(this);
	}

	public Element getDocumentRoot() throws Exception {
		URL resource = XmlParser.class.getClassLoader().getResource(
				"sample.xml");
		File file = new File(resource.getPath());
		Document document = this.createDocument(file);
		Element root = document.getDocumentElement();
		return root;
	}

	public Document createDocument(File file) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);
		return document;
	}

	public List<Element> getElementList(Node parent) {
		List<Element> list = new LinkedList<Element>();
		NodeList nodeList = parent.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); ++i) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE)
				list.add((Element) nodeList.item(i));
		}
		return list;
	}
}
