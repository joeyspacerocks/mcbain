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


import org.mcbain.Container;
import org.mcbain.Elemental;
import org.mcbain.Renderer;
import org.mcbain.Writer;
import org.mcbain.rest.Context;
import org.mcbain.template.Attributes;

/************************************************************************
 * Component that conditionally renders its content.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class If implements Renderer, Container, Elemental {

    private Renderer content;
    private String element;
    private boolean condition;
    

    /************************************************************************
     * Constructs a new instance.
     */

    public If() {
    }
    
    
    /************************************************************************
     * Constructs a new component with a specified condition.
     * 
     * @param   condition       True to render the content, false not to
     */

    public If(boolean condition) {
        this.condition = condition;
    }
    
    
    /************************************************************************
     * Tests to see if the content should be rendererd.
     * 
     * @return      True to render the content, false not to
     */
    
    public boolean condition() {
        return condition;
    }
    
    
    // @see org.mcbain.Container#contents(org.mcbain.Renderer)
    
    public void contents(Renderer content) {
        this.content = content;
    }

    
    // @see org.redneck.Attributes#attributes(java.util.Map)
    
    public void element(String element, Attributes attributes) {
        this.element = element;
    }

    
    // @see org.mcbain.Renderer#render(org.mcbain.rest.Resources, org.mcbain.Writer)
    
    public void render(Context context, Writer writer) {
        if (condition) {
            if (element != null)
                writer.tag(element);

            content.render(context, writer);
        
            if (element != null)
                writer.close();
        }
    }
}