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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Tests the named routes router.
 */

@Test
public class NamedRouterTest {
    private NamedRouter router;

    @BeforeMethod
    public void setup() {
        router = new NamedRouter(new ExactPathRouter());
    }

    public void shouldBuildLinkForNamedRoute() {
        router.add("route", "/path", new MockResponseHandler());

        String path = router.buildNamedPath("route");
        assertEquals(path, "/path");
    }

    public void shouldBuildLinkForNamedRouteWithParameters() {
        router.add("route", "/path", new MockResponseHandler());

        String path = router.buildNamedPath("route");
        assertEquals(path, "/path");
    }
}
