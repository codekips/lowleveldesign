package com.abworks.structures.database.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Row {
    private final Long rowId;
    private final Object[] data;

    public Object getValue(String column) {

    }
}
