package com.darkredgm;

import com.darkredgm.querymc.Annotations.DBColPrimary;
import com.darkredgm.querymc.Annotations.DBForeign;
import com.darkredgm.querymc.Annotations.DbColumn;
import com.darkredgm.querymc.Database.Model;

import java.util.ArrayList;

public class Book extends Model {

    @DBColPrimary
    @DbColumn
    private Integer id;


    @DBForeign(model = User.class)
    @DbColumn
    private Integer userId;

    public Book() {
    }

    public Book(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", userId=" + userId +
                '}';
    }
}
