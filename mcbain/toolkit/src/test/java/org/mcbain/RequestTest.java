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

package org.mcbain;

import org.mcbain.test.MockHttpServletRequest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for the request wrapper core class.
 */

@Test
public class RequestTest {
    private MockHttpServletRequest httpRequest;
    private Request request;

    @BeforeMethod
    public void setup() {
        httpRequest = new MockHttpServletRequest();
        request = new Request(httpRequest);
    }
}
