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

import org.mcbain.TemplateInstance;
import org.mcbain.Templated;
import org.mcbain.Writer;
import org.mcbain.components.Loop;
import org.mcbain.components.Value;
import org.mcbain.examples.blog.model.Post;
import org.mcbain.rest.Resources;
import org.mcbain.template.TemplateFactory;


/************************************************************************
 * Component used to display a list of posts.
 */

public class Posts implements Templated {

    private TemplateInstance template;
    private Loop<Post> postLoop;
    private Post currentPost;
    
    
    /************************************************************************
     * Constructs a new instance.
     */

    public Posts(final List<Post> posts) {
        postLoop = new Loop<Post>(posts) {
            public void currentValue(Post value) {
                currentPost = value;
            }
        };
    }
    
    
    // @see org.mcbain.Renderer#render(org.mcbain.rest.Resources, org.mcbain.Writer)
    
    public void render(Resources context, Writer writer) {
        template.bind(
            "posts", postLoop,
            "title", new Value(currentPost.getTitle()),
            "body", new Value(currentPost.getContent())
        );
        
        template.render(context, writer);
    }


    // @see org.mcbain.Templated#templateFactory(org.mcbain.template.TemplateFactory)
    
    public void templateFactory(TemplateFactory factory) {
        template = factory.instance("posts");
    }
}
