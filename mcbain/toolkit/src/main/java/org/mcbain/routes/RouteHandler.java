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
import org.mcbain.response.Response;

/**
 * Handles the resolution of a route by a router by producing a response.
 */

public interface RouteHandler {

    public static final RouteHandler NONE = new RouteHandler() {
        @Override
        public Response handle(Request request) {
            return null;
        }
    };

    public Response handle(Request request);
}
