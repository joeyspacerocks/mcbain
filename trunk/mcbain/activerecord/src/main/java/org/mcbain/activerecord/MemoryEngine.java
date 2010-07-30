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
