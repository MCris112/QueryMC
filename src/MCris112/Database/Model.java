package MCris112.Database;

import MCris112.Helpers.Str;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Model implements DatabaseEnv {

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
        try{
            return this.getAttribute( this.getKeyName() );
        } catch (IllegalAccessException e) {
            throw new IllegalAccessWithoutKey();
        }
    }

    public List<ModelAttribute> getFieldAttributes() {
        List<ModelAttribute> fields = new ArrayList<>();

        for (Field field : getClass().getDeclaredFields()) {
            DbColumn annotation = field.getAnnotation(DbColumn.class);
            if (annotation != null) {
                fields.add( new ModelAttribute( annotation.value(), field, this) );
            }
        }

        return fields;
    }
    /**
     * Obtiene el attributo de la clase
     * @param name
     * @return
     */
    public ModelAttribute getField(String name) {
        // Search if exists some Annotations with specific name
        // Exact @DbColumn match (primary)
        for (ModelAttribute field : this.getFieldAttributes()) {

            if (field.getName().equals(name) ) {
                return field;
            }
        }

        return null;
    }

    /**
     * Obtener valor dinamicamente del modelo
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
     * Colocar un valor dinamicamente del modelo
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

    public void fillAttributes(Consumer<SetBuilder> builder ) throws IllegalAccessException {
        SetBuilder setBuilder = new SetBuilder();
        builder.accept(setBuilder);

        for(Map.Entry<String, Object> entry : setBuilder.getValues().entrySet() )
        {
            this.setAttribute( entry.getKey(), entry.getValue() );
        }
    }

    /**
     * Guarda el estado actual en la base de datos
     * Actualiza o inserta un nuevo valor
     * @throws SQLException
     */
    public void save() throws SQLException {

        SetBuilder builder = new SetBuilder();
        // Iterate on each field
        for(ModelAttribute field : this.getFieldAttributes()) {
            try {
                // Set the value dynamically on query
                builder.set( field.getColumnName(), field.getValue() );
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        // If model needs to update or insert
        if ( this.isModelFromDatabase )
        {
            if ( !this.hasKey() )
                throw new IllegalUpdateWithNoKey();

            QueryBuilder<?> query = new QueryBuilder<>(this );
            query.whereKey()
                    .update(builder);
            return;
        }
        // Save the values into db
        DB.on(this.getTableName()).insert( builder );
        this.isModelFromDatabase = true;
    }
}
