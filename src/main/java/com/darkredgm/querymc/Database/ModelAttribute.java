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
        return this.field.get(this.model);
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
                // If it fails to instantiate or set attribute, we might be setting a model
                // instance directly
                if (targetClass.isInstance(value)) {
                    this.field.set(this.model, value);
                    return;
                }
                throw new RuntimeException("Failed to instantiate related model: " + targetClass.getName(), e);
            }
        }

        // Handle numeric conversions
        if (value instanceof Number) {
            Class<?> type = this.field.getType();
            Number num = (Number) value;

            if (type == int.class || type == Integer.class) {
                value = num.intValue();
            } else if (type == double.class || type == Double.class) {
                value = num.doubleValue();
            } else if (type == float.class || type == Float.class) {
                value = num.floatValue();
            } else if (type == long.class || type == Long.class) {
                value = num.longValue();
            } else if (type == short.class || type == Short.class) {
                value = num.shortValue();
            } else if (type == byte.class || type == Byte.class) {
                value = num.byteValue();
            } else if (type == java.math.BigInteger.class) {
                value = java.math.BigInteger.valueOf(num.longValue());
            } else if (type == java.math.BigDecimal.class) {
                // If the target field is explicitly a BigDecimal,
                // cast it purely or convert
                if (num instanceof java.math.BigDecimal) {
                    value = num;
                } else if (num instanceof java.math.BigInteger) {
                    value = new java.math.BigDecimal((java.math.BigInteger) num);
                } else {
                    value = new java.math.BigDecimal(num.doubleValue());
                }
            }
        }

        // Handle Date/Time conversions
        if (value != null) {
            Class<?> type = this.field.getType();
            if (value instanceof java.util.Date) {
                java.util.Date dbDate = (java.util.Date) value;
                if (type == java.util.Date.class) {
                    value = new java.util.Date(dbDate.getTime());
                } else if (type == java.sql.Date.class) {
                    value = new java.sql.Date(dbDate.getTime());
                } else if (type == java.sql.Timestamp.class) {
                    value = new java.sql.Timestamp(dbDate.getTime());
                } else if (type == java.sql.Time.class) {
                    value = new java.sql.Time(dbDate.getTime());
                }
            } else if (value instanceof String) {
                // Try to handle simple string dates like "YYYY-MM-DD"
                if (type == java.util.Date.class || type == java.sql.Date.class || type == java.sql.Timestamp.class
                        || type == java.sql.Time.class) {
                    try {
                        if (type == java.sql.Time.class) {
                            value = java.sql.Time.valueOf((String) value);
                        } else {
                            java.sql.Date parsedDate = java.sql.Date.valueOf((String) value);
                            if (type == java.util.Date.class) {
                                value = new java.util.Date(parsedDate.getTime());
                            } else if (type == java.sql.Date.class) {
                                value = parsedDate;
                            } else if (type == java.sql.Timestamp.class) {
                                value = new java.sql.Timestamp(parsedDate.getTime());
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        // TODO: Let it gracefully fall back to default failure
                    }
                }
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
