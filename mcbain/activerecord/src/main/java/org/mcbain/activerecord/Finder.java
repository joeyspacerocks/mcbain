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

import java.util.List;

import static org.mcbain.activerecord.ActiveRecord.engine;

public class Finder<T> {

    private Class<T> type;

    @SuppressWarnings({"unchecked"})
    public Finder() {
        type = (Class<T>) Reflection.genericType(this);
    }

    public T byId(long id) {
        return (T) engine().fetchById(type, id);
    }

    public List<T> all() {
        return engine().fetchAll(type);
    }

    public Query<T> where() {
        return new Query<T>();
    }
}
