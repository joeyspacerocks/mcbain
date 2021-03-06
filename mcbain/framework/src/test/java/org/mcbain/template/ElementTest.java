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

import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests the Element container.
 */

@Test
public class ElementTest {

	public void shouldHoldTagAndId() {
		Element element = new Element("tag", "id");
		assertEquals(element.tag(), "tag");
		assertEquals(element.id(), "id");
	}

	public void shouldExposeAddedAttributes() {
		Element element = new Element("tag", "id");
		element.attribute("name", "displayAs");
		assertEquals(element.attribute("name"), "displayAs");
	}

	public void shouldPreserveNullAttributeValue() {
		Element element = new Element("tag", "id");
		element.attribute("name", null);
		assertEquals(element.attribute("name"), null);
	}

	public void shouldSetNonStringAttributeValueToString() {
		Element element = new Element("tag", "id");
		element.attribute("name", new Object() {
			public String toString() {
				return "displayAs";
			}
		});
		assertEquals(element.attribute("name"), "displayAs");
	}

	public void shouldExposeAttributeNames() {
		Element element = new Element("tag", "id");
		element.attribute("name1", "displayAs");
		element.attribute("name2", "displayAs");
		assertNames(element, "name1", "name2");
	}

	public void shouldExposeTagAndIdFromWrappedElement() {
		Element element = new Element("tag", "id");
		Element proxy = new Element(element);
		assertEquals(proxy.tag(), "tag");
		assertEquals(proxy.id(), "id");
	}

	public void shouldExposeAttributesFromWrappedElement() {
		Element element = new Element("tag", "id");
		Element proxy = new Element(element);
		element.attribute("name", "displayAs");
		assertEquals(proxy.attribute("name"), "displayAs");
	}

	public void shouldMaskAttributeFromWrappedElementIfSetInWrapper() {
		Element element = new Element("tag", "id");
		Element proxy = new Element(element);
		element.attribute("name", "value1");
		proxy.attribute("name", "value2");
		assertEquals(proxy.attribute("name"), "value2");
	}

	public void shouldExposeAttributesSetOnlyInElementWrapper() {
		Element element = new Element("tag", "id");
		Element proxy = new Element(element);
		proxy.attribute("name", "displayAs");
		assertEquals(proxy.attribute("name"), "displayAs");
	}

	public void shouldExposeAttributeNamesFromWrappedElementWhenNoneSetInProxy() {
		Element element = new Element("tag", "id");
		element.attribute("name1", "displayAs");
		element.attribute("name2", "displayAs");
		Element proxy = new Element(element);
		assertNames(proxy, "name1", "name2");
	}

	public void shouldExposeAttributeNamesFromWrappedElementWhenSomeSetInProxy() {
		Element element = new Element("tag", "id");
		element.attribute("name1", "displayAs");
		element.attribute("name2", "displayAs");
		Element proxy = new Element(element);
		proxy.attribute("name1", "displayAs");
		assertNames(proxy, "name1", "name2");
	}

	public void shouldExposeAttributeNamesFromProxyWhenOthersSetInDelegate() {
		Element element = new Element("tag", "id");
		element.attribute("name1", "displayAs");
		Element proxy = new Element(element);
		proxy.attribute("name2", "displayAs");
		assertNames(proxy, "name1", "name2");
	}

    public void shouldSetStyleClassAttributeWhenAttributeHasNoValue() {
        Element element = new Element("tag", "id");
        element.styleClass("value");
        assertEquals(element.attribute("class"), "value");
    }

    public void shouldAddStyleClassToExistingAttributeValue() {
        Element element = new Element("tag", "id");
        element.attribute("class", "existing");
        element.styleClass("value");
        assertEquals(element.attribute("class"), "existing value");
    }

	private void assertNames(Element element, String... expected) {
		Set<String> names = element.attributeNames();
		assertEquals(names.size(), expected.length);
		for (String name : expected) {
			assertTrue(names.contains(name));
		}
	}
}
