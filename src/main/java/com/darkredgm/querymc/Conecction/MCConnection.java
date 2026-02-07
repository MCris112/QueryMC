package com.darkredgm.querymc.Conecction;



import com.darkredgm.querymc.Database.Schema.Schema;
import com.darkredgm.querymc.Env.Env;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class MCConnection implements DatabaseEnv {

    protected String databaseName;
    protected String tableName;
    protected String port;
    protected String username;
    protected String password;

    public Schema schema;

    public MCConnection() {
        Env env = new Env();
        this.schema = new Schema(this);

        this.databaseName = env.database.getName();
        this.port = env.database.getPort();
        this.username = env.database.getUsername();
        this.password = env.database.getPassword();
    }

    public MCConnection(String databaseName, String tableName, String port, String username, String password) {
        Env env = new Env();

        if (databaseName == null) {
            this.databaseName = env.database.getName();
        }else {
            this.databaseName = databaseName;
        }

        if (tableName == null) {
            this.tableName = env.database.getName();
        }else{
            this.tableName = tableName;
        }

        if (port == null) {
            this.port = env.database.getPort();
        }else{
            this.port = port;
        }

        if (username == null) {
            this.username = env.database.getUsername();
        }else{
            this.username = username;
        }

        if (password == null) {
            this.password = env.database.getPassword();
        }else{
            this.password = password;
        }
    }

    // Non-static instance methods use subclass overrides
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:" + getDatabasePort() + "/" + getDatabaseName() + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        return DriverManager.getConnection(url, this.getDatabaseUserName(), this.getDatabaseUserPassword() );
    }

    /**
     * En caso que sea Select Devuelve ResultSet, o devuelve las filas afectadas, o devuelve un booleano
     * @param sql
     * @param bindings
     * @return
     * @throws SQLException
     */
    public Object execute(String sql, List<Object> bindings) throws SQLException {

        System.out.println( sql );
        System.out.println();
        System.out.println();
        System.out.println();

        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        for (int i = 0; i < bindings.size(); i++) {
            ps.setObject(i + 1, bindings.get(i));
        }

        if ( sql.startsWith("SELECT") ) {
            return ps.executeQuery();
        }

        if ( sql.startsWith("UPDATE") || sql.startsWith("DELETE") ||  sql.startsWith("INSERT") ) {
            return ps.executeUpdate();
        }

        return ps.execute();
    }

    public ResultSet executeInsertGetId( String sql, List<Object> bindings ) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < bindings.size(); i++) {
            ps.setObject(i + 1, bindings.get(i));
        }

        ps.execute(sql);

        return ps.getGeneratedKeys();
    }

    public void execute(String sql, ArrayList<Object> bindings) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        for (int i = 0; i < bindings.size(); i++) {
            ps.setObject(i + 1, bindings.get(i));
        }

        ps.execute();
    }

    public static MCConnection instance(Class<? extends MCConnection> clazz) throws Exception {
        return clazz.getDeclaredConstructor().newInstance();
    }

    /// ///////////////////////////////////////
    ///
    /// Database Configuration
    ///
    /// ///////////////////////////////////////

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public String getDatabasePort() {
        return port;
    }

    @Override
    public String getDatabaseUserName() {
        return username;
    }

    @Override
    public String getDatabaseUserPassword() {
        return password;
    }

    @Override
    public String getTableName() {
        return tableName;
    }
}
