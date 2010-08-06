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

import java.util.*;

/**
 * Basic implementation of an org.mcbain.activerecord.ActiveRecord storage engine that stores data in an
 * in-memory structure.
 */

public class MemoryEngine implements Engine {

    private Map<Class, Long> ids;
    private Map<Class, Map<Long,Object>> store;

    public MemoryEngine() {
        ids = new HashMap<Class, Long>();
        store = new HashMap<Class, Map<Long, Object>>();
    }

    @SuppressWarnings({"unchecked"})
    public <T> T fetchById(Class<T> type, long id) {
        return (T) typeStore(type).get(id);
    }

    @SuppressWarnings({"unchecked"})
    public <T> List<T> fetchAll(Class<T> type) {
        return new ArrayList<T>((Collection<T>) typeStore(type).values());
    }

    public <T> long store(ActiveRecord<T> entity) {
        Class<? extends ActiveRecord> type = entity.getClass();

        long id = entity.id();

        if (id <= 0) {
            id = nextId(type);
        }

        typeStore(entity.getClass()).put(id, entity);

        return id;
    }

    private Map<Long,Object> typeStore(Class type) {
        Map<Long, Object> typeStore = store.get(type);
        if (typeStore == null) {
            typeStore = new LinkedHashMap<Long, Object>();
            store.put(type, typeStore);
        }
        return typeStore;
    }

    private long nextId(Class type) {
        Long id = ids.get(type);
        if (id == null) {
            id = 1L;
        }
        ids.put(type, id + 1);
        return id;
    }
}
