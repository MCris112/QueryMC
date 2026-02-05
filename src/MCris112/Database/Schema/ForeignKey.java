package MCris112.Database.Schema;

import MCris112.Database.SqlAction;

public class ForeignKey implements SqlAction {

    protected String tableName;
    protected String columnName;
    protected String onColumn;
    protected String referencedTable;

    public ForeignKey(String tableName, String referencedColumnName) {
        this.tableName = tableName;
        this.columnName = referencedColumnName;
    }

    public ForeignKey references(String tableName)
    {
        this.referencedTable = tableName;
        return this;
    }

    public ForeignKey on(String columnName) {
        this.onColumn = columnName;
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
