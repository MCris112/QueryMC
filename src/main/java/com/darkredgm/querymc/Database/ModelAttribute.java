package com.darkredgm.querymc.Database;

import com.darkredgm.querymc.Annotations.BelongsTo;
import com.darkredgm.querymc.Annotations.Primary;
import com.darkredgm.querymc.Helpers.Str;

import java.lang.reflect.Field;

public class ModelAttribute {

    // TODO remove model
    protected Model model;
    protected String columnName;

    protected Field field;

    public ModelAttribute(String columnName, Field field, Model model) {

        this.model = model;
        this.field = field;
        this.field.setAccessible(true); // CRITICAL: Allows access to protected/private fields

        // 1. If column name is provided explicitly via @DbColumn
        if (columnName != null && !columnName.isEmpty()) {
            this.columnName = columnName;
            return;
        }

        // 2. If it is a @BelongsTo relation, append _id by default or use custom column
        if (field.isAnnotationPresent(BelongsTo.class)) {
            BelongsTo belongsTo = field.getAnnotation(BelongsTo.class);
            if (belongsTo.column() != null && !belongsTo.column().isEmpty()) {
                this.columnName = belongsTo.column();
            } else {
                this.columnName = Str.toSnakeCase(field.getName()) + "_id";
            }
            return;
        }

        // 3. Just a regular column fallback
        this.columnName = Str.toSnakeCase(field.getName());
    }

    public String getName() {
        return this.field.getName();
    }

    public Object getValue() throws IllegalAccessException {
        return  this.field.get(this.model);
    }

    public void setValue(Object value) throws IllegalAccessException {
        if (value == null) {
            this.field.set(this.model, null);
            return;
        }

        if (this.field.isAnnotationPresent(BelongsTo.class)) {
            BelongsTo belongsTo = this.field.getAnnotation(BelongsTo.class);
            Class<? extends Model> targetClass = belongsTo.model();

            try {
                Model targetInstance = targetClass.getDeclaredConstructor().newInstance();
                // Set the primary key value of the target instance
                targetInstance.setAttributeByColName(targetInstance.getKeyName(), value);
                this.field.set(this.model, targetInstance);
                return;
            } catch (Exception e) {
                // If it fails to instantiate or set attribute, we might be setting a model instance directly
                if (targetClass.isInstance(value)) {
                    this.field.set(this.model, value);
                    return;
                }
                throw new RuntimeException("Failed to instantiate related model: " + targetClass.getName(), e);
            }
        }

        this.field.set(this.model, value);
    }

    public String getColumnName() {
        return columnName;
    }

    public Field asField() {
        return field;
    }

    public boolean isPrimaryKey() {
        return this.field.isAnnotationPresent(Primary.class);
    }
}
