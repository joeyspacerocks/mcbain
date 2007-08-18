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

                System.out.println("[redneck] Loading template " + file.getName());
                
                
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
            pa.parse(new InputSource(in));
            
            in.close();
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return root;
    }
}
