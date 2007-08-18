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

package org.mcbain.templates;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.testng.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.mcbain.ComponentFactory;
import org.mcbain.template.Template;
import org.mcbain.template.TemplateElement;
import org.mcbain.template.TemplateLoader;
import org.mcbain.template.TemplateText;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/************************************************************************
 * Tests parsing of templates.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class TemplateLoaderTest {

    @DataProvider(name = "plain")
    public Object[][] createPlainHtmlTestData() {
        return new Object[][] {
            { "<html><head><title>Test</title></head><body></body></html>" },
            { "<html>\n\t<head>\n\t\t<title>Test</title>\n\t</head>\n\t<body>\n\t</body>\n</html>" },
            { "<html>\n\t<head>\n\t\t<title>Test</title>\n\t</head>\n\t<body>\n\t<!-- comment --></body>\n</html>" },
            { "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n<html><head><title>Test</title></head><body></body></html>" },
        };
    }
    
    @DataProvider(name = "basic")
    public Object[][] createBasicHtmlTestData() {
        return new Object[][] {
            { "<html><head><title>Test</title></head><body><h1>Heading</h1></body></html>" }
        };
    }


    /************************************************************************
     * Tests parsing plain HTML templates with with no recognised components.
     */
    
    @Test(dataProvider = "plain")
    public void testPlainHtml(String html) {
        InputStream in = new ByteArrayInputStream(html.getBytes());
        ComponentFactory factory = createMock(ComponentFactory.class);
        expect( factory.createComponent(isA(String.class)) ).andReturn(null).anyTimes();
        
        replay(factory);
        
        TemplateLoader loader = new TemplateLoader(factory);
        Template t = loader.loadTemplate("test", in);

        verify(factory);

        List<TemplateElement> children = t.root().children();
        
        assertEquals(children.size(), 1);
        assertContent(children.get(0), html);
    }
    
    
    /************************************************************************
     * Tests that an HTML template containing an id'd component is correctly
     * parsed.
     */
    
    @Test
    public void testHtmlWithIdComponent() {
        String html = "<html><head><title>Test</title></head><body><div id=\"test\">Some text</div></body></html>";
        InputStream in = new ByteArrayInputStream(html.getBytes());
        
        ComponentFactory factory = createMock(ComponentFactory.class);
        expect( factory.createComponent(isA(String.class)) ).andReturn(null).anyTimes();

        replay(factory);
        
        TemplateLoader loader = new TemplateLoader(factory);
        Template t = loader.loadTemplate("test", in);
        
        verify(factory);
        
        List<TemplateElement> children = t.root().children();
        
        assertEquals(children.size(), 3);
        assertContent(children.get(0), "<html><head><title>Test</title></head><body>");
        assertContent(children.get(2), "</body></html>");
    }
    
    
    /************************************************************************
     * Asserts that the specified template contains a template text renderer 
     * with the expected content.
     */
    
    private void assertContent(TemplateElement spec, String expected) {
        Assert.assertTrue(spec instanceof TemplateText);
        assertEquals( ((TemplateText) spec).text(), expected);
    }
}
