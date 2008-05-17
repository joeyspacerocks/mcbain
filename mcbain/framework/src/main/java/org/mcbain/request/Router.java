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

package org.mcbain.request;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.mcbain.Renderer;

/************************************************************************
 * Forwards requests to the appropriate controller based on the path.
 */

public class Router {

    private Map<UriTemplate, ControllerChain> routes;

    
    public Router() {
        routes = new LinkedHashMap<UriTemplate, ControllerChain>();
    }

    
    /************************************************************************
     * Adds a new uri/controller to the resource pool.
     * 
     * @param   path            Path to route from
     * @param   controller      Controller chain to route requests to
     */
    
    public void add(String path, ControllerChain controller) {
        UriTemplate template = new UriTemplate(path);
    	routes.put(template, controller);
    }
    
    
    public Renderer route(Request request) {
    	UriTemplate uri = matchUri(request);
    	return uri == null ? null : process(uri, request);
    }
    
    
    private UriTemplate matchUri(Request request) {
    	String path = request.servletRequest().getServletPath();
    	
        Set<UriTemplate> templates = routes.keySet();
        for (UriTemplate template : templates) {
            Uri testUri = template.match(path);
            if (testUri.matches()) {
            	request.uri(testUri);
            	return template;
            }
        }
        
        return null;
    }
    
    private Renderer process(UriTemplate uri, Request request) {
    	String method = request.servletRequest().getMethod().toUpperCase();
        ControllerChain c = routes.get(uri);

        boolean handled = false;
        for (Interceptor interceptor : c.interceptors()) {
        	handled = interceptor.intercept(request);
        	if (!handled) {
        		break;
        	}
        }
        
        if (handled) {
	    	if ("GET".equals(method)) {
	    		return c.controller().get(request);
	    		
	    	} else if ("POST".equals(method)) {
	    		return c.controller().post(request);
	    	}
        }
    	
    	return null;
    }
}
