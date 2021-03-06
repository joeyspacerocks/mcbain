/*
 * Copyright 2010 Joe Trewin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mcbain.template;

import org.mcbain.util.ArrayStack;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * SAX handler that is used to decompose an XML template into a hierarchical
 * structure of component specifications.
 */

class TemplateSAXHandler extends DefaultHandler2 {

	private static final String DEFAULT_ID_ATTRIBUTE = "id";

	private Locator locator;

	private String idAttribute = DEFAULT_ID_ATTRIBUTE;

	private ComponentSpec current;
	private StringBuilder tween;
	private boolean parsingDtd;

	private int depth;
	private ArrayStack<String> tagStack;
	private Map<String, Integer> componentCount;

	private int startColumn;
	private int startLine;


	/**
	 * Constructs a new handler instance.
	 *
	 * @param template Root template
	 */

	public TemplateSAXHandler(TemplateClass template) {
		this.current = template.root();
		this.tagStack = new ArrayStack<String>();
		this.componentCount = new HashMap<String, Integer>();
	}


	public void setDocumentLocator(Locator locator) {
		this.locator = locator;
	}


	public void startDocument() throws SAXException {
		tween = new StringBuilder();
		depth = 0;
	}


	public void endDocument() throws SAXException {
		endTween();
	}

	public void startDTD(String name, String publicId, String systemId) throws SAXException {
		tween("<!DOCTYPE ").tween(name).tween(" PUBLIC \"").tween(publicId)
			.tween("\" \"").tween(systemId).tween("\">\n");
		parsingDtd = true;
	}

	public void endDTD() throws SAXException {
		parsingDtd = false;
	}

	public void comment(char[] ch, int start, int length) throws SAXException {
		if (!parsingDtd)
			tween("<!--").tween(new String(ch, start, length)).tween("-->");
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		String value = new String(ch, start, length);

		int valuePos;
		int endPos = -1;

		while ((valuePos = value.indexOf("${", endPos)) > -1) {
			tween.append(value.substring(0, valuePos));

			endPos = value.indexOf("}", valuePos);
			if (endPos < 0) {
				break;
			}

			endTween();
			String id = value.substring(valuePos + 2, endPos);
			current.add(id, new Element(null, id), startLine);
		}

		tween.append(value.substring(endPos + 1));
	}

	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		if (name.equals(TemplateFactory.FAKE_ROOT)) return;

		String id = attributes.getValue(idAttribute);
		startColumn = locator.getColumnNumber();
		startLine = locator.getLineNumber();

		if (id != null) {		   // if has id attribute, create a component to be later bound
			endTween();
			tagStack.push(name + depth);
			current = current.add(id, createElement(name, attributes), startLine);

		} else {					// if no id, look for default anonymous component
//            Renderer r = factory.createComponent(name);

//            if (r == null) {
			tween("<").tween(name);

			int count = attributes.getLength();
			while (count-- > 0) {
				tween(" ").tween(attributes.getLocalName(count)).tween("=\"");
				tween(attributes.getValue(count)).tween("\"");
			}

			tween(">");

//            } else {                    // else is content
//    
//                endTween();
//                tagStack.push(name);
//                current = current.add( componentName(name), name);
//                current.component(r);
//            }
		}

		depth++;
	}


	public void endElement(String uri, String localName, String name) throws SAXException {
		if (name.equals(TemplateFactory.FAKE_ROOT)) return;

		depth--;

		String latestTag = tagStack.peek();

		if (latestTag != null && latestTag.equals(name + depth)) {
			endTween();
			current = current.parent();
			tagStack.pop();

		} else {
			if (locator.getColumnNumber() == startColumn && locator.getLineNumber() == startLine) {
				tween.insert(tween.length() - 1, '/');
			} else {
				tween("</").tween(name).tween(">");
			}
		}
	}


	public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId) throws SAXException, IOException {
		int leafStart = systemId.lastIndexOf('/');
		String leaf = "org/mcbain/dtd/" + systemId.substring(leafStart + 1);

		InputStream in = getClass().getClassLoader().getResourceAsStream(leaf);

		if (in != null) {
			return new InputSource(in);

		} else {
			return super.resolveEntity(name, publicId, baseURI, systemId);
		}
	}


	/**
	 * Stores text found in between component templates.
	 *
	 * @param text Text to store
	 * @return Handler instance
	 */

	private TemplateSAXHandler tween(String text) {
		tween.append(text);
		return this;
	}


	/**
	 * Creates a rendering component to hold the current in-between text and
	 * resets the buffer.
	 */

	private void endTween() {
		if (tween.length() > 0) {
			current.addTemplateText(tween.toString());
			tween.setLength(0);
		}
	}


	/**
	 * Creates an element wrapper from an XML attributes collection,
	 * filtering out the 'id' attribute.
	 *
	 * @param attributes Attributes collection
	 * @return Element wrapper
	 * @param	tag				Element tag name
	 */

	private Element createElement(String tag, Attributes attributes) {
		Element element = new Element(tag, attributes.getValue("id"));
		int count = attributes.getLength();

		while (count-- > 0) {
			String name = attributes.getLocalName(count);
			if (!name.equals("id")) {
				element.attribute(name, attributes.getValue(count));
			}
		}

		return element;
	}


	/**
	 * Gets the name of an anonymous component, formed using the name plus
	 * a count variable.
	 *
	 * @param name Template name
	 * @return Unique component name
	 */

	private String componentName(String name) {
		int count = 0;

		if (componentCount.containsKey(name)) {
			count = componentCount.get(name) + 1;
		}

		componentCount.put(name, count);
		return name + count;
	}
}
