package com.darkredgm.querymc.Conecction;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseConnection {

    protected String host;

    protected String port;

    protected String username;

    protected String password;

    protected String databaseName;

    public BaseConnection(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public BaseConnection setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public abstract String getConnectionName();

    public String getConnectionUrl()
    {
        String dbPart = (databaseName != null && !databaseName.isEmpty()) ? databaseName : "";

        return String.format("jdbc:%s://%s:%s%s%s", this.getConnectionName(), host, port, dbPart.isEmpty() ? "" : "/", dbPart);
    }

    public Connection asSqlConnection() throws SQLException {
        System.out.println("Connecting to: " + getConnectionUrl());
        return DriverManager.getConnection( this.getConnectionUrl(), this.username, this.password );
    }
}
