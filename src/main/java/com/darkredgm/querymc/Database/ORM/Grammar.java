package com.darkredgm.querymc.Database.ORM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Grammar {

    protected List<Object> bindings =  new ArrayList<Object>();

    public String compileSelect( QueryBuilder<?> query )
    {
        this.bindings.clear();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");

        if ( query.getColumns() == null )
        {
            sql.append("*");
        }else {
            for (int i = 0; i < query.getColumns().length; i++) {
                sql.append(query.getTableName()).append(".").append(query.getColumns()[i]);
                if (i != query.getColumns().length - 1)
                {
                    sql.append(", ");
                }
            }
        }

        sql.append(" FROM ").append(query.getTableName()).append(" ");

        return compileComponents(query, sql).toString();
    }

    protected StringBuilder compileComponents( QueryBuilder<?> query, StringBuilder sql  )
    {

        sql.append( this.compileWhere(query) );

        if ( query.getOrderByColumn() != null  && query.getSqlOrder() != null )
        {
            sql.append(" ORDER BY ").append(query.getOrderByColumn()).append(" ").append(query.getSqlOrder());
        }

        if ( query.getLimit() > 0 )
        {
            sql.append(" LIMIT ").append(query.getLimit());
        }

        return sql;
    }

    public String compileUpdate( QueryBuilder<?> query, SetBuilder builder  )
    {
        if ( builder.isEmpty() )
            throw new IllegalArgumentException("No values to update");

        StringBuilder sql = new  StringBuilder();
        sql.append("UPDATE ").append(query.getTableName()).append(" SET ");

        int index = 0;
        for (Map.Entry<String, Object> entry : builder.getValues().entrySet()) {
            if (index > 0)
                sql.append(", ");

            index++;
            sql.append(entry.getKey()).append(" = ?");
            this.addBinding( entry.getValue() );
        }

        compileComponents(query, sql);

        return sql.toString();
    }

    public String compileDelete( QueryBuilder<?> query  )
    {
        StringBuilder sql = new  StringBuilder();
        sql.append("DELETE FROM ").append( query.getTableName() );

        compileComponents(query, sql);

        return sql.toString();
    }

    public String compileInsert( String tableName, SetBuilder builder  )
    {
        if ( builder.isEmpty() )
            throw new IllegalArgumentException("No values to insert");

        StringBuilder sql = new  StringBuilder();

        sql.append("INSERT INTO ")
                .append(tableName)
                .append(" (").append(String.join(", ", builder.getValues().keySet()))
                .append(") VALUES (");

        int index = 0;
        for (Map.Entry<String, Object> entry : builder.getValues().entrySet()) {
            if (index > 0)
            {
                sql.append(", ");
            }

            sql.append("?");

            this.addBinding( entry.getValue() );
            index++;
        }

        return sql.append(")").toString();
    }

    public String compileWhere( QueryBuilder<?> query)
    {
        String sql = " ";

        if (query.getWheres().isEmpty())
            return sql;

        sql += "WHERE ";

        for (int i = 0; i < query.getWheres().size(); i++) {
            Where where = (Where) query.getWheres().get(i);

            if (i > 0) {
                sql += " "+where.getBoolean()+" ";
            }

            sql += where.getColumn() + " "+where.getComparator()+" ? ";
            this.addBinding( where.getValue() );
        }

        return sql;
    }


    public List<Object> getBindings() {
        return bindings;
    }

    /**
     * Add a new parameter to query
     * @param value
     * @return The position of the binding added
     */
    public int addBinding( Object value )
    {
        this.bindings.add(value);
        return this.bindings.size() - 1;
    }
}
