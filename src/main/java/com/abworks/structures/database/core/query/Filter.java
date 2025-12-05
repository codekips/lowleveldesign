package com.abworks.structures.database.core.query;


import lombok.Getter;

public class Filter {
    @Getter
    private String colName;
    private Comparable compValue;
    private Op op;

    public boolean matches (Object actualVal){
        if (actualVal == null){
            if (op == Op.EQ)
                return compValue == null;
            return false;
        }
        Comparable actual = (Comparable) actualVal;
        return switch (op){
            case EQ -> compValue.equals(actualVal);
            case LT -> compValue.compareTo(actual) < 0;
            case NEQ -> !compValue.equals(actualVal);
        };
    }
}
