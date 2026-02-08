package com.darkredgm;

import com.darkredgm.querymc.Database.DB;
import com.darkredgm.querymc.Database.Schema.Schema;
import com.darkredgm.querymc.Env.Env;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() throws SQLException {

        DB.createDatabaseIfNotExists("test_database_test");
        DB.verify( User.class );
    }
}
