package MCris112.Database.Schema;

import java.util.ArrayList;

public class DBTable {

    protected boolean ifNotExists = false;

    protected String tableName;
    protected ArrayList<DBColumn> columns = new ArrayList<>();

    protected ArrayList<String> primaryKeys = new ArrayList<>();
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
//        if ( column.isPrimaryKey() )
//        {
//            this.primaryKeys.add( column.getName() );
//            column.setPrimaryKey( false );
//        }

        this.columns.add( column );
        return column;
    }

    public ForeignKey foreignKey( String columnName )
    {
        return new ForeignKey(this.tableName, columnName);
    }

    @Override
    public String toString() {
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
