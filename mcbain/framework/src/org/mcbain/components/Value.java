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
import org.mcbain.template.Attributes;

/************************************************************************
 * Simple component that renders a value, wrapped by a tag if one is 
 * specified.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class Value implements Renderer, Elemental {

    private String element;
    private Attributes attributes;
    
    
    // @see org.redneck.Attributes#attributes(java.util.Map)
    
    public void element(String element, Attributes attributes) {
        this.element = element;
        this.attributes = attributes;
    }

    
    /************************************************************************
     * Gets the value to render.
     * 
     * @return      Value
     */
    
    public Object value() {
        return null;
    }
    
    
    // @see org.redneck.Renderer#render(org.redneck.Writer)
    
    public void render(Writer writer) {
        // FIXME: value translation - from object to string?

        Object value = value();
        
        if (value != null) {
            if (element != null) {
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
