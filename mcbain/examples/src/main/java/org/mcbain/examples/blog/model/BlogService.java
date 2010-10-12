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

package org.mcbain.examples.blog.model;

import java.util.*;

/**
 * Service providing access to blogs.
 *
 * @author Joe Trewin
 * @version $Revision$
 */

public class BlogService {

	private Map<String, Blog> blogs;


	/**
	 * Constructs a new instance.
	 */

	public BlogService() {
		Calendar cal = new GregorianCalendar(2006, 6, 2);

		blogs = new HashMap<String, Blog>();

		Blog populated = new Blog("Joe");
		populated.addPost(new Post("Folly", "If there are two things that I've learnt over the years it's that there are too many Java web frameworks, and that you should never mess about with classloaders. That said, this blog is an on-going account of my attempt to design and build a better web framework. The first challenge is bringing a bit more dynasism to Java using custom classloaders. If you haven't already guessed, this is (hopefully) going to get a bit technical. If Java, web programming and all the associated nonsense doesn't float your boat you'd possible be better off clicking the Next Blog button now. Similarly, if you chanced upon this in the hope of finding evocations literally both strange and curved, you will again be disappointed.", cal.getTime()));
		populated.addPost(new Post("What's already out there?", "What's the first thing you do when evaluating a web framework? For me, it's to ignore the paragraphs on the homepage that always seem to contain the words ease, leverage and seperation of concerns, and find the HelloWorld or equivilent tutorial. It then takes me several seconds to decide whether it's for me:", new Date()));
		populated.addPost(new Post("Stripes before my eyes", "<b>Responding to my previous</b> &lt; knee-jerk dismissal of the Stripes framework, Tim Fennell commented: \"You shouldn't dismiss Stripes so quickly. All the examples use JSP because, frankly, that's what the vast majority of developers are familiar with. But Stripes is not tied to JSP.\" So I took some time to take a closer look at Stripes, and I have to say that I like what I see.", new Date()));

		blogs.put("joe", populated);

		Blog empty = new Blog("Empty");
		blogs.put("empty", empty);
	}


	/**
	 * Gets a blog by name.
	 *
	 * @param name Blog name
	 * @return Blog
	 */

	public Blog getBlog(String name) {
		return blogs.get(name.toLowerCase());
	}
}
