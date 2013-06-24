package com.imaginea.xmltojson;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JsonGenerator {
	
	public void iterate(JSONObject parent, Map<String, Object> map)
			throws JSONException {
		Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> pairs = (Map.Entry<String, Object>) iterator
					.next();
			Object value = pairs.getValue();
			String key = pairs.getKey();
			parent.accumulate("-" + key, value);
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
				parent.accumulate(node.getNodeName(), node.getTextContent());
			} else if (node.getNodeType() == Node.ELEMENT_NODE) {
				parent.accumulate(((Element) node).getNodeName(),
						createJson((Element) node));
			}
		}
	}

	public JSONObject getAttributesAsJson(Element element) throws DOMException,
			JSONException {
		JSONObject attributes = new JSONObject();
		NamedNodeMap nodeMap = element.getAttributes();
		for (int i = 0; i < nodeMap.getLength(); i++) {
			Node node = nodeMap.item(i);
			attributes.accumulate(node.getNodeName(), node.getNodeValue());
		}
		return attributes;
	}
}