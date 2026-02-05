package com.darkredgm.querymc.Database;

import com.darkredgm.querymc.Helpers.Str;

import java.lang.reflect.Field;

public class ModelAttribute {

    protected Model model;
    protected String columnName;

    protected Field field;

    public ModelAttribute(String columnName, Field field, Model model) {

        this.model = model;

        // In case annotation has null value, set the column name for field
        if ( columnName == null || columnName.isEmpty() )
        {
            this.columnName = Str.toSnakeCase(field.getName());
        }else{
            this.columnName = columnName;
        }

        this.field = field;
        this.field.setAccessible(true); // CRITICAL: Allows access to protected/private fields
    }


    public String getName()
    {
        return this.field.getName();
    }

    public Object getValue() throws IllegalAccessException {
        return this.field.get(this.model);
    }

    public void setValue(Object value) throws IllegalAccessException {
        // NULL handling for primitives
        if (value == null) {
            Class<?> type = this.field.getType();
            if (type == int.class) value = 0;
            else if (type == long.class) value = 0L;
            else if (type == double.class) value = 0.0;
            else if (type == float.class) value = 0.0f;
            else if (type == boolean.class) value = false;
            else if (type == byte.class) value = (byte)0;
            else if (type == short.class) value = (short)0;
            else if (type == char.class) value = '\u0000';
        }

        this.field.set(this.model, value);
    }

    public String getColumnName() {
        return columnName;
    }
}
