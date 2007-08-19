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
import static org.easymock.EasyMock.replay;
import static org.testng.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;

import org.mcbain.template.Template;
import org.mcbain.template.TemplateElement;
import org.mcbain.template.TemplateFactory;
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

    @DataProvider(name = "wrapper")
    public Object[][] createWrappedInputStreamTestData() {
        return new Object[][] {
            { "<div>actual</div>", "<ROOT><div>actual</div></ROOT>" },
            { "<!--comment--><div>actual</div>", "<!--comment--><ROOT><div>actual</div></ROOT>" },
            { "<!-- <div>comment</div> --><div>actual</div>", "<!-- <div>comment</div> --><ROOT><div>actual</div></ROOT>" }
        };
    }


    /************************************************************************
     * Tests parsing plain HTML templates with with no recognised components.
     */
    
    @Test(dataProvider = "plain")
    public void testPlainHtml(String html) {
        InputStream in = new ByteArrayInputStream(html.getBytes());
        
        ServletContext context = createMock(ServletContext.class);
        expect( context.getResourceAsStream("/test.html") ).andReturn(in);
        replay(context);
        
        TemplateFactory templateFactory = new TemplateFactory(context);
        Template t = templateFactory.parseTemplate("test", in);

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
        
        ServletContext context = createMock(ServletContext.class);
        expect( context.getResourceAsStream("/test.html") ).andReturn(in);
        replay(context);
        
        TemplateFactory templateFactory = new TemplateFactory(context);
        Template t = templateFactory.parseTemplate("test", in);
        
        List<TemplateElement> children = t.root().children();
        
        assertEquals(children.size(), 3);
        assertContent(children.get(0), "<html><head><title>Test</title></head><body>");
        assertContent(children.get(2), "</body></html>");
    }
    
    
    /************************************************************************
     * Tests that an HTML template with no root element is correctly loaded
     * and parsed. Such HTML may form the body of a component template.
     */
    
    @Test
    public void testHtmlWithNoRoot() {
        String html = "<div>Part one</div><div>Part two</div>";
        InputStream in = new ByteArrayInputStream(html.getBytes());
        
        ServletContext context = createMock(ServletContext.class);
        expect( context.getResourceAsStream("/test.html") ).andReturn(in);
        replay(context);
        
        TemplateFactory templateFactory = new TemplateFactory(context);
        Template t = templateFactory.parseTemplate("test", in);
        
        List<TemplateElement> children = t.root().children();
        
        assertEquals(children.size(), 1);
        assertContent(children.get(0), "<div>Part one</div><div>Part two</div>");
    }
    

    /************************************************************************
     * Tests that the input stream wrapper inserts the fake root element in
     * the correct place.
     */
    
    @Test(dataProvider = "wrapper")
    public void testWrappedInputStream(String input, String expected) throws IOException {
        InputStream in = new TemplateFactory.WrappedInputStream( new ByteArrayInputStream(input.getBytes()) );
        
        StringBuilder actual = new StringBuilder();
        int b = 0;
        while ((b = in.read()) > 0) {
            actual.append((char) b);
        }
        
        assertEquals(actual.toString(), expected);
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
