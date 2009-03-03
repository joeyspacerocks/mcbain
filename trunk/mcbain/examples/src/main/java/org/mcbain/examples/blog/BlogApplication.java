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

import org.mcbain.examples.blog.model.Blog;
import org.mcbain.examples.blog.model.BlogService;
import org.mcbain.examples.blog.model.Post;
import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.request.Controller;
import org.mcbain.request.Interceptor;
import org.mcbain.request.Request;
import org.mcbain.route.RouteBuilder;
import org.mcbain.route.Router;


/**
 * Entry point for the blog application.
 */

public class BlogApplication {

	public Router buildRouter() {
		RouteBuilder routes = new RouteBuilder();

		final BlogService blogService = new BlogService();

		Interceptor blogLocator = new Interceptor() {
			public boolean intercept(Request request) {
				Blog blog = blogService.getBlog(request.parameter("blog"));
				if (blog == null) {
					return false;
				} else {
					request.resource(blog);
					return true;
				}
			}
		};

		routes
			.route("/").to(new Controller() {
			public Renderer get(Request request) {
				return new Renderer() {
					public void render(RenderContext context, Writer writer) {
						context.template("index").render(context, writer);
					}
				};
			}
		})

			.route("/blog/$blog").via(blogLocator).to(new Controller() {
			public Renderer get(Request request) {
				return new BlogHome(request.resource(Blog.class), null);
			}
		})

			.route("/blog/$blog/$archive/$post").via(blogLocator).to(new Controller() {
			public Renderer get(Request request) {
				Blog blog = request.resource(Blog.class);
				Post post = blog.getPost(request.parameter("post"));
				return (post == null ? null : new FullPost(blog, post));
			}
		})

			.route("/blog/$blog/newpost").via(blogLocator).to(new Controller() {


			public Renderer get(Request request) {
				return new NewPost(request.resource(Blog.class));
			}

			public Renderer post(Request request) {
				Blog blog = request.resource(Blog.class);

				if (request
					.has("title")
					.has("content")
					.ok()) {

					blog.addPost(request.parameter("title"), request.parameter("content"));

					return new BlogHome(blog, null);

				} else {
					return new NewPost(blog);
				}
			}
		})

			.route("/blog/$blog/$archive").via(blogLocator).to(new Controller() {
			public Renderer get(Request request) {
				String archive = request.parameter("archive").replace('-', '/');
				return new BlogHome(request.resource(Blog.class), archive);
			}
		});

		return routes.end();
	}
}
