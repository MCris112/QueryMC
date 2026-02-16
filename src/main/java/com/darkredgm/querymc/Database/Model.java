package com.darkredgm.querymc.Database;


import com.darkredgm.querymc.Annotations.DBColPrimary;
import com.darkredgm.querymc.Annotations.DbColumn;
import com.darkredgm.querymc.Conecction.BaseConnection;
import com.darkredgm.querymc.Conecction.DatabaseEnv;
import com.darkredgm.querymc.Database.ORM.DB;
import com.darkredgm.querymc.Database.ORM.QueryBuilder;
import com.darkredgm.querymc.Database.ORM.SetBuilder;
import com.darkredgm.querymc.Database.ORM.SqlOrder;
import com.darkredgm.querymc.Exceptions.IllegalAccessWithoutKey;
import com.darkredgm.querymc.Exceptions.IllegalUpdateWithNoKey;
import com.darkredgm.querymc.QueryMC;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Model implements DatabaseEnv {

    /**
     * Constructor por defecto para evitar error en los query
     */
    public Model() {
    }

    public boolean hasKey() {
        return true;
    }

    @Override
    public String getTableName() {
        // Genera automáticamente el nombre de la tabla con base en la clase, volviéndolo plural
        String name = getClass().getSimpleName().toLowerCase();

        if (name.endsWith("r"))
            return name + "es";

        return name + "s";
    }


    protected boolean isModelFromDatabase = false;

    public void setIsModelFromDatabase(boolean modelFromDatabase) {
        this.isModelFromDatabase = modelFromDatabase;
    }

    /// /////////////////////////////
    ///
    /// Dynamic Accessors
    ///
    /// ////////////////////////////

    /**
     * Obtiene la llave primaria del modelo
     *
     * @return
     */
    public String getKeyName() {
        return "id";
    }

    public Object getKeyValue() throws IllegalAccessWithoutKey {
        try {
            return this.getAttribute(this.getKeyName());
        } catch (IllegalAccessException e) {
            throw new IllegalAccessWithoutKey();
        }
    }

    public List<ModelAttribute> getFieldAttributes() {
        List<ModelAttribute> fields = new ArrayList<>();

        for (Field field : getClass().getDeclaredFields()) {
            DbColumn annotation = field.getAnnotation(DbColumn.class);
            if (annotation != null) {
                fields.add(new ModelAttribute(annotation.value(), field, this));
            }
        }

        return fields;
    }

    /**
     * Obtiene el attributo de la clase
     *
     * @param name
     * @return
     */
    public ModelAttribute getField(String name) {
        // Search if exists some Annotations with specific name
        // Exact @DbColumn match (primary)
        for (ModelAttribute field : this.getFieldAttributes()) {

            if (field.getName().equals(name)) {
                return field;
            }
        }

        return null;
    }

    /**
     * Obtener valor dinamicamente del modelo
     *
     * @param name
     * @return
     * @throws IllegalAccessException
     */
    public Object getAttribute(String name) throws IllegalAccessException {
        ModelAttribute field = this.getField(name);
        if (field == null) {
            return null;
        }

        return field.getValue();
    }

    /**
     * Gets ModelAttribute by DB column name (case-insensitive).
     *
     * @param name DB column name (e.g., "user_id", "USER_ID")
     * @return Matching field or null
     * @throws IllegalAccessException on reflection issues
     */
    public ModelAttribute getFieldByColName(String name) throws IllegalAccessException {
        for (ModelAttribute field : this.getFieldAttributes()) {
            if (field.getColumnName().equalsIgnoreCase(name)) {
                return field;
            }
        }

        return null;
    }

    /**
     * Colocar un valor dinamicamente del modelo
     *
     * @param name
     * @param value
     * @throws IllegalAccessException
     */
    public void setAttribute(String name, Object value) throws IllegalAccessException {
        ModelAttribute field = this.getField(name);
        if (field != null) {
            field.setValue(value);
        }
    }

    public void setAttributeByColName(String name, Object value) throws IllegalAccessException {
        ModelAttribute field = this.getFieldByColName(name);

        if (field != null) {
            field.setValue(value);
        }
    }

    public ModelAttribute getFieldPrimaryKey() {
        for (ModelAttribute field : this.getFieldAttributes()) {
            if (field.isPrimaryKey()) {
                return field;
            }
        }

        return null;
    }

    public void fillAttributes(Consumer<SetBuilder> builder) throws IllegalAccessException {
        SetBuilder setBuilder = new SetBuilder();
        builder.accept(setBuilder);

        for (Map.Entry<String, Object> entry : setBuilder.getValues().entrySet()) {
            this.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Guarda el estado actual en la base de datos
     * Actualiza o inserta un nuevo valor
     *
     * @throws SQLException
     */
    public void save() throws SQLException {

        SetBuilder builder = new SetBuilder();
        // Iterate on each field
        for (ModelAttribute field : this.getFieldAttributes()) {
            try {
                // Set the value dynamically on query

                if (field.isPrimaryKey() && field.getValue() == null)
                    continue; // Don't add null PK

                System.out.printf("[DEBUG][FILL] %s = %s%n", field.getColumnName(), field.getValue());
                builder.set(field.getColumnName(), field.getValue());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        // If model needs to update or insert
        if (this.isModelFromDatabase) {
            if (!this.hasKey())
                throw new IllegalUpdateWithNoKey();

            QueryBuilder.use(this.getClass()).whereKey(this.getKeyValue()).update(builder);
            return;
        }


        // Save the values into db
        ResultSet result = DB.use(this).table(this.getTableName()).insertGetId(builder);

        if (result.next()) {
            ModelAttribute attr = this.getFieldPrimaryKey();

            if ( attr != null ) {
                try{
                    Class<?> type = attr.asField().getType();
                    // Avoid error of java.math.BigInteger
                    if ( type == Integer.class || type == int.class ) {
                        attr.setValue(result.getInt(1));
                    }else{
                        attr.setValue(result.getInt(1));
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        this.isModelFromDatabase = true;
    }

    public void delete() throws SQLException {
        if ( this.getKeyValue() == null )
            return;

        QueryBuilder.use(this.getClass()).whereKey( this.getKeyValue() ).delete();
    }
    public BaseConnection getConnection() {
        return QueryMC.getConnection(
                this.getDatabaseHost(),
                this.getDatabasePort(),
                this.getDatabaseUserName(),
                this.getDatabaseUserPassword(),
                this.getDatabaseName()
        );
    }
}
