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

/**
 * A router takes an incoming request and locates a corresponding
 * route handler.
 */

public interface Router {

    /**
     * Adds a route handler for a path. The interpretation of the path
     * is up to the implementation of the router.
     *
     * @param   path        Path that triggers the route
     * @param   handler     Handler returned if the route is triggered
     * @return  The router, allowing calls to be chained
     */

    public Router add(String path, RouteHandler handler);

    /**
     * Sets the default handler. This handler will be returned if no
     * other route applied to the incoming request.
     *
     * @param   handler     Handler returned if no applicable route found
     * @return  The router, allowing calls to be chained
     */

    public Router defaultHandler(RouteHandler handler);

    /**
     * Identifies the route handler that should handle the request. If
     * none can be found the default handler should be returned. If no
     * default handler has been set, null should be returned.
     *
     * If more than one route applies to the request, the order they
     * were added to the router should be used to decide which handler
     * is to be returned.
     *
     * If the router implementation supports named parameters when
     * path matching, any identified parameters should be added to the
     * request.
     *
     * @param   request     Incoming request
     * @return  Appropriate or default route handler, or null
     */

    public RouteHandler route(Request request);

    /**
     * Builds a path using the supplied path as a template and inserting
     * any supplied parameters. This is typically used to build a url to
     * a route that uses wildcards or pattern matching to identify portions
     * of the url.
     *
     * @param   path        Path to merge parameters with
     * @param   params      Array of zero or more parameters
     * @return  Compiled path
     */

    public String buildPath(String path, Object... params);
}
