package com.abworks.structures.database.core;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ColumnSchema {
    private List<Column> columns;
    private Map<String, Column> columnMap;

    public ColumnSchema(List<Column> srcColumns){
        this.columns = List.copyOf(srcColumns);
        this.columnMap = new HashMap<>();
        for (Column column: srcColumns){
            columnMap.put(column.getName(), column);
        }
    }


    public int size() {
        return columns.size();
    }
}
