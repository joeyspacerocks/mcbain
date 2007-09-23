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

import org.mcbain.rest.Resources;
import org.mcbain.template.TemplateFactory;


/************************************************************************
 * The render engine is responsible for rendering components in a 
 * particular context, for example, for a specific request.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class RenderEngine {

    private TemplateFactory templateFactory;
    
    
    /************************************************************************
     * Constructs a new render engine.
     */

    public RenderEngine(TemplateFactory templateFactory) {
        this.templateFactory = templateFactory;
    }
    
    
    /************************************************************************
     * Renders the specified templated renderer.
     * 
     * @param   renderer        Renderer to render
     * @return                  Markup writer containing result
     */
    
    public Writer render(Resources context, Templated renderer) {
        renderer.templateFactory(templateFactory);
        return render(context, (Renderer) renderer);
    }
    
    
    /************************************************************************
     * Renders the specified renderer.
     * 
     * @param   context         Render context
     * @param   renderer        Renderer to render
     * @return                  Markup writer containing result
     */
    
    public Writer render(Resources context, Renderer renderer) {
        Writer writer = new Writer();
        renderer.render(context, writer);
        return writer;
    }
}
