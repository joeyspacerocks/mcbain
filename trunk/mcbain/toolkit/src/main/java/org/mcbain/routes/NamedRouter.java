/*
 * Copyright 2010 Joe Trewin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mcbain.routes;

import java.util.HashMap;
import java.util.Map;

/**
 * Decorates a router so that routes with names can be added. Such
 * routes can then be looked up by their name to build paths with
 * parameters.
 */

public class NamedRouter extends DelegatingRouter {
    private Map<String, Route> routes;

    public NamedRouter(Router router) {
        super(router);
        routes = new HashMap<String, Route>();
    }

    public NamedRouter add(String name, String uri, RouteHandler handler) {
        routes.put(name, new Route(uri, handler));
        add(uri, handler);
        return this;
    }

    @Override
    public String buildPath(String path, Object... params) {
        Route route = routes.get(path);

        if (route == null) {
            throw new IllegalArgumentException("Cannot build path for unknown route: " + path);
        }

        try {
            return super.buildPath(route.path, params);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error building path for route: " + path, e);
        }
    }
    
    private class Route {
        public String path;
        public RouteHandler handler;

        public Route(String path, RouteHandler handler) {
            this.path = path;
            this.handler = handler;
        }

        public String buildLink(Object... params) {
            return buildPath(path, params);
        }
    }
}
