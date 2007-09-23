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
import org.mcbain.rest.Context;
import org.mcbain.rest.Uri;
import org.mcbain.template.Attributes;
import org.mcbain.util.PairIterator;

/************************************************************************
 * Link component.
 */

public class Link implements Renderer, Elemental {

    private Attributes attributes;
    private String resourceId;
    private PairIterator<String, String> parameters;
    private String value;
    
    public void uri(String id, Object... parameters) {
        this.resourceId = id;
        this.parameters = new PairIterator<String, String>(parameters);
    }

    public void value(String value) {
        this.value = value;
    }
    
    // @see org.redneck.Attributes#attributes(java.lang.String, java.util.Map)

    public void element(String element, Attributes attributes) {
        this.attributes = new Attributes(attributes);
    }
    
    // @see org.mcbain.Renderer#render(org.mcbain.rest.Resources, org.mcbain.Writer)
    
    public void render(Context context, Writer writer) {
        Uri uri = context.resources().link(resourceId);

        while(parameters.hasNext()) {
            uri.addParameter(parameters.nextKey(), parameters.nextValue());
        }
        
        attributes.put("href", uri.toString());
        
        writer
            .tag("a")
                .attributes(attributes)
                .body(value);
    }
}
