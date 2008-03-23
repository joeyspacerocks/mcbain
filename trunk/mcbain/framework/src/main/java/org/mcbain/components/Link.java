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
import org.mcbain.template.Attributes;
import org.mcbain.util.PairIterator;

/************************************************************************
 * Link component.
 */

public class Link implements Renderer, Elemental, Container {

    private Attributes attributes;
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
    
    // @see org.redneck.Attributes#attributes(java.lang.String, java.util.Map)

    public void element(String element, Attributes attributes) {
        this.attributes = new Attributes(attributes);
    }

    // @see org.mcbain.Container#contents(org.mcbain.Renderer)
    
    public void contents(Renderer content) {
        this.content = content;
    }
    
    // @see org.mcbain.Renderer#render(org.mcbain.rest.Resources, org.mcbain.Writer)
    
    public void render(Context context, Writer writer) {
        Uri uri = context.resources().link(resourceId);

        if (parameters != null && parameters.length > 0) {
            PairIterator<String, String> it = new PairIterator<String, String>(parameters);
            while(it.hasNext()) {
                uri.addParameter(it.nextKey(), it.nextValue());
            }
        }
        
        attributes.put("href", uri.toString());
        
        writer
            .tag("a")
                .attributes(attributes);
        
        if (value != null) {
            writer.body(value);
        } else {
            content.render(context, writer);
            writer.close();
        }
    }
}
