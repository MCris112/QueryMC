package com.darkredgm.querymc.Database.Schema;


import com.darkredgm.querymc.Conecction.BaseConnection;
import com.darkredgm.querymc.Conecction.MCConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Schema {

    protected BaseConnection connection;

    public Schema(BaseConnection connection) {
        this.connection = connection;
    }

    public static void create(String name , Consumer<DBTable> table ) throws SQLException {
        DBTable content = new DBTable(name, false);
        table.accept(content);

        new MCConnection().execute( content.toString(), new ArrayList<>() );
    }

    //    public static void create(String name , Consumer<DBTable> table ) throws SQLException {
//        DBTable content = new DBTable(name, false);
//        table.accept(content);
//
//        executeDdl( content.toString() );
//    }

    public void createIfNotExists(String name , Consumer<DBTable> table ) throws SQLException {
        DBTable content = new DBTable(name, true);
        table.accept(content);

        System.out.println(content.toString());
        executeDdl( content.toString() );
    }
//
    public void drop(String name ) throws SQLException {
        executeDdl( "DROP TABLE "+name);
    }
//
    public void dropIfExists(String name ) throws SQLException {
        executeDdl( "DROP TABLE IF EXISTS "+name);
    }

    // Helper for DDL (reused, safe)
    private void executeDdl(String sql) throws SQLException {
        System.out.println("Executing: " + sql);
        try (Connection conn = this.connection.asSqlConnection();
             Statement stmt = conn.createStatement()) {
            int rows = stmt.executeUpdate(sql);
        }
    }
}
