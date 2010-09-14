/*
 * Copyright 2010 Joe Trewin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mcbain.examples.blog;

import org.mcbain.Request;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.examples.blog.model.BlogService;
import org.mcbain.examples.blog.model.Post;
import org.mcbain.render.RenderContext;
import org.mcbain.render.RenderedResponse;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.response.Response;
import org.mcbain.routes.MethodRouteHandler;
import org.mcbain.routes.Router;
import org.mcbain.routes.WildcardPathRouter;


/**
 * Entry point for the blog application.
 */

public class BlogApplication {

	public Router buildRouter() {
		final BlogService blogService = new BlogService();

        return new WildcardPathRouter()
            .add("/", new MethodRouteHandler() {
                public Response get(Request request) {
                    return new RenderedResponse(new Renderer() {
                        public void render(RenderContext context, Writer writer) {
                            context.template("index").render(context, writer);
                        }
                    });
                }
            })
            .add("/blog/(*:blog)", new MethodRouteHandler() {
                public Response get(Request request) {
                    Blog blog = blogService.getBlog(request.param("blog"));

                    if (blog == null) {
                        return null;
                    }

    				return new RenderedResponse(new BlogHome(blog, null));
                }
            })
            .add("/blog/(*:blog)/newpost", new MethodRouteHandler() {
                public Response get(Request request) {
                    Blog blog = blogService.getBlog(request.param("blog"));

                    if (blog == null) {
                        return null;
                    }

                    return new RenderedResponse(new NewPost(blog));
                }

                public Response post(Request request) {
                    Blog blog = blogService.getBlog(request.param("blog"));

                    if (blog == null) {
                        return null;
                    }

                    blog.addPost(request.param("title"), request.param("content"));
                    return new RenderedResponse(new BlogHome(blog, null));
                }
            })
            .add("/blog/(*:blog)/(*:archive)/(*:post)", new MethodRouteHandler() {
                public Response get(Request request) {
                    Blog blog = blogService.getBlog(request.param("blog"));

                    if (blog == null) {
                        return null;
                    }

                    Post post = blog.getPost(request.param("post"));
                    return (post == null ? null : new RenderedResponse(new FullPost(blog, post)));
                }
            })
            .add("/blog/(*:blog)/(*:archive)", new MethodRouteHandler() {
                public Response get(Request request) {
                    Blog blog = blogService.getBlog(request.param("blog"));

                    if (blog == null) {
                        return null;
                    }

                    String archive = request.param("archive").replace('-', '/');
                    return new RenderedResponse(new BlogHome(blog, archive));
                }
            });
	}
}
