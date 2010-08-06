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

package org.mcbain;

import org.testng.annotations.Test;

/**
 *
 */

@Test
public class ReflectionTest {

	public void test() throws Exception {
		Sample object = new Sample();
		Class c = Sample.class;

		int loops = 100000;

		for (int i = 0; i < loops; i++) {
			object.test("value");
		}

		long start = System.currentTimeMillis();
		for (int i = 0; i < loops; i++) {
			object.test("value");
		}

		System.out.println(loops + " regular method calls:" + (System.currentTimeMillis() - start) + " milliseconds.");

		java.lang.reflect.Method method = c.getMethod("test", new Class[]{String.class});

		for (int i = 0; i < loops; i++) {
			method.invoke(object, new Object[]{"value"});
		}

		start = System.currentTimeMillis();
		for (int i = 0; i < loops; i++) {
			method.invoke(object, new Object[]{"value"});
		}

		System.out.println(loops + " reflective method calls without lookup:" + (System.currentTimeMillis() - start) + " milliseconds.");
		for (int i = 0; i < loops; i++) {
			method = c.getMethod("test", new Class[]{String.class});
			method.invoke(object, new Object[]{"value"});
		}
		start = System.currentTimeMillis();
		for (int i = 0; i < loops; i++) {
			method = c.getMethod("test", new Class[]{String.class});
			method.invoke(object, new Object[]{"value"});
		}
		System.out.println(loops + " reflective method calls with lookup:" + (System.currentTimeMillis() - start) + " milliseconds.");
	}


	public class Sample {
		public String test(String value) {
			return value;
		}
	}
}
