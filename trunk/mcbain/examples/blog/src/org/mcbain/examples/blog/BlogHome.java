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

import org.mcbain.TemplateInstance;
import org.mcbain.Templated;
import org.mcbain.Writer;
import org.mcbain.components.Value;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.rest.Resources;
import org.mcbain.template.TemplateFactory;


/************************************************************************
 * Blog home page. Contains a list of archives, and a list of the last
 * few most recent posts.
 */

public class BlogHome implements Templated {

    private TemplateInstance template;
    
    private Border border;
    private Posts posts;
    private String archive;
    
    
    /************************************************************************
     * Constructs a new instance.
     */

    public BlogHome(final Blog blog) {
        this(blog, null);
    }

    
    /************************************************************************
     * Constructs a new instance.
     */

    public BlogHome(final Blog blog, final String archive) {
        border = new Border(blog);
        this.archive = archive;
        posts = new Posts(archive == null ? blog.latestPosts() : blog.archivedPosts(archive));
    }

    // @see org.mcbain.Templated#templateFactory(org.mcbain.template.TemplateFactory)
    
    public void templateFactory(TemplateFactory factory) {
        template = factory.instance("blog");
    }

    
    // @see org.mcbain.Renderer#render(org.mcbain.rest.Resources, org.mcbain.Writer)
    
    public void render(Resources context, Writer writer) {
        template.bind(
            "border", border,
            "posts", posts,
            "postTitle", new Value(archive == null ? "Recent Posts" : "Archive: " + archive)
        );
        
        template.render(context, writer);
    }
}
