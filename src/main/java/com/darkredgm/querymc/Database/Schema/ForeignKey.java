package com.darkredgm.querymc.Database.Schema;


import com.darkredgm.querymc.Contracts.SqlAction;

public class ForeignKey implements SqlAction {

    protected String tableName;
    protected String columnName;
    protected String onColumn;
    protected String referencedTable;

    public ForeignKey(String tableName, String referencedColumnName) {
        this.tableName = tableName;
        this.columnName = referencedColumnName;
    }

    public ForeignKey references(String columnName)
    {
        this.onColumn = columnName;
        return this;
    }

    public ForeignKey on(String tableName) {
        this.referencedTable = tableName;
        return this;
    }

    public String getConstraintName()
    {
        return "fk_"+this.tableName+"_"+this.columnName;
    }

    @Override
    public String toSql() {
        return "CONSTRAINT "+this.getConstraintName()+" FOREIGN KEY ("+this.columnName+") REFERENCES "+this.referencedTable+"("+this.onColumn+")";
    }
}
