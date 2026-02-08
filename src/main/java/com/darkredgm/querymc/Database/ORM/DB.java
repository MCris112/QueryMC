package com.darkredgm.querymc.Database.ORM;


import com.darkredgm.querymc.Conecction.DatabaseEnv;
import com.darkredgm.querymc.Conecction.MCConnection;
import com.darkredgm.querymc.Database.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class DB implements DatabaseEnv {

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

    public DB( Model model ) {
        this.grammar = new Grammar();
        this.tableName = model.getTableName();
        this.connection = new MCConnection(
                model.getDatabaseName(),
                model.getTableName(),
                model.getDatabasePort(),
                model.getDatabaseUserName(),
                model.getDatabaseUserPassword()
        );
    }

    public static DB use( Model model ) {
        return new DB(model);
    }

    public static DB on( String tableName ) {
        return new DB( tableName );
    }

    public DB table( String tableName ) {
        this.tableName = tableName;
        return this;
    }

    public Integer insert(Consumer<SetBuilder> builder ) throws SQLException {
        SetBuilder setBuilder = new SetBuilder();
        builder.accept( setBuilder );

        return (Integer) this.connection.execute( this.grammar.compileInsert(this.tableName, setBuilder), this.grammar.getBindings() );
    }

    public Integer insert(SetBuilder builder ) throws SQLException {
        return (Integer) this.connection.execute( this.grammar.compileInsert(this.tableName, builder), this.grammar.getBindings() );
    }

    public ResultSet insertGetId(SetBuilder builder ) throws SQLException {
        return  this.connection.executeInsertGetId( this.grammar.compileInsert(this.tableName, builder), this.grammar.getBindings() );
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }
}
