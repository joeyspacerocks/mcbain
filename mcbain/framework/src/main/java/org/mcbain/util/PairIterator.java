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
 * Utility to iterate around an array of data, treating alternate values
 * as key/value pairs.
 */

public class PairIterator<K, V> {

	private Object[] source;
	private int keyIndex;
	private int valueIndex;


	/**
	 * Constructs an iterator to iterate round the specified source array.
	 *
	 * @param source Array of source data
	 */

	public PairIterator(Object[] source) {
		this.source = source;
		keyIndex = 0;
		valueIndex = 1;
	}


	/**
	 * Tests to see if their is another pair to fetch.
	 *
	 * @return True if more data present, else false
	 */

	public boolean hasNext() {
		return (source != null && keyIndex < source.length);
	}


	/**
	 * Gets the next key value.
	 *
	 * @return Next key value
	 */

	@SuppressWarnings("unchecked")
	public K nextKey() {
		K key = (K) source[keyIndex];
		keyIndex += 2;
		return key;
	}


	/**
	 * Gets the next value.
	 *
	 * @return Next value
	 */

	@SuppressWarnings("unchecked")
	public V nextValue() {
		V value = (V) source[valueIndex];
		valueIndex += 2;
		return value;
	}
}
