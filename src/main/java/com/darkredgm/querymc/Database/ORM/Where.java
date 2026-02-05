package com.darkredgm.querymc.Database.ORM;

public class Where {

    protected String column;

    protected String comparator;

    protected Object value;

    protected String booleanV;


    public Where(String column, String comparator, Object value, String booleanV) {
        this.column = column;
        this.comparator = comparator;
        this.value = value;
        this.booleanV = booleanV;
    }

    public String getBoolean()
    {
        return this.booleanV;
    }

    public String getColumn() {
        return column;
    }

    public String getComparator() {
        return comparator;
    }

    public Object getValue() {
        return value;
    }
}
