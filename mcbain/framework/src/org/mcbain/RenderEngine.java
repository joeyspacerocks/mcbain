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

import org.mcbain.template.Template;
import org.mcbain.template.TemplateLoader;


/************************************************************************
 * The render engine is responsible for rendering components in a 
 * particular context, for example, for a specific request.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class RenderEngine {

    private TemplateLoader templateLoader;
    private ComponentFactory componentFactory;
    
    
    /************************************************************************
     * Constructs a new render engine.
     */

    public RenderEngine() {
        this.componentFactory = new ComponentFactory() {
            public Renderer createComponent(String name) {
                return null;
            }
        };
        
        this.templateLoader = new TemplateLoader(componentFactory);
    }
    
    
    /************************************************************************
     * Renders the specified renderer.
     * 
     * @param   renderer        Renderer to render
     * @return                  Markup writer containing result
     */
    
    public Writer render(Templated renderer) {
        Template template = templateLoader.findTemplate(renderer);
        
        renderer.attachTemplate(template);
        
        return render((Renderer) renderer);
    }
    
    
    /************************************************************************
     * Renders the specified renderer.
     * 
     * @param   renderer        Renderer to render
     * @return                  Markup writer containing result
     */
    
    public Writer render(Renderer renderer) {
        Writer writer = new Writer();
        
        renderer.render(writer);
        
        return writer;
    }
}
