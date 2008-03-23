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

import java.util.HashMap;
import java.util.Map;

import org.mcbain.util.PairIterator;

/************************************************************************
 * A uri is an instance of a UriTemplate - i.e. it is a template plus a
 * map of parameters.
 */

public class Uri {

    public static final Uri NOMATCH = new Uri(false);
    
    private UriTemplate template;
    private boolean match;
    private Map<String,Object> parameters;
    
    
    /************************************************************************
     * Constructs a new instance.
     */

    public Uri(UriTemplate template) {
        this.template = template;
        this.match = true;
    }
    
    
    /************************************************************************
     * Constructs a new instance.
     */

    public Uri(boolean match) {
        this.match = match;
    }
    
    
    /************************************************************************
     * Tests to see if the this result matches the incoming string.
     * 
     * @return      True if a match, else false
     */
    
    public boolean matches() {
        return match;
    }
    
    
    /************************************************************************
     * Adds a parameter to the uri result.
     * 
     * @param   name        Parameter name
     * @param   value       Parameter value
     * @return              Uri instance
     */
    
    public Uri addParameter(String name, Object value) {
        parameters().put(name, value);
        return this;
    }

    
    /************************************************************************
     * Adds a set of parameters to the uri result from an array containing
     * alternate name/value pairs.
     * 
     * @param   parameters  Parameter array
     * @return              Uri instance
     */
    
    public Uri parameters(Object[] parameters) {
        PairIterator<String, String> it = new PairIterator<String, String>(parameters);
        while(it.hasNext()) {
            parameters().put(it.nextKey(), it.nextValue());
        }
        return this;
    }
    
    
    /************************************************************************
     * Gets the named parameter, returning null if none present.
     * 
     * @param   name        Parameter name
     * @return              Value, or null
     */
    
    public String parameter(String name) {
        Object value = parameters().get(name);
        return (value == null ? null : value.toString());
    }

    
    /************************************************************************
     * Gets the parameters collection, lazy initialising if necessary.
     * 
     * @return      Parameter map
     */
    
    public Map<String, Object> parameters() {
        if (parameters == null) {
            parameters = new HashMap<String, Object>();
        }
        return parameters;
    }

    
    @Override
    public String toString() {
        return template.build(this);
    }
}
