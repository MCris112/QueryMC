package com.darkredgm;

import com.darkredgm.querymc.Collections.MCList;
import com.darkredgm.querymc.Database.DB;
import com.darkredgm.querymc.Database.ORM.QueryBuilder;
import com.darkredgm.querymc.QueryMC;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() throws SQLException {

//        DB.deleteDatabase("test_database_test");
//        DB.createDatabaseIfNotExists("test_database_test");
//        DB.verify( User.class );

//        MCList<User> models = QueryBuilder.use(User.class).get();
//
//        System.out.println(models.toString());

        User user = new User("test@gmail.com", "sdfsdfsdf");

        user.save();

        System.out.println(user);
    }
}
