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

import java.util.ArrayList;
import java.util.List;

/**
 * Simple LIFO stack implementation based on an array list.
 */

public class ArrayStack<E> {

	private final List<E> stack;


	/**
	 * Constructs a new stack.
	 */

	public ArrayStack() {
		stack = new ArrayList<E>();
	}


	/**
	 * Pushes an element on to the end of the stack.
	 *
	 * @param element Element to push on to the stack
	 */

	public void push(E element) {
		stack.add(element);
	}


	/**
	 * Pops the last element off of the end of the stack.
	 *
	 * @return Last element, or null if stack empty
	 */

	public E pop() {
		return stack.isEmpty() ? null : stack.remove(stack.size() - 1);
	}


	/**
	 * Gets the last element from the end of the stack, leaving it in place.
	 *
	 * @return Last element, or null if stack empty
	 */

	public E peek() {
		return stack.isEmpty() ? null : stack.get(stack.size() - 1);
	}

    public void clear() {
        stack.clear();
    }
}
