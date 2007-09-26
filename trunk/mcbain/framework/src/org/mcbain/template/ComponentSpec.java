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

import org.mcbain.Container;
import org.mcbain.Elemental;
import org.mcbain.Renderer;
import org.mcbain.TemplateInstance;
import org.mcbain.Writer;
import org.mcbain.rest.Context;

/************************************************************************
 * Specification of a component, usually defined through by template.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class ComponentSpec implements TemplateElement{

    private Template template;
    private String id;
    private String element;
    private Attributes attributes;
    
    private ComponentSpec parent;
    private List<TemplateElement> children;
    private Map<String, TemplateElement> childLookup;
    
    
    /************************************************************************
     * Constructs a new component specification.
     * 
     * @param   template    Template the specification belongs to
     * @param   id          Component specification id
     */
    
    public ComponentSpec(Template template) {
        this.template = template;
        children = new ArrayList<TemplateElement>(0);
        childLookup = new HashMap<String,TemplateElement>(0);
    }
    
    
    /************************************************************************
     * Constructs a new component specification.
     * 
     * @param   id          Component specification id
     * @param   parent      Parent fragment
     * @param   element     Markup element name
     * @param   attributes  Map of attributes from the contributing tag
     * @param   component   Component the specification defines
     */
    
    public ComponentSpec(String id, ComponentSpec parent, String element, Attributes attributes) {
        this(parent.template);
        this.id = id;
        this.parent = parent;
        this.element = element.toLowerCase();
        this.attributes = attributes;
    }

    
    /************************************************************************
     * Adds a new component specification as a child of this one.
     * 
     * @param   id          Component specification id
     * @param   element     Markup element name
     * @param   attributes  Map of attributes from the contributing tag
     * @return              New child specification
     */
    
    public ComponentSpec add(String id, String element, Attributes attributes) {
        ComponentSpec child = new ComponentSpec(id, this, element, attributes);
        
        template.add(id, child);
        children.add(child);
        childLookup.put(id, child);
        
        return child;
    }
    

    /************************************************************************
     * Adds a new child component specification to hold template text.
     * 
     * @param   text        Template text
     */
    
    public void addTemplateText(String text) {
        TemplateElement child = new TemplateText(text);
        children.add(child);
    }
    
    
    /************************************************************************
     * Gets a list of child templates - returns empty list if none.
     * 
     * @return      List of children
     */
    
    public List<TemplateElement> children() {
        return children;
    }

    
    /************************************************************************
     * Gets the parent of this template, or null if it's the root template.
     * 
     * @return      Parent template, or null
     */
    
    public ComponentSpec parent() {
        return parent;
    }
    
    
    /************************************************************************
     * Gets the XML tag that was used to define the component in the 
     * template.
     * 
     * @return      XML tag name
     */
    
    public String tag() {
        return element;
    }
    

    // @see org.mcbain.template.TemplateElement#render(org.mcbain.rest.Resources, org.mcbain.Writer, org.mcbain.TemplateInstance)
    
    public void render(Context context, Writer writer, TemplateInstance templateInstance) {
        for (TemplateElement e : children) {
            if (e instanceof ComponentSpec) {
                renderElement(context, (ComponentSpec) e, writer, templateInstance);
            } else {
                ((TemplateElement) e).render(context, writer, templateInstance);
            }
        }
    }
    
    
    /************************************************************************
     * Renders a child component specification.
     * 
     * @param   context             Render context
     * @param   spec                Component specification
     * @param   writer              Markup writer
     * @param   templateInstance    Template instance
     */

    private void renderElement(final Context context, final ComponentSpec spec, final Writer writer, final TemplateInstance templateInstance) {
        Renderer component = templateInstance.get(spec.id);
        
        if (component != null) {
            if (component instanceof Container) {
                ((Container) component).contents( new Renderer() {
                    public void render(Context context, Writer writer) {
                        spec.render(context, writer, templateInstance);
                    }
                });
            }
            
            if (component instanceof Elemental) {
                ((Elemental) component).element(spec.element, spec.attributes);
            }
            
            component.render(context, writer);
        }
    }
}