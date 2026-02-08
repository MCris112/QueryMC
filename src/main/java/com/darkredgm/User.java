package com.darkredgm;

import com.darkredgm.querymc.Annotations.DBColPrimary;
import com.darkredgm.querymc.Annotations.DbColumn;
import com.darkredgm.querymc.Database.Model;

public class User extends Model {

    @DBColPrimary
    @DbColumn
    public int name;

    @DbColumn
    public String email;

    @DbColumn
    public String password;

    @Override
    public String getDatabaseName() {
        return  "test_database_test";
    }
}
