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

import org.mcbain.TemplateInstance;
import org.mcbain.Writer;
import org.mcbain.rest.Context;

/************************************************************************
 * Representation of a component template, holding the hierarchy of 
 * contained component specifications.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class Template implements TemplatePart {

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
    
    
    // @see org.mcbain.template.TemplateElement#render(org.mcbain.rest.Resources, org.mcbain.Writer, org.mcbain.TemplateInstance)
    
    public void render(Context context, Writer writer, TemplateInstance templateInstance) {
        root.render(context, writer, templateInstance);
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
    
    
    /************************************************************************
     * Creates a new template instance.
     * 
     * @return      Template instance
     */
    
    public TemplateInstance instance() {
        return new TemplateInstance(this);
    }
}
