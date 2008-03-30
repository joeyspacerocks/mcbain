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
import org.mcbain.template.Element;


/************************************************************************
 * Input field component.
 */

public class Input implements Renderer, Elemental {

    private Object value;
    private Element element;

    public Input() {
    }
    
    public Input(Object value) {
    	value(value);
	}
    
    public void element(Element element) {
        this.element = element;
    }

    
    public Input value(Object value) {
        this.value = value;
        return this;
    }

    
	// FIXME: unique id/name when in loop ...
    
    public void render(Context context, Writer writer) {
    	if (element.tag().equals("textarea")) {
            writer
            .tag("textarea")
	            .attribute("name", element.id())
	            .attribute("id", element.id())
	            .attributes(element)
        	.print( value == null ? "" : value.toString(), false)
        	.close();
            
    	} else {
    		element.attribute("value", value);
	    	
	        writer
	            .emptyTag("input")
	            .attribute("name", element.id())
	            .attribute("id", element.id())
	            .attributes(element);
    	}
    }
}
