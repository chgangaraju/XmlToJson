package com.imaginea.xmltojson;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

@SuppressWarnings("unchecked")
public class JsonGenerator {
	private XmlParser parser;

	public JsonGenerator(XmlParser parser) {
		this.parser = parser;
	}

	public JSONObject createJson(Element element) {
		JSONObject parent = new JSONObject();
		JSONArray array = new JSONArray();
		if (element.hasAttributes()) {
			array.add(getAttributesAsJson(element));
		}
		if (element.hasChildNodes()) {
			List<Element> childList = parser.getElementList(element);
			if (childList.size() == 0) {
				parent.put(element.getTagName(), element.getTextContent());
				return parent;
			} else {
				for (int i = 0; i < childList.size(); i++) {
					Element childElement = childList.get(i);
					array.add(createJson(childElement)); // recursion
				}
			}
		}
		parent.put(element.getNodeName(), array);
		return parent;
	}

	public JSONObject getAttributesAsJson(Element element) {
		JSONObject attributes = new JSONObject();
		NamedNodeMap nodeMap = element.getAttributes();
		for (int i = 0; i < nodeMap.getLength(); i++) {
			Node node = nodeMap.item(i);
			attributes.put(node.getNodeName(), node.getNodeValue());
		}
		return attributes;
	}
}