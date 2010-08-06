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

package org.mcbain.activerecord;

public class ActiveRecord<T> {

    private static Engine engine = new MemoryEngine();

    private long id;

    /**
     * Override default storage engine with a custom implementation.
     * Used for testing, etc.
     *
     * @param   engine      org.mcbain.activerecord.Engine implementation
     */

    public static void engine(Engine engine) {
        ActiveRecord.engine = engine;
    }

    public void store() {
        id = engine.store(this);
    }

    public long id() {
        return id;
    }

    protected void id(long id) {
        this.id = id;
    }

    protected static Engine engine() {
        return engine;
    }
}
