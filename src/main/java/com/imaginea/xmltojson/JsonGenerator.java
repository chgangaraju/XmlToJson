package com.imaginea.xmltojson;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JsonGenerator {
	private XmlParser parser;

	public JsonGenerator(XmlParser parser) {
		this.parser = parser;
	}

	public void iterate(JSONObject parent, Map<String, Object> map)
			throws JSONException {
		Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> pairs = (Map.Entry<String, Object>) iterator
					.next();
			Object value = pairs.getValue();
			String key = pairs.getKey();
			parent.put("-" + key, value);
		}
	}

	public void putInMap(JSONObject obj, Map<String, Object> map)
			throws JSONException {
		Iterator<?> keys = obj.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			map.put(key, obj.get(key));
		}
	}

	public JSONObject createJson(Element element) throws JSONException {
		JSONObject parent = new JSONObject();
		if (element.hasAttributes()) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject attributes = getAttributesAsJson(element);
			putInMap(attributes, map);
			iterate(parent, map);
		}
		if (element.hasChildNodes()) {
			processChildNodes(element, parent);
		}
		return parent;
	}

	public void processChildNodes(Element element, JSONObject parent)
			throws DOMException, JSONException {

		NodeList childList = element.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			Node node = childList.item(i);
			if (node.getChildNodes().getLength() == 1) {
				parent.put(node.getNodeName(), node.getTextContent());
			} else if (node.getNodeType() == Node.ELEMENT_NODE) {
				List<Element> list = parser.getElementListByTag(element,
						node.getNodeName());
				if (list.size() == 1) {
					parent.put(((Element) node).getNodeName(),
							createJson((Element) node));
				} else {
					parent.put(((Element) node).getNodeName(),
							createJSONArray(list));
				}
			}
		}
	}

	public JSONArray createJSONArray(List<Element> list) throws JSONException {
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Element element = list.get(i);
			array.put(createJson(element));
		}
		return array;
	}

	public JSONObject getAttributesAsJson(Element element) throws DOMException,
			JSONException {
		JSONObject attributes = new JSONObject();
		NamedNodeMap nodeMap = element.getAttributes();
		for (int i = 0; i < nodeMap.getLength(); i++) {
			Node node = nodeMap.item(i);
			attributes.put(node.getNodeName(), node.getNodeValue());
		}
		return attributes;
	}
}