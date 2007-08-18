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
 * Link component.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class Link implements Renderer, Container, Elemental {

    private Attributes attributes;
    private Renderer content;
    
    
    // @see org.redneck.Renderer#render(org.redneck.Writer)

    public void render(Writer writer) {
        writer.tag("a").attributes(attributes);
        content.render(writer);
        writer.close();
    }

    
    // @see org.redneck.Container#contents(org.redneck.Renderer)
    
    public void contents(Renderer content) {
        this.content = content;
    }
    
    
    // @see org.redneck.Attributes#attributes(java.lang.String, java.util.Map)

    public void element(String element, Attributes attributes) {
        this.attributes = attributes;
    }
}
