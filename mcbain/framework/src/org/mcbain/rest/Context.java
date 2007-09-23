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

package org.mcbain.rest;

import javax.servlet.ServletContext;

import org.mcbain.TemplateInstance;
import org.mcbain.template.TemplateFactory;


/************************************************************************
 * The context provides components with access to the framework in the 
 * current request context.
 */

public class Context {
    
    private Resources resources;
    private TemplateFactory templateFactory;
    
    /************************************************************************
     * Constructs a new instance.
     */

    public Context(ServletContext servletContext) {
        resources = new Resources();
        templateFactory = new TemplateFactory(servletContext);
    }
    
    /************************************************************************
     * Gets the application resources.
     * 
     * @return      Resources
     */
    
    public Resources resources() {
        return resources;
    }
    
    /************************************************************************
     * Gets the named template instance.
     * 
     * @param   name    Template name
     * @return          Template instance
     */
    
    public TemplateInstance template(String name) {
        return templateFactory.instance(name);
    }
}
