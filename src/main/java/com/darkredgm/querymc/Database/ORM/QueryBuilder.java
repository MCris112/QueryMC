package com.darkredgm.querymc.Database.ORM;


import com.darkredgm.querymc.Collections.MCList;
import com.darkredgm.querymc.Conecction.DatabaseEnv;
import com.darkredgm.querymc.Conecction.MCConnection;
import com.darkredgm.querymc.Database.Model;
import com.darkredgm.querymc.Exceptions.ModelCreationException;
import com.darkredgm.querymc.Helpers.Str;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class QueryBuilder<M extends Model> implements DatabaseEnv {

    protected Class<M> modelClass;
    protected M model;

    protected MCConnection connection;

    protected Grammar grammar = new Grammar();

    protected String[] columns;

    public String[] getColumns() {
        return columns;
    }

    public QueryBuilder(Class<M> modelClass) throws ModelCreationException {
        this.modelClass = modelClass;

        try{
            this.model = modelClass.getConstructor().newInstance();
        }catch (NoSuchMethodException | InvocationTargetException| InstantiationException| IllegalAccessException  e)
        {
            throw new ModelCreationException();
        }

        this.connection = new MCConnection(
                this.getDatabaseName(),
                this.getTableName(),
                this.getDatabasePort(),
                this.getDatabaseUserName(),
                this.getDatabaseUserPassword()
        );
    }

    public QueryBuilder(M model) {
        this.model = model;
        this.modelClass = (Class<M>) model.getClass();
        this.connection = new MCConnection(
                this.getDatabaseName(),
                this.getTableName(),
                this.getDatabasePort(),
                this.getDatabaseUserName(),
                this.getDatabaseUserPassword()
        );
    }

    public static <M extends Model> QueryBuilder<M> use(Class<M> modelClass)
    {
        return new QueryBuilder<>(modelClass);
    }


    public MCList<M> get() throws SQLException {
        MCList<M> models = new MCList<M>();

        System.out.println( this.grammar.compileSelect(this ) );

        ResultSet result = (ResultSet) this.connection.execute( this.grammar.compileSelect(this ), this.grammar.getBindings() );

        while (result.next()) {
            int cols = result.getMetaData().getColumnCount();

            M model = null;
            try {
                model = (M) this.modelClass.getConstructor().newInstance();
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            // Coloca todos los valores de la tabla dentro del modelo
            for (int i = 1; i <= cols; i++) {
                String colName = Str.toCamelCase(result.getMetaData().getColumnLabel(i));

                try{
                    model.setAttribute(colName, result.getObject(i));
                }catch (IllegalAccessException e){}
            }

            model.setIsModelFromDatabase(true);
            models.add(model);

        }

        return models;
    }

    /**
     * Devuelve el primer elemento del query
     * @return
     */
    public M first() throws SQLException {
        MCList<M> models = this.get();

        if ( models.isEmpty() )
            return null;

        return models.getFirst();
    }

    /**
     * Devuelve el ultimo elemento del query
     * @return
     */
    public M last() throws SQLException {
        MCList<M> models = this.get();

        if ( models.isEmpty() )
            return null;

        return models.getLast();
    }


    /**
     * Devuelve la cantidad de filas afectadas
     * @param builder
     * @return
     * @throws SQLException
     */
    public Integer update( Consumer<SetBuilder> builder ) throws SQLException {
        SetBuilder setBuilder = new SetBuilder();

        builder.accept( setBuilder );

        return ( Integer ) this.connection.execute( this.grammar.compileUpdate(this, setBuilder ), this.grammar.getBindings()) ;
    }

    /**
     * Devuelve la cantidad de filas afectadas
     * @param builder
     * @return
     * @throws SQLException
     */
    public Integer update( SetBuilder builder ) throws SQLException {
        return ( Integer ) this.connection.execute( this.grammar.compileUpdate(this, builder ), this.grammar.getBindings()) ;
    }

    /**
     * Devuelve la cantidad de filas afectadas
     * @return
     * @throws SQLException
     */
    public Integer delete() throws SQLException {
        return ( Integer ) this.connection.execute( this.grammar.compileDelete(this ), this.grammar.getBindings() ) ;
    }




    // ///////////////////////////////////////
    //
    //  Condiciones
    //
    // ///////////////////////////////////////

    protected ArrayList<Where> wheres = new ArrayList<>();

    public ArrayList<Where> getWheres() {
        return wheres;
    }

    /**
     * Condicion por la clase
     * @param where
     * @return
     */
    public QueryBuilder<M> where(Where where) {
        wheres.add(where);
        return this;
    }

    /**
     * Condicion si la columna es igual al valor
     * @param column
     * @param value
     * @return
     */
    public QueryBuilder<M> where(String column, Object value)
    {
        this.wheres.add( new Where( column, "=", value, "AND"  ) );
        return this;
    }

    /**
     * Condición general si se requiere colocar un comparador manual
     * @param column
     * @param comparator
     * @param value
     * @return
     */
    public QueryBuilder<M> where( String column, String comparator, Object value)
    {
        this.wheres.add( new Where( column,  comparator, value, "AND"  ) );
        return this;
    }

   public QueryBuilder<M> orWhere( Where where )
   {
       this.wheres.add(where);
       return this;
   }

    /**
     * O si la condicion si la columna es igual al valor
     * @param column
     * @param value
     * @return
     */
    public QueryBuilder<M> orWhere(String column, String value)
    {
        this.wheres.add( new Where( column, "=", value, "OR"  ) );
        return this;
    }

    /**
     * O si la condición general si se requiere colocar un comparador manual
     * @param column
     * @param comparator
     * @param value
     * @return
     */
    public QueryBuilder<M> orWhere( String column, String comparator, Object value)
    {
        this.wheres.add( new Where( column,  comparator, value, "OR"  ) );
        return this;
    }

    public QueryBuilder<M> whereKey( Object value )
    {
        return this.where( this.model.getKeyName(), value );
    }

    protected int limit = 0;

    public int getLimit() {
        return limit;
    }

    public QueryBuilder<M> limit(int limit)
    {
        this.limit = limit;
        return this;
    }

    /**
     * Selecciona columnas especificas de la tabla
     * @param columns
     * @return
     */
    public QueryBuilder<M> select( String... columns )
    {
        this.columns = columns;
        return this;
    }

    protected String orderByColumn = null;
    protected SqlOrder sqlOrder = null;

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public SqlOrder getSqlOrder() {
        return sqlOrder;
    }

    public QueryBuilder<M> orderBy(String column, SqlOrder order )
    {
        this.orderByColumn = column;
        this.sqlOrder = order;
        return this;
    }


    @Override
    public String getDatabaseName() {
        return this.model.getDatabaseName();
    }

    @Override
    public String getDatabasePort() {
        return this.model.getDatabasePort();
    }

    @Override
    public String getDatabaseUserName() {
        return this.model.getDatabaseUserName();
    }

    @Override
    public String getDatabaseUserPassword() {
        return this.model.getDatabaseUserPassword();
    }

    @Override
    public String getTableName() {
        return this.model.getTableName();
    }
}
