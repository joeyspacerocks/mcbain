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
import org.mcbain.binding.BeanPropertyAccessor;
import org.mcbain.binding.BeanRequestParser;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.examples.blog.model.BlogService;
import org.mcbain.examples.blog.model.Post;
import org.mcbain.render.RenderContext;
import org.mcbain.render.RenderedResponse;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.response.Response;
import org.mcbain.routes.MethodRouteHandler;
import org.mcbain.routes.NamedRouter;
import org.mcbain.routes.Router;
import org.mcbain.routes.WildcardPathRouter;
import org.mcbain.template.TemplateFactory;
import org.mcbain.validation.PropertyValidator;
import org.mcbain.validation.ValidationResult;
import org.mcbain.validation.ValidatorBuilder;


/**
 * Entry point for the blog application.
 */

public class BlogApplication {

    final BlogService blogService = new BlogService();

    /**
     * Custom route handler base class for all handlers that need to extract
     * the blog entity from the id passed through the url.
     */
    
    abstract public class BlogRouteHandler extends MethodRouteHandler {
        public Response get(Request request) {
            Blog blog = blogService.getBlog(request.param("blog"));
            Renderer renderer = blog == null ? null : get(blog, request);
            return renderer == null ? null : new RenderedResponse(renderer);
        }

        public Response post(Request request) {
            Blog blog = blogService.getBlog(request.param("blog"));
            Renderer renderer = blog == null ? null : post(blog, request);
            return renderer == null ? null : new RenderedResponse(renderer);
        }

        abstract public Renderer get(Blog blog, Request request);
        public Renderer post(Blog blog, Request request) { return null; }
    }

    /**
     * Build a router to handle the blog application urls.
     *
     * @param   templateFactory     Template factory
     * @return  Router
     */

	public Router buildRouter(final TemplateFactory templateFactory) {
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
            .add("blog", "/blog/(*:blog)", new BlogRouteHandler() {
                public Renderer get(Blog blog, Request request) {
    				return new BlogHome(blog, null, router, templateFactory);
                }
            })
            .add("newpost", "/blog/(*:blog)/newpost", new BlogRouteHandler() {
                public Renderer get(Blog blog, Request request) {
                    return new NewPost(blog, router, templateFactory, ValidationResult.NONE);
                }

                public Renderer post(Blog blog, Request request) {
                    Post post = new BeanRequestParser<Post>(Post.class).parse(request);

                    PropertyValidator validator = new ValidatorBuilder()
                        .check("title").isNotEmpty()
                        .check("content").isNotEmpty()
                        .build();

                    ValidationResult result = validator.validate("post", new BeanPropertyAccessor(post));
                    if (result.passed()) {
                        blog.addPost(post);
                        return new BlogHome(blog, null, router, templateFactory);

                    } else {
                        return new NewPost(blog, router, templateFactory, result);
                    }
                }
            })
            .add("post", "/blog/(*:blog)/(*:archive)/(*:post)", new BlogRouteHandler() {
                public Renderer get(Blog blog, Request request) {
                    Post post = blog.getPost(request.param("post"));
                    return (post == null ? null : new FullPost(blog, post, router, templateFactory));
                }
            })
            .add("archive", "/blog/(*:blog)/(*:archive)", new BlogRouteHandler() {
                public Renderer get(Blog blog, Request request) {
                    return new BlogHome(blog, request.param("archive"), router, templateFactory);
                }
            });

        return router;
	}
}
