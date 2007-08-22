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
import org.mcbain.template.Attributes;

/************************************************************************
 * Component that renders its content multiple times.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class Loop<T> implements Renderer, Container, Elemental {

    private Renderer content;
    private String element;
    private T currentValue;

    
    /************************************************************************
     * Gets the loop source. Should be overriden when instance created to 
     * supply custom source.
     * 
     * @return      Iterable source
     */

    public Iterable<T> source() {
        return null;
    }

    
    /************************************************************************
     * Sets the binding to the variable to use for the loop value.
     * 
     * @param binding
     * @return
     */
    
    public void currentValue(T value) {
        currentValue = value;
    }

    
    /************************************************************************
     * Gets the current value in the loop.
     * 
     * @return      Current loop value
     */
    
    public T value() {
        return currentValue;
    }
    
    
    // @see org.redneck.Container#contents(org.redneck.Renderer)
    
    public void contents(Renderer content) {
        this.content = content;
    }

    
    // @see org.redneck.Attributes#attributes(java.util.Map)
    
    public void element(String element, Attributes attributes) {
        this.element = element;
    }

    
    // @see org.redneck.Renderer#render(org.redneck.Writer)

    @SuppressWarnings("unchecked")
    public void render(Writer writer) {
        Iterable<T> items = source();
        
        if (items == null) return;

        if (element != null)
            writer.tag(element);

        for (T value : items) {
            currentValue(value);
            content.render(writer);
        }
        
        if (element != null)
            writer.close();
    }
}
