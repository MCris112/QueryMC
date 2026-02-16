package com.darkredgm;

import com.darkredgm.querymc.Annotations.DBColPrimary;
import com.darkredgm.querymc.Annotations.DbColumn;
import com.darkredgm.querymc.Database.Model;

public class User extends Model {

    @DBColPrimary
    @DbColumn
    public Integer id;

    @DbColumn
    public String email;

    @DbColumn
    public String password;

    @Override
    public String getDatabaseName() {
        return  "test_database_test";
    }

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
