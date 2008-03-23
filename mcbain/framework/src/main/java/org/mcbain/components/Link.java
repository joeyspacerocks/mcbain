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
import org.mcbain.rest.Uri;
import org.mcbain.template.Element;

/************************************************************************
 * Link component.
 */

public class Link implements Renderer, Elemental, Container {

    private Element element;
    private String resourceId;
    private Object[] parameters;
    private String value;
    private Renderer content;
    
    public void uri(String id, Object... parameters) {
        this.resourceId = id;
        this.parameters = parameters;
    }

    public void value(String value) {
        this.value = value;
    }
    
    public void element(Element element) {
        this.element = element;
    }

    public void contents(Renderer content) {
        this.content = content;
    }
    
    public void render(Context context, Writer writer) {
        Uri uri = context.resources().link(resourceId, parameters);
        
        element.attribute("href", uri);
        
        writer.tag("a").attributes(element);
        
        if (value != null) {
            writer.body(value);
        } else {
            content.render(context, writer);
            writer.close();
        }
    }
}
