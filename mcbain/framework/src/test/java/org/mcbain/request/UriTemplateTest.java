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

package org.mcbain.request;

import static org.testng.Assert.*;
import org.testng.annotations.Test;
import org.mcbain.route.Uri;
import org.mcbain.route.UriPattern;

/**
 * Unit tests for UriTemplate.
 */

public class UriTemplateTest {

	/**
	 * Tests uri matching with a single string.
	 */

	@Test
	public void testStringMatch() {
		UriPattern uri1 = new UriPattern("/");

		assertTrue(uri1.match("/").matches());
		assertFalse(uri1.match("/bad").matches());

		UriPattern uri2 = new UriPattern("/part1/part2");

		assertTrue(uri2.match("/part1/part2").matches());
		assertFalse(uri2.match("/bad").matches());
		assertFalse(uri2.match("/part1").matches());
		assertFalse(uri2.match("/part1/").matches());
	}


	/**
	 * Tests uri matching with a single parameter.
	 */

	@Test
	public void testSingleParameterMatch() {
		UriPattern uri = new UriPattern("/$p");

		Uri result = uri.match("/test");
		assertTrue(result.matches());
		assertEquals(result.parameter("p"), "test");
	}


	/**
	 * Tests uri matching with a multiple parameter.
	 */

	@Test
	public void testMultipleParameterMatch() {
		UriPattern uri = new UriPattern("/$p1/$p2");

		Uri result = uri.match("/test1/test2");
		assertTrue(result.matches());
		assertEquals(result.parameter("p1"), "test1");
		assertEquals(result.parameter("p2"), "test2");
	}
}
