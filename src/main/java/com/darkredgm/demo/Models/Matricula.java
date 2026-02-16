package com.darkredgm.demo.Models;

import com.darkredgm.querymc.Annotations.DBColPrimary;
import com.darkredgm.querymc.Annotations.DbColumn;
import com.darkredgm.querymc.Database.Model;

public class Matricula extends Model {

    @DBColPrimary(autoincrement = false)
    @DbColumn
    private int alumnoId;

    @DBColPrimary(autoincrement = false)
    @DbColumn
    private int cursoEscolarId;

    @DBColPrimary(autoincrement = false)
    @DbColumn
    private int asignaturaId;


    @Override
    public String getDatabaseName() {
        return "cristopher_matriculas";
    }
}
