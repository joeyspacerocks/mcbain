//-----------------------------------------------------------------------
// Copyright Can Factory Limited, UK 
// http://www.canfactory.com - mailto:info@canfactory.com
//
// The copyright to the computer program(s) (source files, compiled 
// files and documentation) herein is the property of Can Factory 
// Limited, UK.
// The program(s) may be used and/or copied only with the written 
// permission of Can Factory Limited or in accordance with the terms 
// and conditions stipulated in the agreement/contract under which
// the program(s) have been supplied.
//-----------------------------------------------------------------------

package org.mcbain.routes;

import org.mcbain.response.Response;
import org.mcbain.test.MockRequest;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for a route handler base class that calls a method based on the HTTP method.
 */

@Test
public class MethodRouteHandlerTest {
    private MockRequest request;

    public void setup() {
        request = new MockRequest("/path");
    }

    public void shouldRouteGetRequestToGetMethod() {
        final Response response = mock(Response.class);

        RouteHandler handler = new MethodRouteHandler() {
            public Response get() {
                return response;
            }
        };

        assertEquals(handler.handle(request), response);
    }
}
