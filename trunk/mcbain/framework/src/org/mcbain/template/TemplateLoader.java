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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.mcbain.ComponentFactory;
import org.mcbain.Templated;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.ParserAdapter;


/************************************************************************
 * Responsible for loading and parsing XML templates into a Template.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class TemplateLoader {

    protected static final String FAKE_ROOT = "ROOT";
    
    private ComponentFactory factory;
    private Map<String,Template> templates;
    
    
    /************************************************************************
     * Constructs a new template loader.
     * 
     * @param   factory     Component factory
     */
    
    public TemplateLoader(ComponentFactory factory) {
        this.factory = factory;
        this.templates = new HashMap<String,Template>();
    }
    
    
    /************************************************************************
     * Gets a template by name.
     * 
     * @param   name        Template name
     * @return              Template
     */
    
    public Template findTemplate(Templated renderer) {
        Template template = null;
        String name = renderer.templateName();

        String fileName = "examples/blog/web/" + name + ".html";
        File file = new File(fileName);
        
        if (file.exists()) {
            template = templates.get(name);

            if (template == null || template.olderThan(file.lastModified())) {
                // TODO: synchronise
                // TODO: move location code

                System.out.println("[mcbain] Loading template " + file.getName());
                
                InputStream in;
                try {
                    in = new FileInputStream(fileName);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("Template file not found : " + file.getAbsolutePath());
                }
                
                template = loadTemplate(name, in);
                templates.put(name, template);
                
                renderer.attachTemplate(template);
            }
        }
        
        return template;
    }
    
    
    /************************************************************************
     * Loads and parses a template from an open input stream.
     * 
     * @param   id          Template id
     * @param   in          Input stream
     * @return              Template specification
     */
    
    public Template loadTemplate(String id, InputStream in) {
        Template root = new Template(id, this);
        
        DefaultHandler handler = new TemplateSAXHandler(factory, root);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
            
            ParserAdapter pa = new ParserAdapter(saxParser.getParser());
            pa.setContentHandler(handler);
            pa.setEntityResolver(handler);
            
            pa.parse(new InputSource( new WrappedInputStream(in) ));
            
            in.close();
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return root;
    }
    
    
    /************************************************************************
     * Input wrapper that wraps the XML with a root element to ensure that
     * if the input is a fragment then a root exists.
     */
    
    public static class WrappedInputStream extends InputStream {

        private static final String OPEN = FAKE_ROOT + "><";
        private static final String CLOSE = "/" + FAKE_ROOT + ">";
        
        private enum State { SEEK, ENTITY, FOUND, OPEN, BODY, CLOSE };
        
        private InputStream in;
        private State state;
        
        private int cursor;
        private StringBuilder buffered;
        
        public WrappedInputStream(InputStream in) {
            this.in = in;
            state = State.SEEK;
            buffered = new StringBuilder();
        }
        
        public int read() throws IOException {
            int b = -1;
            
            switch (state) {
            case BODY:
                if ((b = in.read()) < 0) {
                    state = State.CLOSE;
                    b = '<';
                }
                break;
                
            case SEEK:
                b = in.read();
                if (b == '<') {
                    state = State.FOUND;
                    buffered.setLength(0);
                }
                break;
                
            case ENTITY:
                b = in.read();
                if (b == '>') {
                    String ent = buffered.toString();
                    boolean comment = ent.startsWith("!--");
                    if ((comment && ent.endsWith("--")) || !comment) {
                        state = State.SEEK;
                    }
                }
                
                buffered.append((char) b);
                break;
                
            case FOUND:
                b = in.read();
                buffered.append((char) b);

                if (b == '?' || b == '!') {
                    state = State.ENTITY;
                    break;
                }
                
                state = State.OPEN;

                // Fall through deliberately to OPEN case
                
            case OPEN:
                if (cursor >= OPEN.length()) {
                    state = State.BODY;
                    b = buffered.charAt(0);
                    cursor = 0;
                } else {
                    b = OPEN.charAt(cursor++);
                }
                break;
                
            case CLOSE:
                b = (cursor >= CLOSE.length() ? -1 : CLOSE.charAt(cursor++));
                break;
            }
            
            return b;
        }
    }
}
