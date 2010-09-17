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
import org.mcbain.routes.*;
import org.mcbain.template.TemplateFactory;
import org.mcbain.validation.PropertyValidator;
import org.mcbain.validation.RequiredStringValidator;
import org.mcbain.validation.ValidationResult;


/**
 * Entry point for the blog application.
 */

public class BlogApplication {

	public Router buildRouter(final TemplateFactory templateFactory) {
		final BlogService blogService = new BlogService();
        final NamedRouter router = new NamedRouter(new WildcardPathRouter());

        router
            .add("home", "/", new MethodRouteHandler() {
                public Response get(Request request) {
                    return new RenderedResponse(new Renderer() {
                        public void render(RenderContext context, Writer writer) {
                            templateFactory.instance("index").render(context, writer);
                        }
                    });
                }
            })
            .add("blog", "/blog/(*:blog)", new MethodRouteHandler() {
                public Response get(Request request) {
    				return new RenderedResponse(new BlogHome(request.resource("blog", Blog.class), null, router, templateFactory));
                }
            })
            .add("newpost", "/blog/(*:blog)/newpost", new MethodRouteHandler() {
                public Response get(Request request) {
                    return new RenderedResponse(new NewPost(request.resource("blog", Blog.class), router, templateFactory, ValidationResult.NONE));
                }

                public Response post(Request request) {
                    Blog blog = request.resource("blog", Blog.class);

                    PropertyValidator validator = new PropertyValidator();
                    validator.addPropertyRule("title", new RequiredStringValidator());
                    validator.addPropertyRule("content", new RequiredStringValidator());

                    ValidationResult result = validator.validate("post", request);
                    if (result.passed()) {
                        blog.addPost(request.param("title"), request.param("content"));
                        return new RenderedResponse(new BlogHome(blog, null, router, templateFactory));

                    } else {
                        return new RenderedResponse(new NewPost(blog, router, templateFactory, result));
                    }
                }
            })
            .add("post", "/blog/(*:blog)/(*:archive)/(*:post)", new MethodRouteHandler() {
                public Response get(Request request) {
                    Blog blog = request.resource("blog", Blog.class);

                    Post post = blog.getPost(request.param("post"));
                    return (post == null ? null : new RenderedResponse(new FullPost(blog, post, router, templateFactory)));
                }
            })
            .add("archive", "/blog/(*:blog)/(*:archive)", new MethodRouteHandler() {
                public Response get(Request request) {
                    return new RenderedResponse(new BlogHome(request.resource("blog", Blog.class), request.param("archive"), router, templateFactory));
                }
            });

        RouteHandler blogLocator = new RouteHandler() {
            public Response handle(Request request) {
                RouteHandler handler = router.route(request);
                if (handler == RouteHandler.NONE) return null;

                Blog blog = blogService.getBlog(request.param("blog"));
                if (blog == null) return null;

                request.resource("blog", blog);
                return handler.handle(request);
            }
        };

        return new WildcardPathRouter()
            .add("/blog/(*:blog)", blogLocator)
            .add("/blog/(*:blog)/**", blogLocator)
            .defaultHandler(new RouteHandler() {
                public Response handle(Request request) {
                    return router.route(request).handle(request);
                }
            });
	}
}
