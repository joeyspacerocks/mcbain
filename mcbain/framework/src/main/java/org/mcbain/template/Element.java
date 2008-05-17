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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/************************************************************************
 * Collection of attributes from a markup element.
 */

public class Element {
    
	private String tag;
	private String id;
	private Map<String, String> attributes;

	private Element passThrough;
	private boolean proxy;

    public Element(String tag, String id) {
    	this.tag = tag;
    	this.id = id;
    }

    public Element(Element element) {
    	passThrough = element;
    	proxy = true;
    }
    
    public Element attribute(String key, Object value) {
    	if (attributes == null) {
    		attributes = new LinkedHashMap<String, String>();
    	}
    	
    	if (proxy) {
    		attributes.putAll(passThrough.attributes());
    	}
    	
    	attributes.put(key, value == null ? null : value.toString());
    	return this;
    }

    public String tag() {
    	return proxy ? passThrough.tag() : tag;
    }

    public String id() {
    	return proxy ? passThrough.id() : id;
    }
    
    public Set<String> attributeNames() {
    	return attributes().keySet();
    }

    public String attribute(String key) {
    	return attributes().get(key);
    }
    
    protected Map<String, String> attributes() {
    	if (attributes == null) {
    		if (proxy) {
    			return passThrough.attributes();
    		} else {
    			return Collections.emptyMap();
    		}

    	} else {
        	return attributes;
    	}
    }
}
