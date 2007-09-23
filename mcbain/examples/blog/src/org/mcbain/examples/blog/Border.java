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

package org.mcbain.examples.blog;

import org.mcbain.Container;
import org.mcbain.Renderer;
import org.mcbain.TemplateInstance;
import org.mcbain.Templated;
import org.mcbain.Writer;
import org.mcbain.components.Link;
import org.mcbain.components.Loop;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.rest.Resources;
import org.mcbain.template.TemplateFactory;


/************************************************************************
 * Border component used to render the common border around each page in
 * the blog.
 */

public class Border implements Templated, Container {

    private TemplateInstance template;

    private Loop<String> archiveLoop;
    private Link archive;
    private Renderer content;
    
    private Blog blog;
    
    /************************************************************************
     * Constructs a new instance.
     */

    public Border(final Blog blog) {
        this.blog = blog;

        archive = new Link();
        archiveLoop = new Loop<String>(blog.getArchives()) {
            public void currentValue(String value) {
                archive.value(value);
                archive.uri("archive", 
                    "name", blog.getName(), 
                    "archive", value.replace('/', '-'));
            }
        };
    }

    
    // @see org.mcbain.Templated#templateFactory(org.mcbain.template.TemplateFactory)
    
    public void templateFactory(TemplateFactory factory) {
        template = factory.instance("border");
    }
    
    
    // @see org.redneck.Container#contents(org.redneck.Renderer)
    
    public void contents(Renderer content) {
        this.content = content;
    }
    
    
    // @see org.mcbain.Renderer#render(org.mcbain.rest.Resources, org.mcbain.Writer)
    
    public void render(Resources context, Writer writer) {
        long timestamp = System.currentTimeMillis();

        template.bind(
            "archives", archiveLoop,
            "archive", archive,
            "title", "Blog: " + blog.getName(),
            "time", System.currentTimeMillis() - timestamp,
            "content", content
        );

        writer.reset();
        template.render(context, writer);
        writer.seal();
    }
}
