package com.abworks.structures.database.core.index;

import com.abworks.structures.database.core.Row;
import com.abworks.structures.database.core.query.Filter;

import java.util.Set;

public interface Index {

    void onInsert(Row row);
    void onUpdate(Row row);
    void onDelete(Row row);
    Set<Long> lookup(Filter filter);


}
