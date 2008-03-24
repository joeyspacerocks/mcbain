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

import org.mcbain.Renderer;
import org.mcbain.TemplateInstance;
import org.mcbain.Writer;
import org.mcbain.components.If;
import org.mcbain.components.Link;
import org.mcbain.components.Loop;
import org.mcbain.components.Value;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.examples.blog.model.Post;
import org.mcbain.rest.Context;


/************************************************************************
 * Component used to display a list of posts.
 */

public class Posts implements Renderer {

    private Loop<Post> postLoop;
    private Link titleLink;
    private Value title;
    private Value content;
    private If empty;
    
    
    public Posts(final Blog blog, final List<Post> posts) {
        if (posts.isEmpty()) {
            empty = new If(posts.isEmpty());
                
        } else {
            titleLink = new Link();
            title = new Value();
            title.tagless();
            content = new Value();
            
            postLoop = new Loop<Post>(posts) {
                public void currentValue(Post post) {
                    String archive = post.getArchiveDate().replace('/', '-');
                    
                    title.value(post.getTitle());
                    titleLink.uri("post", "name", blog.getName(), "archive", archive, "post", post.getTitle());
                    content.value(post.getContent());
                }
            };
        }
    }
    
    
    public void render(Context context, Writer writer) {
        TemplateInstance template = context.template("posts");
        
        template.bind(
            "posts", postLoop,
            "title", title,
            "titleLink", titleLink,
            "body", content,
            "moreLink", titleLink,
            "empty", empty
        );
        
        template.render(context, writer);
    }
}
