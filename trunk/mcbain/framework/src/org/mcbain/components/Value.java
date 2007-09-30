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

package org.mcbain.components;

import org.mcbain.Elemental;
import org.mcbain.Renderer;
import org.mcbain.Writer;
import org.mcbain.rest.Context;
import org.mcbain.template.Attributes;

/************************************************************************
 * Simple component that renders a value, wrapped by a tag if one is 
 * specified.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class Value implements Renderer, Elemental {

    private Object value;
    private String element;
    private Attributes attributes;
    private boolean tagless;

    
    /************************************************************************
     * Constructs a new component with an unspecified value.
     */

    public Value() {
    }

    
    /************************************************************************
     * Constructs a new component to render the specified value.
     */

    public Value(Object value) {
        this.value = value;
    }
    
    
    // @see org.redneck.Attributes#attributes(java.util.Map)
    
    public void element(String element, Attributes attributes) {
        this.element = element;
        this.attributes = attributes;
    }

    
    /************************************************************************
     * Sets the value to render.
     * 
     * @param   value       Value to render
     */
    
    public void value(Object value) {
        this.value = value;
    }

    
    /************************************************************************
     * Indicates that the template element used to refer to the component 
     * should not be rendered.
     */
    
    public void tagless() {
        this.tagless = true;
    }
    
    
    // @see org.mcbain.Renderer#render(org.mcbain.rest.Resources, org.mcbain.Writer)
    
    public void render(Context context, Writer writer) {
        if (value != null) {
            if (!tagless && element != null) {
                writer
                    .tag(element)
                    .attributes(attributes)
                    .body( value.toString() );
                
            } else {
                writer.print( value.toString(), false );
            }
        }
    }

}
