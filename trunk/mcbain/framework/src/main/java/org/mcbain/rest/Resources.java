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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.mcbain.Renderer;

/************************************************************************
 * Collection of resources and controllers.
 */

public class Resources {

    private Map<String, UriTemplate> uris;
    private Map<String, Controller> controllers;

    
    /************************************************************************
     * Constructs a new instance.
     */

    public Resources() {
        uris = new HashMap<String, UriTemplate>();
        controllers = new HashMap<String, Controller>();
    }

    
    /************************************************************************
     * Adds a new uri/controller to the resource pool.
     * 
     * @param   id              Resource id
     * @param   uri             Uri template
     * @param   controller      Controller to route requests to
     * @return                  Resources instance
     */
    
    public Resources add(String id, String uri, Controller controller) {
        UriTemplate template = new UriTemplate(uri);
        uris.put(id, template);
        controllers.put(id, controller);
        return this;
    }
 
    
    public Uri link(String id) {
        UriTemplate template = uris.get(id);
        if (template == null)
            throw new IllegalArgumentException("Unknown resource id: " + id);
        
        return template.emptyUri();
    }
    
    
    public Uri link(String id, Object[] parameters) {
        UriTemplate template = uris.get(id);
        if (template == null)
            throw new IllegalArgumentException("Unknown resource id: " + id);
        
        Uri uri = template.emptyUri();
        uri.parameters(parameters);
        return uri;
    }
    
    
    /************************************************************************
     * Routes a URI to a controller, and executes it.
     * 
     * @param   uri     	URI
     * @param	method  	HTTP method
     * @return          	Result of running controller
     */
    
    public Renderer route(String uri, String method, HttpServletRequest request) {
        Controller c = null;
        Uri result = null;
        
        Set<String> templates = uris.keySet();
        for (String id : templates) {
            UriTemplate template = uris.get(id);
            result = template.match(uri);
            if (result.matches()) {
                c = controllers.get(id);
                break;
            }
        }
        
        Renderer r = null;
        if (c != null) {
        	if ("GET".equalsIgnoreCase(method)) {
        		r = c.get(result);
        	} else if ("POST".equalsIgnoreCase(method)) {
        		r = c.post(result, request);
        	}
        }
        
        return r;
    }
}
