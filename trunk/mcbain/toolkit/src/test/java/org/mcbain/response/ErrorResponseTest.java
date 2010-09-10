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

package org.mcbain.response;

import org.mcbain.test.MockHttpServletResponse;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for the error response implementation.
 */

@Test
public class ErrorResponseTest {
    private MockHttpServletResponse httpResponse;

    public ErrorResponseTest() {
        this.httpResponse = new MockHttpServletResponse();
    }

    public void shouldOutputCodeOnly() {
        ErrorResponse response = new ErrorResponse(404);
        response.commit(httpResponse);

        assertTrue(httpResponse.hasStatus(404));
    }

    public void shouldOutputCodeAndMessageOnly() {
        ErrorResponse response = new ErrorResponse(404, "error");
        response.commit(httpResponse);

        assertTrue(httpResponse.hasStatus(404));
        assertTrue(httpResponse.hasContent("error"));
    }

    public void shouldOutputCodeAndPassToNextResponse() {
        Response nextResponse = mock(Response.class);
        ErrorResponse response = new ErrorResponse(404, nextResponse);
        response.commit(httpResponse);

        assertTrue(httpResponse.hasStatus(404));
        verify(nextResponse, times(1));
    }
}
