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

package org.mcbain.test;

import org.mcbain.Request;

/**
 * Mock request for use in tests. Provides programmatic creation of request situations.
 */

public class MockRequest extends Request {

    public MockRequest(String uri) {
        super(new MockHttpServletRequest(uri));
    }

    public MockRequest() {
        super(new MockHttpServletRequest());
    }

    public MockRequest method(String method) {
        mockRequest().method(method);
        return this;
    }

    private MockHttpServletRequest mockRequest() {
        return (MockHttpServletRequest) httpRequest();
    }
}
