// Copyright 2007 Joe Trewin
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.mcbain.rest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

/************************************************************************
 * Unit tests for UriTemplate.
 */

public class UriTemplateTest {

    /************************************************************************
     * Tests uri matching with a single string.
     */
    
    @Test
    public void testStringMatch() {
        UriTemplate uri1 = new UriTemplate("/");
        
        assertTrue( uri1.match("/").matches() );
        assertFalse( uri1.match("/bad").matches() );
        
        UriTemplate uri2 = new UriTemplate("/part1/part2");
        
        assertTrue( uri2.match("/part1/part2").matches() );
        assertFalse( uri2.match("/bad").matches() );
        assertFalse( uri2.match("/part1").matches() );
        assertFalse( uri2.match("/part1/").matches() );
    }
    
    
    /************************************************************************
     * Tests uri matching with a single parameter.
     */
    
    @Test
    public void testSingleParameterMatch() {
        UriTemplate uri = new UriTemplate("/$p");
        
        Uri result = uri.match("/test");
        assertTrue(result.matches());
        assertEquals(result.parameter("p"), "test");
    }
    
    
    /************************************************************************
     * Tests uri matching with a multiple parameter.
     */
    
    @Test
    public void testMultipleParameterMatch() {
        UriTemplate uri = new UriTemplate("/$p1/$p2");
        
        Uri result = uri.match("/test1/test2");
        assertTrue(result.matches());
        assertEquals(result.parameter("p1"), "test1");
        assertEquals(result.parameter("p2"), "test2");
    }
}
