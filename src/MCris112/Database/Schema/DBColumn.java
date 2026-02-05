package MCris112.Database.Schema;


import MCris112.Database.SqlAction;

public class DBColumn implements SqlAction {

    protected String type;
    protected String name;

    protected Integer length = 0;
    protected boolean primaryKey;
    protected boolean nullable = true;

    protected String defaultValue;

    protected String extra = "";


    public DBColumn(String name, String type)
    {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }


    public String getType() {
        return type;
    }

    public DBColumn setLength(Integer length) {
        this.length = length;
        return this;
    }

    public DBColumn defaultVal(String value) {
        this.defaultValue = "'"+value+"'";
        return this;
    }

    public DBColumn autoIncrement() {
        this.extra += "AUTO_INCREMENT";
        return this;
    }

    public DBColumn notNull() {
        this.nullable = false;
        return this;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public DBColumn setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }


    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toSql()
    {
        String sql = this.name + " " + this.type.toUpperCase();

        if ( this.length > 0 )
        {
            sql += "(" + this.length + ")";
        }

        if ( !this.nullable )
        {
            sql += " NOT NULL";
        }

        if ( this.defaultValue != null )
        {
            sql += " DEFAULT " + this.defaultValue;
        }

        sql += " "+ this.extra;

        return sql;
    }
}
