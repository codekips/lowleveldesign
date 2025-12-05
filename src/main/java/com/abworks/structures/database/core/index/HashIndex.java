package com.abworks.structures.database.core.index;

import com.abworks.structures.database.core.Column;
import com.abworks.structures.database.core.Row;
import com.abworks.structures.database.core.Table;
import com.abworks.structures.database.core.query.Filter;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class HashIndex implements Index{
    private final Column column;
    private final Map<Object, Set<Long>> valueBuckets;
    public HashIndex(Table table, String columnStr){
        this.column = table.getColumnSchema().getColumnMap().get(columnStr);
        this.valueBuckets = new HashMap<>();
    }
    @Override
    public void onInsert(Row row) {
        Object val = row.getData()[column.getOrder()];
        valueBuckets.computeIfAbsent(val, (k-> new HashSet<>())).add(row.getRowId());
    }

    @Override
    public void onUpdate(Row row) {

    }

    @Override
    public void onDelete(Row row) {
        // Row deleted
        Object storedVal = row.getData()[column.getOrder()];
        Set<Long> rowsContaining = valueBuckets.get(storedVal);
        rowsContaining.remove(row.getRowId());
        if (rowsContaining.isEmpty())
            valueBuckets.remove(storedVal);
    }

    @Override
    public Set<Long> lookup(Filter filter) {
        /**
         * Implement lookup
         */
        return Set.of();
    }
}
