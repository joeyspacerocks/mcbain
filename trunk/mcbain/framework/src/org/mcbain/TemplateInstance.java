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

package org.mcbain;

import java.util.HashMap;

import org.mcbain.components.Value;
import org.mcbain.rest.Resources;
import org.mcbain.template.Template;
import org.mcbain.util.PairIterator;

/************************************************************************
 * Collection of components keyed by id.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class TemplateInstance extends HashMap<String, Renderer> implements Renderer {

    private Template template;
    
    
    /***************************************************************************
     * Constructs a component collection for binding to the specified template.
     * 
     * @param   template        Template
     */

    public TemplateInstance(Template template) {
        this.template = template;
    }



    /***************************************************************************
     * Binds a component collection from the supplied key/value pairs.
     * 
     * @param   content     Array of objects, in String/Object pairs
     */

    public TemplateInstance bind(Object... content) {
        PairIterator<String, Object> it = new PairIterator<String, Object>(content);
        
        while (it.hasNext()) {
            String id = it.nextKey();
            Object component = it.nextValue();
            
            if (component instanceof Renderer) {
                bind(id, (Renderer) component);
            } else {
                bind(id, component);
            }
        }
        
        return this;
    }


    /************************************************************************
     * Adds a component to the collection.
     * 
     * @param   id              Component id in the context of it's container
     * @param   component       Renderable component
     * @return                  This object
     */
    
    public TemplateInstance bind(final String id, final Renderer component) {
        put(id, component);
        return this;
    }
    
    
    /************************************************************************
     * Adds an object to the collection. The object will be rendered using
     * its toString method.
     * 
     * @param   id              Component id in the context of it's container
     * @param   value           Object to be rendererd
     * @return                  This object
     */
    
    public TemplateInstance bind(final String id, final Object value) {
        put(id, new Value(value));
        return this;
    }
    
    
    // @see org.mcbain.Renderer#render(org.mcbain.rest.Resources, org.mcbain.Writer)
    
    public void render(Resources context, Writer writer) {
        template.render(context, writer, this);
    }
}
