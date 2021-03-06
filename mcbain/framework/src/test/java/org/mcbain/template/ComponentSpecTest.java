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

package org.mcbain.template;

import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

/**
 * Tests the component specification structure.
 */

@Test
public class ComponentSpecTest {

	@Mock private TemplateClass templateClass;
	@Mock private Element element;
	@Mock private RenderContext rc;
	@Mock private Writer writer;
	@Mock private Template template;

	private ComponentSpec root;

	@BeforeMethod
	public void setup() {
		initMocks(this);
		root = new ComponentSpec(templateClass);
	}

	public void shouldHaveNoParentIfRoot() {
		assertNull(root.parent());
	}

	public void shouldHaveParentIfChild() {
		ComponentSpec child = root.add("id", element, 1);
		assertEquals(child.parent(), root);
	}

	public void shouldListChildren() {
		ComponentSpec child1 = root.add("id1", element, 1);
		ComponentSpec child2 = root.add("id2", element, 1);

		List<TemplatePart> children = root.children();
		assertEquals(children.size(), 2);
		assertSame(children.get(0), child1);
		assertSame(children.get(1), child2);
	}

	public void shouldRegisterChildWithTemplate() {
		ComponentSpec child = root.add("id", element, 1);
		verify(templateClass).add("id", child);
	}

	public void shouldListTemplateTextWithChildren() {
		ComponentSpec child = root.add("id", element, 1);
		root.addTemplateText("text");

		List<TemplatePart> children = root.children();
		assertEquals(children.size(), 2);
		assertSame(children.get(0), child);
		assertEquals(((TemplateText) children.get(1)).text(), "text");
	}

	public void shouldHaveNoElementIfRoot() {
		assertNull(root.element());
	}

	public void shouldHaveElementIfChild() {
		ComponentSpec child = root.add("id", element, 1);
		assertSame(child.element(), element);
	}

	public void shouldJustRenderChildrenIfRoot() {
		root.addTemplateText("child1");
		root.addTemplateText("child2");

		root.render(rc, writer, template);

		verify(writer).print("child1", true);
		verify(writer).print("child2", true);
	}

	public void shouldRenderTemplateElementIfNothingBound() {
		stubWriterForUnboundId("id");

		root.add("id", element, 1);
		root.render(rc, writer, template);
	}

	public void shouldRenderNothingIfNullBound() {
		bindToTemplate(null);

		root.add("id", element, 1);
		root.render(rc, writer, template);

		verifyZeroInteractions(writer);
	}

	public void shouldRenderBoundComponent() {
		Renderer component = mock(Renderer.class);

		bindToTemplate(component);

		root.add("id", element, 1);
		root.render(rc, writer, template);

		verify(component).render(rc, writer);
	}

//	public void shouldInjectElementInElementalComponent() {
//		ElementAwareRenderer component = new ElementAwareRenderer();
//		stub(element.id()).toReturn("element");
//
//		bindToTemplate(component);
//
//		root.add("id", element);
//		root.render(rc, writer, template);
//
//		assertEquals(component.element.id(), element.id());
//	}
//
//	public void shouldInjectChildRendererIntoContainerComponent() {
//		ContainerRenderer component = new ContainerRenderer();
//
//		bindToTemplate(component);
//		stubWriterForUnboundId("child");
//
//		root.add("id", element).add("child", element);
//		root.render(rc, writer, template);
//	}

//	private class ElementAwareRenderer implements Renderer, ElementAware {
//		public Element element;
//
//		public void render(RenderContext context, Writer writer) {
//		}
//
//		public void element(Element element) {
//			this.element = element;
//		}
//	}
//
//	private class ContainerRenderer implements Renderer, Container {
//		public void render(RenderContext context, Writer writer) {
//		}
//
//		public void contents(Renderer content) {
//			content.render(rc, writer);
//		}
//	}

	private void bindToTemplate(Renderer component) {
		stub(template.contains("id")).toReturn(true);
		stub(template.get("id")).toReturn(component);
	}

	private void stubWriterForUnboundId(String id) {
		stub(element.tag()).toReturn("tag");
		stub(writer.tag("tag")).toReturn(writer);
		stub(writer.attribute("id", id)).toReturn(writer);
		stub(writer.attributes(element)).toReturn(writer);
	}
}
