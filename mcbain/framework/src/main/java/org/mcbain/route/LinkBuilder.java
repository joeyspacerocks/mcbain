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

package org.mcbain.route;

/**
 * Builds URLs to named routes, or arbitrary locations.
 */

public class LinkBuilder {
    private Router router;

    public LinkBuilder(Router router) {
        this.router = router;
    }

    // TODO: make context relative
	public String link(String path) {
		return path;
	}

    public String linkRoute(String routeName, Object... parameters) {
        Route route = router.namedRoute(routeName);
        if (route == null) {
            throw new RuntimeException("Named route does not exist: " + routeName);
        }

        return route.pattern().build(parameters);
    }
}
