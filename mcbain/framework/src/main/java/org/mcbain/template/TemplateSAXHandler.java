// Copyright 2007 Joe Trewin
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.mcbain.template;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.mcbain.ComponentFactory;
import org.mcbain.util.ArrayStack;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.ext.Locator2;

/*******************************************************************************
 * SAX handler that is used to decompose an XML template into a hierarchical 
 * structure of component specifications.
 */

public class TemplateSAXHandler extends DefaultHandler2 {
    
    public static final String DEFAULT_ID_ATTRIBUTE = "id";
    
    private Locator locator;
    
    private ComponentFactory factory;
    private String idAttribute = DEFAULT_ID_ATTRIBUTE;
    
    private ComponentSpec current;
    private StringBuilder tween;
    private boolean parsingDtd;
    
    private int depth;
    private ArrayStack<String> tagStack;
    private Map<String, Integer> componentCount;

    private int startColumn;
    private int startLine;

    
    /************************************************************************
     * Constructs a new handler instance.
     * 
     * @param   factory     Component factory
     * @param   root        Root template
     */
    
    public TemplateSAXHandler(ComponentFactory factory, Template template) {
        this.current = template.root();
        this.factory = factory;
        this.tagStack = new ArrayStack<String>();
        this.componentCount = new HashMap<String, Integer>();
    }

    
    // @see org.xml.sax.helpers.DefaultHandler#setDocumentLocator(org.xml.sax.Locator)
    
    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
    }

    
    // @see org.xml.sax.helpers.DefaultHandler#startDocument()
    
    public void startDocument() throws SAXException {
        tween = new StringBuilder();
        depth = 0;
    }

    
    // @see org.xml.sax.helpers.DefaultHandler#endDocument()
    
    public void endDocument() throws SAXException {
        endTween();
    }

    
    // @see org.xml.sax.ext.DefaultHandler2#startDTD(java.lang.String, java.lang.String, java.lang.String)
    
    public void startDTD(String name, String publicId, String systemId) throws SAXException {
        tween("<!DOCTYPE ").tween(name).tween(" PUBLIC \"").tween(publicId)
                .tween("\" \"").tween(systemId).tween("\">\n");
        parsingDtd = true;
    }

    
    // @see org.xml.sax.ext.DefaultHandler2#endDTD()
    
    public void endDTD() throws SAXException {
        parsingDtd = false;
    }

    
    // @see org.xml.sax.ext.DefaultHandler2#comment(char[], int, int)
    
    public void comment(char[] ch, int start, int length) throws SAXException {
        if (!parsingDtd)
            tween("<!--").tween(new String(ch, start, length)).tween("-->");
    }

    
    // @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
    
    public void characters(char[] ch, int start, int length) throws SAXException {
        tween.append(new String(ch, start, length));
    }

    
    // @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
    
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        if (name.equals(TemplateFactory.FAKE_ROOT)) return;
        
        String id = attributes.getValue(idAttribute);
        startColumn = locator.getColumnNumber();
        startLine = locator.getLineNumber();
        
        if (id != null) {           // if has id attribute, create a component to be later bound
            endTween();
            tagStack.push(name + depth);
            current = current.add(id, createElement(name, attributes));
            
        } else {                    // if no id, look for default anonymous component
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

    
    // @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
    
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

    
    // @see org.xml.sax.ext.DefaultHandler2#resolveEntity(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
    
    public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId) throws SAXException, IOException {
        String leaf = "org/mcbain/dtd" + systemId.substring(systemId.lastIndexOf('/'));

        InputStream in = getClass().getClassLoader().getResourceAsStream(leaf);

        if (in != null) {
            return new InputSource(in);

        } else {
            return super.resolveEntity(name, publicId, baseURI, systemId);
        }
    }

    
    /************************************************************************
     * Stores text found in between component templates.
     * 
     * @param   text        Text to store
     * @return              Handler instance
     */
    
    private TemplateSAXHandler tween(String text) {
        tween.append(text);
        return this;
    }

    
    /************************************************************************
     * Creates a rendering component to hold the current in-between text and
     * resets the buffer.
     */
    
    private void endTween() {
        if (tween.length() > 0) {
            current.addTemplateText(tween.toString());
            tween.setLength(0);
        }
    }
    
    
    /************************************************************************
     * Creates an element wrapper from an XML attributes collection, 
     * filtering out the 'id' attribute.
     * 
     * @param	tag				Element tag name
     * @param   attributes      Attributes collection
     * @return                  Element wrapper
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
    
    
    /************************************************************************
     * Gets the name of an anonymous component, formed using the name plus
     * a count variable.
     * 
     * @param   name            Template name
     * @return                  Unique component name
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
