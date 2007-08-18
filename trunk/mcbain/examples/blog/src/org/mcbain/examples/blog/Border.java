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

import org.mcbain.Components;
import org.mcbain.Container;
import org.mcbain.Renderer;
import org.mcbain.Templated;
import org.mcbain.Writer;
import org.mcbain.components.Loop;
import org.mcbain.components.Value;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.template.Template;


/************************************************************************
 * Border component used to render the common border around each page in
 * the blog.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class Border implements Templated, Container {

    private Template template;
    private Components components;
    
    private Renderer content;
    private long timestamp;
    
    
    /************************************************************************
     * Constructs a new instance.
     */

    public Border() {
        Value title = new Value() {
            public Object value() {
                return "Blog: " + blog().getName();
            }
        };
        
        Value time = new Value() {
            public Object value() {
                return String.valueOf(System.currentTimeMillis() - timestamp);
            }
        };

        final Loop<String> archives = new Loop<String>() {
            public Iterable<String> source() {
                return blog().getArchives();
            }
        };
        
        Value archive = new Value() {
            public Object value() {
                return archives.currentValue();
            }
        };

        components = new Components()
            .add("title", title)
            .add("time", time)
            .add("archives", archives)
            .add("archive", archive);
    }

    
    /************************************************************************
     * Gets the blog.
     * 
     * @return      Blog
     */
    
    public Blog blog() {
        return null;
    }

    
    // @see org.redneck.Templated#attachComponent(org.redneck.template.Template)
    
    public void attachTemplate(Template template) {
        this.template = template;
        components.put("content", content);
    }
    
    
    // @see org.redneck.Templated#templateName()
    
    public String templateName() {
        return "border";
    }
    
    
    // @see org.redneck.Container#contents(org.redneck.Renderer)
    
    public void contents(Renderer content) {
        this.content = content;
    }
    
    
    // @see org.redneck.Renderer#render(org.redneck.Writer)

    public void render(Writer writer) {
        timestamp = System.currentTimeMillis();
        
        writer.reset();
        template.render(writer, components);
        writer.seal();
    }
}
