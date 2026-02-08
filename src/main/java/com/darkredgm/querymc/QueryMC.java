package com.darkredgm.querymc;

import com.darkredgm.querymc.Conecction.BaseConnection;
import com.darkredgm.querymc.Conecction.MySQLConnection;
import com.darkredgm.querymc.Env.Env;

import java.sql.SQLException;

public class QueryMC {

    public static Env env = new Env();

    public static BaseConnection getConnection() {
        return new MySQLConnection(
                env.database.getHost(),
                env.database.getPort(),
                env.database.getUsername(),
                env.database.getPassword()
        ).setDatabaseName(env.database.getName());
    }


    public static BaseConnection getConnection( String host, String port, String username, String password, String databaseName ) {

        if (host == null) {
            host = env.database.getHost();
        }

        if (databaseName == null) {
            databaseName = env.database.getName();
        }

        if (port == null) {
            port = env.database.getPort();
        }

        if (username == null) {
            username = env.database.getUsername();
        }

        if (password == null) {
            password = env.database.getPassword();
        }

        return new MySQLConnection( host, port, username, password)
                .setDatabaseName( databaseName );
    }

    public static BaseConnection getConnectionWithoutDB()
    {
        return new MySQLConnection(
                env.database.getHost(),
                env.database.getPort(),
                env.database.getUsername(),
                env.database.getPassword()
        );
    }


}
