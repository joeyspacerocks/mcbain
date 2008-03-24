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
import org.mcbain.template.Element;

/************************************************************************
 * Component that renders a form.
 */

public class Form implements Renderer, Container, Elemental {

	private String resourceId;
	private Object[] parameters;
	
    private Renderer content;
    private Element element;
    

    public Form(String resourceId, Object... parameters) {
    	this.resourceId = resourceId;
    	this.parameters = parameters;
    }
    
    
    public void contents(Renderer content) {
        this.content = content;
    }

    
    public void element(Element element) {
        this.element = element;
    }

    
    public void render(Context context, Writer writer) {
        writer.tag(element.tag());

        element.attribute("action", context.resources().link(resourceId, parameters));
        element.attribute("method", "POST");

        writer.attributes(element);
        content.render(context, writer);
    
        writer.close();
    }
}
