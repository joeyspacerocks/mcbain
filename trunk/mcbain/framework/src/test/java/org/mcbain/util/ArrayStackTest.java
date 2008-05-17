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

package org.mcbain.util;

import org.testng.Assert;
import org.testng.annotations.Test;

/************************************************************************
 * Unit tests the array stack implementation.
 */

public class ArrayStackTest {

    /************************************************************************
     * Tests that a pop from an empty stack returns null.
     */
    
    @Test
    public void testPopFromEmptyStack() {
        ArrayStack<String> stack = new ArrayStack<String>();
        
        Assert.assertNull( stack.pop() );
    }
    
    
    /************************************************************************
     * Tests that a single push followed by a pop returns correct element.
     */
    
    @Test
    public void testPushThenPop() {
        ArrayStack<String> stack = new ArrayStack<String>();
        
        stack.push("test");
        
        Assert.assertEquals( stack.pop(), "test" );
        Assert.assertNull( stack.pop() );
    }
    
    
    /************************************************************************
     * Tests that a single push followed by a peek returns correct element.
     */
    
    @Test
    public void testPushThenPeek() {
        ArrayStack<String> stack = new ArrayStack<String>();
        
        stack.push("test");
        
        Assert.assertEquals( stack.peek(), "test" );
        Assert.assertEquals( stack.pop(), "test" );
        Assert.assertNull( stack.pop() );
    }
    
    
    /************************************************************************
     * Tests that a stack with several items maintains itself.
     */
    
    @Test
    public void testMultipleElements() {
        ArrayStack<String> stack = new ArrayStack<String>();
        
        stack.push("test1");
        stack.push("test2");
        stack.push("test3");
        
        Assert.assertEquals( stack.pop(), "test3" );
        Assert.assertEquals( stack.pop(), "test2" );
        Assert.assertEquals( stack.pop(), "test1" );
        Assert.assertNull( stack.pop() );
    }
}
