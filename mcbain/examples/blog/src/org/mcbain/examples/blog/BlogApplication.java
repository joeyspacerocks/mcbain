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

import javax.servlet.http.HttpServletRequest;

import org.mcbain.Application;
import org.mcbain.Renderer;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.examples.blog.model.BlogService;
import org.mcbain.examples.blog.model.Post;
import org.mcbain.rest.Context;
import org.mcbain.rest.Controller;
import org.mcbain.rest.Uri;


/************************************************************************
 * Entry point for the blog application.
 */

public class BlogApplication implements Application{

    private BlogService blogService;
    
    
    public BlogApplication() {
        this.blogService = new BlogService();
    }
    
    
    public void initialise(final Context context) {
        context.resources()
        
            .add("index", "/", new Controller() {
                public Renderer get(Uri uri) {
                    return context.template("index");
                }
            })
            
            .add("blog", "/blog/$name", new Controller() {
                public Renderer get(Uri uri) {
                    Blog blog = blogService.getBlog(uri.parameter("name"));
                    return (blog == null ? null : new BlogHome(blog));
                }
            })
            
            .add("archive", "/blog/$name/$archive", new Controller() {
                public Renderer get(Uri uri) {
                    Blog blog = blogService.getBlog(uri.parameter("name"));
                    if (blog != null) {
                        String archive = uri.parameter("archive").replace('-', '/');
                        return new BlogHome(blog, archive);
                    } else {
                        return null;
                    }
                }
            })
            
            .add("post", "/blog/$name/$archive/$post", new Controller() {
                public Renderer get(Uri uri) {
                    Blog blog = blogService.getBlog(uri.parameter("name"));
                    if (blog != null) {
                        Post post = blog.getPost(uri.parameter("post"));
                        return new FullPost(blog, post);
                    } else {
                        return null;
                    }
                }
            })
            
            .add("newpost", "/blog/$name/newpost", new Controller() {
            	public Renderer get(Uri uri) {
                    Blog blog = blogService.getBlog(uri.parameter("name"));
            		return new NewPost(blog);
            	}
            	
            	public Renderer post(Uri uri, HttpServletRequest request) {
                    Blog blog = blogService.getBlog(uri.parameter("name"));
                    
                    String title = request.getParameter("title");
                    String content = request.getParameter("content");
                    
                    blog.addPost(title, content);
                    
                    return new BlogHome(blog);
            	}
            });
    }
}
