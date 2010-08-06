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

package org.mcbain.render;

import org.mcbain.template.Element;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for the Writer markup type.
 *
 * @author Joe Trewin
 * @version $Revision$
 */

@Test
public class WriterTest {

	public void shouldWriteEmptyTagWithNoAttributes() {
		Writer writer = new Writer();

		writer.emptyTag("tag");

		assertWriter(writer, "<tag />");
	}


	public void shouldWriteEmptyTagWithAttributes() {
		Writer writer = new Writer();

		writer
			.emptyTag("tag")
			.attribute("attr1", "value1")
			.attribute("attr2", "value2");

		assertWriter(writer, "<tag attr1=\"value1\" attr2=\"value2\" />");
	}

	public void shouldWriteNullAttributeAsEmptyString() {
		Writer writer = new Writer();

		writer
			.emptyTag("tag")
			.attribute("attr", null);

		assertWriter(writer, "<tag attr=\"\" />");
	}

	public void shouldWriteTagWithEmptyBody() {
		Writer writer = new Writer();

		writer.tag("tag");

		assertWriter(writer, "<tag></tag>");
	}


	public void shouldWriteTagWithEmptyBodyAndAttributes() {
		Writer writer = new Writer();

		writer
			.tag("tag")
			.attribute("attr1", "value1")
			.attribute("attr2", "value2");

		assertWriter(writer, "<tag attr1=\"value1\" attr2=\"value2\"></tag>");
	}


	public void shouldWriteTagWithBody() {
		Writer writer = new Writer();

		writer.tag("tag").body("Some body text");

		assertWriter(writer, "<tag>Some body text</tag>");
	}


	public void shouldPrintTextToWriter() {
		Writer writer = new Writer();
		writer.print("Some content", false);
		assertWriter(writer, "Some content");
	}


	public void shouldOutputNothingIfPassedNull() {
		Writer writer = new Writer();
		writer.print(null, false);
		assertWriter(writer, "");
	}


	public void shouldPrintEscapedTextToWriter() {
		Writer writer = new Writer();
		writer.print("Some <b>unsafe</b> &amp; content", false);
		assertWriter(writer, "Some &lt;b&gt;unsafe&lt;/b&gt; &amp;amp; content");
	}


	public void shouldPrintRawTextToWriter() {
		Writer writer = new Writer();
		writer.print("Some <b>unsafe</b> &amp; content", true);
		assertWriter(writer, "Some <b>unsafe</b> &amp; content");
	}


	public void shouldWriteNestedTag() {
		Writer nested = new Writer();
		nested.tag("test").body("content");

		Writer writer = new Writer();
		writer.tag("outer")
			.print(nested)
			.close();

		assertWriter(writer, "<outer><test>content</test></outer>");
	}


	public void shouldCloseTag() {
		Writer writer = new Writer();
		writer.tag("test").close();

		assertWriter(writer, "<test></test>");
	}


	public void shouldCloseOpenTagsInCorrectOrder() {
		Writer writer = new Writer();
		writer.tag("first").tag("second");

		assertWriter(writer, "<first><second></second></first>");
	}


	public void shouldWriteElementAttributes() {
		Writer writer = new Writer();
		Element element = new Element(null, null);
		element.attribute("name1", "value1");
		element.attribute("name2", "value2");
		writer.tag("tag").attributes(element);

		assertWriter(writer, "<tag name1=\"value1\" name2=\"value2\"></tag>");
	}


	public void shouldWriteNoAttributesForNullElement() {
		Writer writer = new Writer();
		writer.tag("tag").attributes(null);

		assertWriter(writer, "<tag></tag>");
	}


	public void shouldSealWriter() {
		Writer writer = new Writer();
		writer.seal();
		writer.print("some text", false);
		assertWriter(writer, "");
	}

	public void shouldResetWriterWithNoOpenTags() {
		Writer writer = new Writer();
		writer.print("some text", false);
		writer.reset();
		assertWriter(writer, "");
	}

	public void shouldResetWriterWithOpenTags() {
		Writer writer = new Writer();
		writer.tag("tag");
		writer.reset();
		assertWriter(writer, "");
	}

	private void assertWriter(Writer writer, String expected) {
		Assert.assertEquals(writer.toString(), expected);
	}
}
