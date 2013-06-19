package com.imaginea.xmlparser;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlToJson {
	private final static Logger LOGGER = Logger.getLogger(XmlToJson.class
			.getName());
	static JSONArray array = new JSONArray();

	public static void main(String[] args) throws Exception {
		try {
			JSONObject object = new JSONObject();
			URL resource = XmlToJson.class.getClassLoader().getResource(
					"pom.xml");
			File file = new File(resource.getPath());
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);

			Element root = document.getDocumentElement();
			object = getData(root);
			array.put(object);
			System.out.println(object.toString(3));
		} catch (Exception exception) {
			LOGGER.log(Level.SEVERE, "Exception:", exception.getMessage());
			throw new Exception("Exception", exception);
		}
	}

	public static JSONObject getData(Element node) throws JSONException {
		JSONObject parent = new JSONObject();
		JSONObject object = new JSONObject();
		if (node.hasAttributes()) {
			NamedNodeMap nodeMap = node.getAttributes();
			for (int i = 0; i < nodeMap.getLength(); i++) {
				Node node1 = nodeMap.item(i);
				object.put(node1.getNodeName(), node1.getNodeValue());
			}
		}
		if (node.hasChildNodes()) {
			List<Element> list = elements(node);
			if (list.size() == 0) {
				object.put("" + node.getTagName(), "" + node.getTextContent());
				parent.put(node.getNodeName(), object);

			} else {
				for (int i = 0; i < list.size(); i++) {
					Element node2 = list.get(i);
					object.put("" + i, getData(node2));
				}
				parent.put(node.getNodeName(), object);
			}
		}
		return parent;
	}

	public static List<Element> elements(Node parent) {
		List<Element> result = new LinkedList<Element>();
		NodeList nl = parent.getChildNodes();
		for (int i = 0; i < nl.getLength(); ++i) {
			if (nl.item(i).getNodeType() == Node.ELEMENT_NODE)
				result.add((Element) nl.item(i));
		}
		return result;
	}
}