package com.darkredgm;

import com.darkredgm.querymc.Annotations.Primary;
import com.darkredgm.querymc.Annotations.DBForeign;
import com.darkredgm.querymc.Annotations.Column;
import com.darkredgm.querymc.Database.Model;

public class Book extends Model {

    @Primary
    @Column
    private Integer id;


    @DBForeign(model = User.class)
    @Column
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
