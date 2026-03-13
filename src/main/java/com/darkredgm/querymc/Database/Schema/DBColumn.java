package com.darkredgm.querymc.Database.Schema;


import com.darkredgm.querymc.Contracts.SqlAction;
import com.darkredgm.querymc.Contracts.SqlRaw;

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
    public DBColumn defaultVal(Object value) {
        if (value == null) {
            this.defaultValue = "NULL";
            return this;
        }

        String strVal = value.toString().trim();

        if (strVal.equalsIgnoreCase("NULL")) {
            this.defaultValue = "NULL";
        } else if (strVal.endsWith("()")) {
            // MySQL 8+ requires expressions like CURRENT_DATE() to be wrapped in parentheses
            this.defaultValue = "(" + strVal + ")";
        } else if (strVal.equalsIgnoreCase("CURRENT_DATE") || 
                   strVal.equalsIgnoreCase("CURRENT_TIMESTAMP") || 
                   strVal.equalsIgnoreCase("CURRENT_TIME") || 
                   strVal.matches("-?\\d+(\\.\\d+)?") ||
                   strVal.equalsIgnoreCase("TRUE") ||
                   strVal.equalsIgnoreCase("FALSE")) {
            this.defaultValue = strVal;
        } else if ((strVal.startsWith("'") && strVal.endsWith("'")) || 
                   (strVal.startsWith("\"") && strVal.endsWith("\""))) {
            this.defaultValue = strVal;
        } else {
            this.defaultValue = "'" + strVal + "'";
        }

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

        if (this.defaultValue != null) {
            sql += " DEFAULT " + this.defaultValue;
        }

        sql += " "+ this.extra;

        return sql;
    }

}
