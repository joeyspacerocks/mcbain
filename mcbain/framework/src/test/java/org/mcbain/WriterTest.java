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

package org.mcbain;

import org.testng.Assert;
import org.testng.annotations.Test;

/************************************************************************
 * Unit tests for the Writer markup type.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class WriterTest {

    /************************************************************************
     * Tests writing an empty tag with no attributes.
     */
    
    @Test
    public void testEmptyTagNoAttributes() {
        Writer writer = new Writer();
        
        writer.emptyTag("tag");
        
        assertWriter(writer, "<tag />");
    }
    
    
    /************************************************************************
     * Tests writing an empty tag with attributes.
     */
    
    @Test
    public void testEmptyTagWithAttributes() {
        Writer writer = new Writer();
        
        writer
            .emptyTag("tag")
            .attribute("attr1", "value1")
            .attribute("attr2", "value2");
        
        assertWriter(writer, "<tag attr1=\"value1\" attr2=\"value2\" />");
    }
    
    
    /************************************************************************
     * Tests writing a tag with no attributes or body.
     */
    
    @Test
    public void testTagNoAttributesNoBody() {
        Writer writer = new Writer();
        
        writer.tag("tag");
        
        assertWriter(writer, "<tag></tag>");
    }
    
    
    /************************************************************************
     * Tests writing a tag with attributes but no body.
     */
    
    @Test
    public void testTagWithAttributesNoBody() {
        Writer writer = new Writer();
        
        writer
            .tag("tag")
            .attribute("attr1", "value1")
            .attribute("attr2", "value2");
        
        assertWriter(writer, "<tag attr1=\"value1\" attr2=\"value2\"></tag>");
    }
    
    
    /************************************************************************
     * Tests writing a tag with no attributes but with a body.
     */
    
    @Test
    public void testTagNoAttributesWithBody() {
        Writer writer = new Writer();
        
        writer.tag("tag").body("Some body text");
        
        assertWriter(writer, "<tag>Some body text</tag>");
    }
    
    
    /************************************************************************
     * Tests writing straight content.
     */
    
    @Test
    public void testPrint() {
        Writer writer = new Writer();
        writer.print("Some content", false);
        assertWriter(writer, "Some content");
    }
    
    
    
    /************************************************************************
     * Tests writing unsafe content.
     */
    
    @Test
    public void testPrintUnsafeContent() {
        Writer writer = new Writer();
        writer.print("Some <b>unsafe</b> &amp; content", false);
        assertWriter(writer, "Some &lt;b&gt;unsafe&lt;/b&gt; &amp;amp; content");
    }
    

    /************************************************************************
     * Tests writing raw content.
     */
    
    @Test
    public void testPrintRawContent() {
        Writer writer = new Writer();
        writer.print("Some <b>unsafe</b> &amp; content", true);
        assertWriter(writer, "Some <b>unsafe</b> &amp; content");
    }
    

    /************************************************************************
     * Tests writing nested content.
     */
    
    @Test
    public void testNestedWriter() {
        Writer nested = new Writer();
        nested.tag("test").body("content");
            
        Writer writer = new Writer();
        writer.tag("outer")
            .print(nested)
            .close();
        
        assertWriter(writer, "<outer><test>content</test></outer>");
    }

    
    /************************************************************************
     * Tests closing a tag.
     */
    
    @Test
    public void testClose() {
        Writer writer = new Writer();
        writer.tag("test").close();
        
        assertWriter(writer, "<test></test>");
    }

    
    /************************************************************************
     * Tests that writing a tag, directly followed by another one, correctly
     * closes the first one.
     */
    
    @Test
    public void testOpenTagFollowedByTag() {
        Writer writer = new Writer();
        writer.tag("first").tag("second");
        
        assertWriter(writer, "<first><second></second></first>");
    }
    
    
    /************************************************************************
     * Asserts that a writer contains the expected content.
     * 
     * @param   writer      Writer to check
     * @param   expected    Expected content
     */
    
    private void assertWriter(Writer writer, String expected) {
        Assert.assertEquals(writer.toString(), expected);
    }
}
