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

package org.mcbain.components;

import org.mcbain.Writer;
import org.testng.Assert;
import org.testng.annotations.Test;

/************************************************************************
 * Unit test for simple value component.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class ValueTest {

    /************************************************************************
     * Tests that nothing is rendered for a valueless, tagless component.
     */
    
    @Test
    public void testNoValueNoTag() {
        Value c = new Value();
        
        Writer w = new Writer();
        
        c.render(null, w);
        
        Assert.assertEquals(w.toString(), "");
    }
    
    
    /************************************************************************
     * Tests that nothing is rendered for a valueless, tag component.
     */
    
    @Test
    public void testNoValueWithTag() {
        Value c = new Value();
        
        c.element("tag", null);
        
        Writer w = new Writer();
        
        c.render(null, w);
        
        Assert.assertEquals(w.toString(), "");
    }
    
    
    /************************************************************************
     * Tests that the value is rendered for a tagless component.
     */
    
    @Test
    public void testValueNoTag() {
        Value c = new Value("content");
        
        Writer w = new Writer();
        
        c.render(null, w);
        
        Assert.assertEquals(w.toString(), "content");
    }
    
    
    /************************************************************************
     * Tests that the tagged value is rendered for a component.
     */
    
    @Test
    public void testValueWithTag() {
        Value c = new Value("content");
        
        c.element("tag", null);
        
        Writer w = new Writer();
        
        c.render(null, w);
        
        Assert.assertEquals(w.toString(), "<tag>content</tag>");
    }
}
