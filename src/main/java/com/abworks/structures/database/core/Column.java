package com.abworks.structures.database.core;

import com.abworks.structures.database.exception.ValidationException;
import lombok.Getter;

@Getter
public class Column {
    private String name;
    private DataType dataType;
    private int order;
    private boolean isNullable;

    public Column(String name, DataType dataType, int order, boolean isNullable) {
        this.name = name;
        this.dataType = dataType;
        this.order = order;
        this.isNullable = isNullable;
    }
    public Column(String name, DataType dataType, int order) {
        this(name, dataType, order, false);
    }

    public void validateData(Object val){
        if (!isNullable & val == null)
            throw new ValidationException("%s can not be null ".formatted(name));
        switch (dataType){
            case INT -> {if (!(val instanceof  Integer)) throw new ValidationException("Must be " +
                    "of" +
                    " type Integer");}
            case FLOAT -> {if (!(val instanceof  Integer)) throw new ValidationException("Must be " +
                    "of" +
                    " type Float");}
            case STR -> {if (!(val instanceof  String)) throw new ValidationException("Must be " +
                    "of" +
                    " type String");}
        }
    }
}
