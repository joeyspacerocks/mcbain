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
    
	private Element passThrough;
	private String tag;
	private Map<String, String> attributes;
	
    public Element(String tag) {
    	this.tag = tag;
    }

    public Element(Element element) {
    	this.passThrough = element;
    }
    
    public Element attribute(String key, Object value) {
    	attributes().put(key, value == null ? null : value.toString());
    	return this;
    }

    public String tag() {
    	return (passThrough == null ? tag : passThrough.tag());
    }

    public Set<String> attributeNames() {
    	if (attributes == null) {
    		return Collections.emptySet();
    	} else {
    		return attributes.keySet();
    	}
    }

    public String attribute(String key) {
    	String value = null;
    	
    	if (attributes != null) {
    		value = attributes.get(key);
    	}
    	
    	if (value == null && passThrough != null) {
    		value = passThrough.attribute(key);
    	}
    	
    	return value;
    }
    
    private Map<String, String> attributes() {
    	if (attributes == null) {
    		attributes = new LinkedHashMap<String, String>();
    	}
    	
    	return attributes;
    }
}
