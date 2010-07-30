package org.mcbain.activerecord;

import java.util.List;

public interface Engine {
    public <T> T fetchById(Class<T> type, long id);

    public <T> long store(ActiveRecord<T> entity);

    public <T> List<T> fetchAll(Class<T> type);
}
