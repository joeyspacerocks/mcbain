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

import org.mcbain.TemplateInstance;
import org.mcbain.Writer;
import org.mcbain.rest.Context;

/************************************************************************
 * Renderer used to store and render portions of text from component
 * templates.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class TemplateText implements TemplateElement {

    private String text;
    
    
    /************************************************************************
     * Constructs a new instance to render the supplied text.
     * 
     * @param   text        Template text
     */
    
    public TemplateText(String text) {
        this.text = text;
    }
    

    // @see org.mcbain.template.TemplateElement#render(org.mcbain.rest.Resources, org.mcbain.Writer, org.mcbain.TemplateInstance)
    
    public void render(Context context, Writer writer, TemplateInstance templateInstance) {
        writer.print(text, true);
    }
    
    
    /************************************************************************
     * Gets the text to be rendered.
     * 
     * @return      Text
     */
    
    public String text() {
        return text;
    }

    
    // @see java.lang.Object#toString()
    
    @Override
    public String toString() {
        return text;
    }
}
