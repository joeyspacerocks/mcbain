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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mcbain.Components;
import org.mcbain.Writer;

/************************************************************************
 * Representation of a component template, holding the hierarchy of 
 * contained component specifications.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class Template implements TemplateElement {

    private TemplateFactory loader;
    private ComponentSpec root;
    private List<ComponentSpec> components;
    private Map<String,ComponentSpec> lookup;
    private long timestamp;
    
    
    /************************************************************************
     * Constructs a new template.
     * 
     * @param   loader      Template loader that loaded the template
     * @param   id          Template id
     */
    
    public Template(String id, TemplateFactory loader) {
        this.loader = loader;
        root = new ComponentSpec(this);
        components = new ArrayList<ComponentSpec>();
        lookup = new HashMap<String,ComponentSpec>();
        timestamp = System.currentTimeMillis();
    }
    
    
    /************************************************************************
     * Adds a new component spec.
     * 
     * @param   id          Component id
     * @param   component   Component spec
     */
    
    public void add(String id, ComponentSpec component) {
        components.add(component);
        lookup.put(id, component);
    }
    
    
    /************************************************************************
     * Binds a component to a component specification.
     * 
     * @param   id          Specification id
     * @param   component   Component to bind
     */
    
//    public ComponentSpec bind(String id, Renderer component) {
//        ComponentSpec spec = component(id);
//        
//        if (spec == null) {
//            System.err.println("Could not bind component to unknown specification '" + id + "'");
//            
//        } else {
//            spec.component(component);
//        }
//        
//        return spec;
//    }

    
    /************************************************************************
     * Renders the template with no bound components.
     * 
     * @param   writer      Markup writer
     */
    
    public void render(Writer writer) {
        render(writer, Components.NONE);
    }

    
    // @see org.redneck.template.TemplateElement#render(org.redneck.Writer, org.redneck.Components)
    
    public void render(Writer writer, Components components) {
        
        // TODO: check if template source has changed
//        if (olderThan(file.lastModified())) {
//        }

        root.render(writer, components);
    }

    
    /************************************************************************
     * Tests to see if the template was created before the specified time.
     * 
     * @param   time        Time to test against
     * @return              True if older than time, else false
     */
    
    public boolean olderThan(long time) {
        return (timestamp < time);
    }
    
    
    /************************************************************************
     * Gets the root component specification that contains all the nested
     * specifications.
     * 
     * @return      Root component specification
     */
    
    public ComponentSpec root() {
        return root;
    }

    
    /************************************************************************
     * Gets the template factory that this template was loaded with.
     * 
     * @return      Template factory
     */
    
    public TemplateFactory factory() {
        return loader;
    }
}
