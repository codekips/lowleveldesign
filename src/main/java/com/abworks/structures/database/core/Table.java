package com.abworks.structures.database.core;

import com.abworks.structures.database.core.query.Filter;
import com.abworks.structures.database.core.query.Query;
import com.abworks.structures.database.exception.ValidationException;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Table {

    private final Map<Long, Row> rowMap;
    private final AtomicLong nextId;
    @Getter
    private final ColumnSchema columnSchema;

    public Table(ColumnSchema columnSchema){
        this.rowMap = new HashMap<>();
        this.columnSchema = columnSchema;
        this.nextId = new AtomicLong(1000L);
    }
    public Long addRow(Map<String, Object> data){
        validateRowData(data);
        Object[] rowData = new Object[data.size()];
        for (String columnStr: data.keySet()){
            Column column = columnSchema.getColumnMap().get(columnStr);
            if (column == null)
                throw new ValidationException("Invalid column %s in row data".formatted(columnStr));
            Object value = data.get(columnStr);
            column.validateData(value);
            rowData[column.getOrder()] = value;
        }
        Long rowId = nextId.incrementAndGet();
        Row newRow = new Row(rowId, rowData);
        rowMap.put(rowId, newRow);
        return rowId;
    }

    public Optional<Row> getRowById(Long rowId){
        return Optional.ofNullable(rowMap.get(rowId));
    }
    public boolean delete(Long rowId){
        Row removed = rowMap.remove(rowId);
        return removed != null;
    }
    public List<Row> select(Query query){
        List<Row> results = new ArrayList<>();
        Filter queryFilter = query.getFilter();

        String columnStr = queryFilter.getColName();
        Column column = columnSchema.getColumnMap().get(columnStr);
        if (column == null)
            throw new ValidationException("Invalid column %s in query".formatted(columnStr));
        Set<Long> rowsToCheck = new HashSet<>(rowMap.keySet());
        for (Long rowId: rowsToCheck){
            Row r = rowMap.get(rowId);
            Object val = r.getData()[column.getOrder()];
            if (queryFilter.matches(val))
                results.add(r);
            if (results.size() == query.getLimit())
                break;
        }
        return results;
    }

    private void validateRowData(Map<String, Object> data) {
        if (data.size() != columnSchema.size())
            throw new ValidationException("Invalid row sent for insertion");
    }

}
