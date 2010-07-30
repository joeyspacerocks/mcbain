package org.mcbain.activerecord;

import java.util.List;

import static org.mcbain.activerecord.ActiveRecord.engine;

public class Finder<T> {

    private Class<T> type;

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
