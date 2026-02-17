package com.darkredgm.querymc.Database;

import com.darkredgm.querymc.Annotations.DBColPrimary;
import com.darkredgm.querymc.Annotations.DBForeign;
import com.darkredgm.querymc.Database.ORM.QueryBuilder;
import com.darkredgm.querymc.Helpers.Str;

import java.lang.reflect.Field;
import java.sql.SQLException;

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
        /*
        TODO Foregin key works well on set value
        if ( this.field.isAnnotationPresent(DBForeign.class) )
        {
            Class<? extends Model> fieldType = (Class<? extends Model>) field.getType();

            if ( fieldType instanceof Model )
            {
                try {
                    Model model = QueryBuilder.use(fieldType).whereKey(value).first();

                    if ( model != null )
                    {
                        this.field.set(this.model, model);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

         */

        this.field.set(this.model, value);
    }

    public String getColumnName() {
        return columnName;
    }

    public Field asField() {
        return field;
    }

    public boolean isPrimaryKey() {
        return this.field.isAnnotationPresent(DBColPrimary.class);
    }
}
