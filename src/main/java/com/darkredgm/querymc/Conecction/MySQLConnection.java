package com.darkredgm.querymc.Conecction;

import com.darkredgm.querymc.Database.DB;

public class MySQLConnection extends BaseConnection{

    public MySQLConnection(String host, String port, String username, String password) {
        super(host, port, username, password);
    }

    @Override
    public String getConnectionName() {
        return "mysql";
    }

}
