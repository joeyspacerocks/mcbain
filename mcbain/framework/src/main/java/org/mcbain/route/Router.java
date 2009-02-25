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

package org.mcbain.route;

import org.mcbain.render.Renderer;
import org.mcbain.request.Request;

import java.util.*;

/**
 * Forwards requests to the appropriate controller based on the path.
 */

public class Router {

    private final List<Route> routes;

	public Router() {
		routes = new ArrayList<Route>();
	}

	public void add(Route route) {
		routes.add(route);
	}

	public Renderer route(Request request) {
        String path = request.servletRequest().getServletPath();

        for (Route route : routes) {
            Uri testUri = route.matches(path);
            if (testUri.matches()) {
                request.uri(testUri);
                return route.process(request);
            }
        }
        
		return null;
	}

    public List<Route> routes() {
        return routes;
    }
}
