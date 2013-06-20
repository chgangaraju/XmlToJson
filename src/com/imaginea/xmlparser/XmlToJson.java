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
			object=getData(root);
			//object.put(root.getNodeName(),getData(root));
			System.out.println(object.toString(3));
		} catch (Exception exception) {
			LOGGER.log(Level.SEVERE, "Exception:", exception.getMessage());
			throw new Exception("Exception", exception);
		}
	}

	public static JSONObject getData(Element element) throws JSONException {
		JSONObject parent = new JSONObject();
		JSONArray array=new JSONArray();
		if (element.hasAttributes()) {
			NamedNodeMap nodeMap = element.getAttributes();
			for (int i = 0; i < nodeMap.getLength(); i++) {
				Node node = nodeMap.item(i);
				parent.put(node.getNodeName(), node.getNodeValue());
			}
		}
		if (element.hasChildNodes()) {
			List<Element> childList = getElementList(element);
			if (childList.size() == 0) {
				parent.put("" + element.getTagName(), "" + element.getTextContent());
			//	parent.put(node.getNodeName(), object);

			} else {
				for (int i = 0; i < childList.size(); i++) {
					Element childElement = childList.get(i);
					array.put(getData(childElement));
					//parent.put(""+node2.getNodeName(), array);
				}
				parent.put(element.getNodeName(), array);
			}
		}
		return parent;
	}

	public static List<Element> getElementList(Node parent) {
		List<Element> list = new LinkedList<Element>();
		NodeList nodeList = parent.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); ++i) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE)
				list.add((Element) nodeList.item(i));
		}
		return list;
	}
}