package com.imaginea.xmltojson;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JsonGenerator {
	private final Logger LOGGER = Logger.getLogger(JsonGenerator.class
			.getName());

	public JSONObject createJsonObject(Element element) throws Exception {
		JSONObject parent = new JSONObject();
		if (element.hasAttributes()) {
			NamedNodeMap nodeMap = element.getAttributes();
			for (int i = 0; i < nodeMap.getLength(); i++) {
				Node node = nodeMap.item(i);
				try {
					parent.accumulate("-"+node.getNodeName(), node.getNodeValue());
				} catch (Exception e) {
					LOGGER.log(Level.SEVERE, "Exception:", e.getMessage());
					throw new Exception("Exception: " + e.getMessage());
				}
			}
		}
		if (element.hasChildNodes()) {
			processChildNodes(element, parent);
		}
		return parent;
	}

	public void processChildNodes(Element element, JSONObject parent) throws Exception {
		NodeList childList = element.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			Node node = childList.item(i);
			try {
				if (node.getChildNodes().getLength() == 1) {
					parent.accumulate(node.getNodeName(), node.getTextContent());
				} else if (node.getNodeType() == Node.ELEMENT_NODE) {
					parent.accumulate(((Element) node).getNodeName(),
							createJsonObject((Element) node));
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Exception:", e.getMessage());
				throw new Exception("Exception: " + e.getMessage());
			}
		}
	}
}