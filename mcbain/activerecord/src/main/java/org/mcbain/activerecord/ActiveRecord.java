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
