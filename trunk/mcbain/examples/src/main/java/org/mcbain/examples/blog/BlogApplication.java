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

import org.mcbain.render.RenderContext;
import org.mcbain.render.RenderedResponse;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.request.Request;
import org.mcbain.response.Response;
import org.mcbain.routes.MethodRouteHandler;
import org.mcbain.routes.Router;
import org.mcbain.routes.WildcardPathRouter;


/**
 * Entry point for the blog application.
 */

public class BlogApplication {

	public Router buildRouter() {

        Router router = new WildcardPathRouter()
            .add("/", new MethodRouteHandler() {
                public Response get(Request request) {
                    return new RenderedResponse(new Renderer() {
                        public void render(RenderContext context, Writer writer) {
                            context.template("index").render(context, writer);
                        }
                    });
                }
            });
//
//
//
//		RouteBuilder routes = new RouteBuilder();
//
//		final BlogService blogService = new BlogService();
//
//		Interceptor blogLocator = new Interceptor() {
//			public boolean intercept(Request request) {
//				Blog blog = blogService.getBlog(request.parameter("blog"));
//				if (blog == null) {
//					return false;
//				} else {
//					request.resource(blog);
//					return true;
//				}
//			}
//		};
//
//		routes
//
//			.route("/blog/$blog").as("blog").via(blogLocator).to(new Controller() {
//			public Response get(Request request) {
//				return new RenderedResponse(new BlogHome(request.resource(Blog.class), null));
//			}
//		})
//
//			.route("/blog/$blog/$archive/$post").via(blogLocator).to(new Controller() {
//			public Response get(Request request) {
//				Blog blog = request.resource(Blog.class);
//				Post post = blog.getPost(request.parameter("post"));
//				return (post == null ? null : new RenderedResponse(new FullPost(blog, post)));
//			}
//		})
//
//			.route("/blog/$blog/newpost").via(blogLocator).to(new Controller() {
//
//			public Response get(Request request) {
//				return new RenderedResponse(new NewPost(request.resource(Blog.class), input(request)));
//			}
//
//			public Response post(Request request) {
//				Blog blog = request.resource(Blog.class);
//
//				InputHandler in = input(request);
//
//				if (in.ok()) {
//					blog.addPost(in.value("title"), in.value("content"));
//					return new RenderedResponse(new BlogHome(blog, null));
//
//				} else {
//					return new RenderedResponse(new NewPost(blog, in));
//				}
//			}
//
//			private InputHandler input(Request request) {
//				InputHandler in = new InputHandler(request);
//				in.addField("title", new RequiredValidator());
//				in.addField("content", new RequiredValidator());
//				return in;
//			}
//		})
//
//			.route("/blog/$blog/$archive").via(blogLocator).to(new Controller() {
//			public Response get(Request request) {
//				String archive = request.parameter("archive").replace('-', '/');
//				return new RenderedResponse(new BlogHome(request.resource(Blog.class), archive));
//			}
//		});
//
//		return routes.end();

        return router;
	}
}
