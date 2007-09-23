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

import org.mcbain.Application;
import org.mcbain.Renderer;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.examples.blog.model.BlogService;
import org.mcbain.rest.Controller;
import org.mcbain.rest.Resources;
import org.mcbain.rest.Uri;
import org.mcbain.template.TemplateFactory;


/************************************************************************
 * Entry point for the blog application.
 */

public class BlogApplication implements Application{

    private BlogService blogService;
    private TemplateFactory templateFactory;
    
    
    /************************************************************************
     * Constructs a new application.
     */

    public BlogApplication(TemplateFactory templateFactory) {
        this.blogService = new BlogService();
        this.templateFactory = templateFactory;
    }
    
    
    // @see org.mcbain.Application#resources()
    
    public Resources resources() {
        
        Resources resources = new Resources();
        
        // url: /

        resources
            .add("index", "/", new Controller() {
                public Renderer get(Uri uri) {
                    return templateFactory.instance("index");
                }
            });

        // url: /blog/$name
        
        resources
            .add("blog", "/blog/$name", new Controller() {
                public Renderer get(Uri uri) {
                    Blog blog = blogService.getBlog(uri.parameter("name"));
                    if (blog != null) {
                        BlogHome bh = new BlogHome(blog);
                        bh.templateFactory(templateFactory);
                        return bh;
                    } else {
                        return null;
                    }
                }
            });

        // url: /blog/$name/$archive
        
        resources
            .add("archive", "/blog/$name/$archive", new Controller() {
                public Renderer get(Uri uri) {
                    Blog blog = blogService.getBlog(uri.parameter("name"));
                    if (blog != null) {
                        String archive = uri.parameter("archive").replace('-', '/');
                        BlogHome bh = new BlogHome(blog, archive);
                        bh.templateFactory(templateFactory);
                        return bh;
                    } else {
                        return null;
                    }
                }
            });

        return resources;
    }
}
