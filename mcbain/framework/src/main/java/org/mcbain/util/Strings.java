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

package org.mcbain.util;

/**
 * String utility methods.
 */

public class Strings {

	/**
	 * Convenience method to construct a string by appending a number of
	 * objects together.
	 *
	 * @param one First item
	 * @param two Second item
	 * @return String
	 */

	public static String string(Object one, Object two) {
		return new StringBuilder()
			.append(one)
			.append(two)
			.toString();
	}


	/**
	 * Convenience method to construct a string by appending a number of
	 * objects together.
	 *
	 * @param one   First item
	 * @param two   Second item
	 * @param three Third item
	 * @return String
	 */

	public static String string(Object one, Object two, Object three) {
		return new StringBuilder()
			.append(one)
			.append(two)
			.append(three)
			.toString();
	}


	/**
	 * Convenience method to construct a string by appending a number of
	 * objects together.
	 *
	 * @param parts Items to append
	 * @return String
	 */

	public static String string(Object... parts) {
		StringBuilder b = new StringBuilder();
		for (Object part : parts) {
			if (part != null)
				b.append(part);
		}
		return b.toString();
	}
}
