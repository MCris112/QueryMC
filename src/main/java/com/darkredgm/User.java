package com.darkredgm;

import com.darkredgm.querymc.Annotations.Primary;
import com.darkredgm.querymc.Annotations.Column;
import com.darkredgm.querymc.Database.Model;

public class User extends Model {

    @Primary
    @Column
    public Integer id;

    @Column
    public String email;

    @Column
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
