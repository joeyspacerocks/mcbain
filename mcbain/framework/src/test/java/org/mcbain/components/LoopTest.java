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

package org.mcbain.components;

import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for Loop component.
 *
 * @author Joe Trewin
 * @version $Revision$
 */

public class LoopTest {

	/**
	 * Tests looping with no source set.
	 */

	@Test
	public void testNullSource() {
		Loop<String> loop = new Loop<String>(null);

		Writer writer = loop(loop);
		Assert.assertEquals(writer.toString(), "");
	}


	/**
	 * Tests looping over an array of one item.
	 */

	@Test
	public void testSingleItemArray() {
		final List<String> source = Arrays.asList("test");

		Loop<String> loop = new Loop<String>(source);

		Writer writer = loop(loop);
		Assert.assertEquals(writer.toString(), "content");
	}


	/**
	 * Tests looping over a collection of two items.
	 */

	@Test
	public void testTwoItemArray() {
		final List<String> source = Arrays.asList("test1", "test2");

		Loop<String> loop = new Loop<String>(source);

		Writer writer = loop(loop);
		Assert.assertEquals(writer.toString(), "contentcontent");
	}


	/**
	 * Set up and render the loop component.
	 */

	private Writer loop(Loop<String> loop) {
		Renderer content = new Renderer() {
			public void render(RenderContext context, Writer writer) {
				writer.print("content", false);
			}
		};

        RenderContext rc = Mockito.mock(RenderContext.class);
        Mockito.stub(rc.contents()).toReturn(content);
        
		Writer writer = new Writer();
		loop.render(rc, writer);

		return writer;
	}
}
