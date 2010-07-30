package org.mcbain.activerecord;

import java.util.Collections;
import java.util.List;

public class Query<T> {
    public Query eq(String property, String value) {
        return this;
    }

    public List<T> toList() {
        return Collections.emptyList();
    }
}
