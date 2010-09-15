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

import org.mcbain.Request;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A router that routes by matching the whole request URI against a string.
 */

public class ExactPathRouter implements Router {

    private Map<String, RouteHandler> handlers;
    private RouteHandler defaultHandler = RouteHandler.NONE;

    public ExactPathRouter() {
        handlers = new LinkedHashMap<String, RouteHandler>();
    }

    @Override
    public Router add(String path, RouteHandler handler) {
        if (!handlers.containsKey(path)) {
            handlers.put(path, handler);
        }
        return this;
    }

    @Override
    public Router defaultHandler(RouteHandler handler) {
        defaultHandler = handler;
        return this;
    }

    @Override
    public RouteHandler route(Request request) {
        RouteHandler handler = handlers.get(request.uri());
        return handler != null ? handler : defaultHandler;
    }

    @Override
    public String buildPath(String path, Object... params) {
        return path;
    }
}
