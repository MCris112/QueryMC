package com.darkredgm.querymc.Database.Schema;

import com.darkredgm.querymc.Annotations.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DBTable {

    protected boolean ifNotExists = false;

    protected String tableName;
    protected ArrayList<DBColumn> columns = new ArrayList<>();

    protected ArrayList<ForeignKey> foreignKeys = new ArrayList<>();

    public DBTable(String tableName, boolean ifNotExists) {
        this.tableName = tableName;
        this.ifNotExists = ifNotExists;
    }


    public void id()
    {
        DBColumn column = new DBColumn("id", "INT");

        column.autoIncrement().setPrimaryKey(true);

        this.addColumn( column );
    }

    /// ///////////////////////////////////////
    /// TYPE STRING
    /// ///////////////////////////////////////


    public DBColumn varchar(String name, int length) {
        return addColumn(new DBColumn(name, "VARCHAR").setLength(length));
    }

    public DBColumn charType(String name, int length) {  // Fixed length
        return addColumn(new DBColumn(name, "CHAR").setLength(length));
    }

    public DBColumn text(String name) {
        return addColumn(new DBColumn(name, "TEXT"));
    }

    public DBColumn tinyText(String name) {
        return addColumn(new DBColumn(name, "TINYTEXT"));
    }

    public DBColumn mediumText(String name) {
        return addColumn(new DBColumn(name, "MEDIUMTEXT"));
    }

    public DBColumn longText(String name) {
        return addColumn(new DBColumn(name, "LONGTEXT"));
    }

    public DBColumn blob(String name) {
        return addColumn(new DBColumn(name, "BLOB"));
    }

    public DBColumn tinyInt(String name) {
        return addColumn(new DBColumn(name, "TINYINT"));
    }

    public DBColumn booleanCol( String name )
    {
        return addColumn(new DBColumn(name, "BOOLEAN"));
    }

    public DBColumn smallInt(String name) {
        return addColumn(new DBColumn(name, "SMALLINT"));
    }

    public DBColumn mediumInt(String name) {
        return addColumn(new DBColumn(name, "MEDIUMINT"));
    }

    public DBColumn bigInt(String name) {
        return addColumn(new DBColumn(name, "BIGINT"));
    }
    public DBColumn intCol(String name) {
        return addColumn(new DBColumn(name, "INT"));
    }

    public DBColumn decimal(String name, int precision, int scale) {
        DBColumn col = addColumn( new DBColumn(name, "DECIMAL") );
        col.setExtra( scale > 0 ? "(" + precision + "," + scale + ")" : "(" + precision + ")" );
        return col;
    }

    public DBColumn decimal(String name, int precision) {
        return decimal(name, precision, 0);
    }

    public DBColumn floatType(String name) {
        return addColumn(new DBColumn(name, "FLOAT"));
    }

    public DBColumn doubleType(String name) {
        return addColumn(new DBColumn(name, "DOUBLE"));
    }

    public DBColumn bit(String name, int bits) {
        return addColumn(new DBColumn(name, "BIT").setLength(bits));
    }

    public DBColumn date(String name) {
        return addColumn(new DBColumn(name, "DATE"));
    }

    public DBColumn time(String name) {
        return addColumn(new DBColumn(name, "TIME"));
    }

    public DBColumn datetime(String name) {
        return addColumn(new DBColumn(name, "DATETIME"));
    }

    public DBColumn timestamp(String name) {
        return addColumn(new DBColumn(name, "TIMESTAMP"));
    }

    public DBColumn year(String name) {
        return addColumn(new DBColumn(name, "YEAR"));
    }


    public DBColumn addColumn( DBColumn column )
    {
        this.columns.add( column );
        return column;
    }

    /**
     * Add dynamically columns from model, to avoid doing Schema.create(...) on each time, better automanage
     * @param name
     * @param field
     * @return
     */
    public DBColumn addColumn(String name, Field field )
    {
        DBColumn column;
        DbColumn col = field.getAnnotation(DbColumn.class);

        switch ( field.getType().getSimpleName()) {
            case "int", "Integer" -> column = this.intCol( name );
            case "long", "Long" -> column = this.bigInt( name );
            case "double", "Double", "BigDecimal" -> column = this.decimal( name, col.precision(), col.scale() );
            case "boolean", "Boolean" -> column = this.booleanCol( name );
            case "java.sql.Date", "java.util.Date" -> column = this.date( name );
            case "java.sql.Timestamp" -> column = this.timestamp( name );
            default -> {
                column = this.varchar( name, col.length() );
            }
        };


        // Primary key annotation
        if ( field.isAnnotationPresent(DBColPrimary.class) )
        {
            DBColPrimary primary = field.getAnnotation(DBColPrimary.class);
            column.setPrimaryKey( true );

            if ( primary.autoincrement() )
            {
                column.autoIncrement();
            }
        }

        if ( field.isAnnotationPresent(DBColDefault.class))
        {
            DBColDefault defaults = field.getAnnotation(DBColDefault.class);
            column.defaultVal( defaults.value() );
        }

        if ( !col.nullable() )
        {
            column.notNull();
        }

        return column;
    }

    public ForeignKey foreignKey( String columnName )
    {
        return new ForeignKey(this.tableName, columnName);
    }

    @Override
    public String toString() {
        ArrayList<String> primaryKeys = new ArrayList<>();

        for ( DBColumn column : columns )
        {
            if ( column.isPrimaryKey() )
            {
                primaryKeys.add( column.getName() );
            }
        }

        String sql = "CREATE TABLE "+( this.ifNotExists ? "IF NOT EXISTS " : "")+this.tableName+" (";


        for ( DBColumn column : columns ) {

            if ( primaryKeys.size() == 1 && primaryKeys.contains( column.getName() )) {
                column.setPrimaryKey(true);
            }

            sql += column.toSql()+",";
        }

        sql = sql.substring(0, sql.length()-1);

        if (!primaryKeys.isEmpty()) {
            sql = sql + ", PRIMARY KEY (";

            for ( String key : primaryKeys ) {
                sql += key+",";
            }

            sql = sql.substring(0, sql.length()-1);

            sql = sql + ")";
        }

        for ( ForeignKey foreignKey : foreignKeys ) {
            sql += foreignKey.toSql()+",";
        }

        sql += ");";

        return sql;
    }
}
