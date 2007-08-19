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

import java.util.List;

import org.mcbain.Components;
import org.mcbain.Templated;
import org.mcbain.Writer;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.examples.blog.model.BlogService;
import org.mcbain.examples.blog.model.Post;
import org.mcbain.template.Template;
import org.mcbain.template.TemplateFactory;


/************************************************************************
 * Blog home page. Contains a list of archives, and a list of the last
 * few most recent posts.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class HomePage implements Templated {

    private Template template;
    private Components components;
    
    private Blog blog;
    private BlogService blogService;
    
    /************************************************************************
     * Constructs a new instance.
     */

    public HomePage(BlogService service) {
        this.blogService = service;

        Border border = new Border() {
            public Blog blog() { return blog; }
        };
        
        Posts posts = new Posts() {
            public List<Post> posts() {
                return blog.latestPosts();
            }
        };

        components = new Components()
            .add("border", border)
            .add("posts", posts);
    }


    // @see org.mcbain.Templated#templateFactory(org.mcbain.template.TemplateFactory)
    
    public void templateFactory(TemplateFactory factory) {
        template = factory.findTemplate("home");
    }

    
    // @see org.redneck.Renderer#render(org.redneck.Writer)
    
    public void render(Writer writer) {
        blog = blogService.getBlog("");
        template.render(writer, components);
    }
}
