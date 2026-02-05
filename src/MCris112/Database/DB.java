package MCris112.Database;

import java.sql.SQLException;
import java.util.function.Consumer;

public class DB implements DatabaseEnv{

    protected String tableName = null;

    protected MCConnection connection;

    protected Grammar grammar;

    public DB( String tableName ) {

        this.tableName = tableName;

        this.grammar = new Grammar();
        this.connection = new MCConnection(
                this.getDatabaseName(),
                this.getTableName(),
                this.getDatabasePort(),
                this.getDatabaseUserName(),
                this.getDatabaseUserPassword()
        );
    }

    public static DB on( String tableName ) {
        return new DB( tableName );
    }

    public Integer insert(Consumer<SetBuilder> builder ) throws SQLException {
        SetBuilder setBuilder = new SetBuilder();
        builder.accept( setBuilder );

        return (Integer) this.connection.execute( this.grammar.compileInsert(this.tableName, setBuilder), this.grammar.getBindings() );
    }

    public Integer insert(SetBuilder builder ) throws SQLException {
        return (Integer) this.connection.execute( this.grammar.compileInsert(this.tableName, builder), this.grammar.getBindings() );
    }


    @Override
    public String getTableName() {
        return this.tableName;
    }
}
