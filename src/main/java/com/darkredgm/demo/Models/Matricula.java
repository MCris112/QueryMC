package com.darkredgm.demo.Models;

import com.darkredgm.querymc.Annotations.Primary;
import com.darkredgm.querymc.Annotations.Column;
import com.darkredgm.querymc.Database.Model;

public class Matricula extends Model {

    @Primary(autoincrement = false)
    @Column
    private int alumnoId;

    @Primary(autoincrement = false)
    @Column
    private int cursoEscolarId;

    @Primary(autoincrement = false)
    @Column
    private int asignaturaId;


    @Override
    public String getDatabaseName() {
        return "cristopher_matriculas";
    }
}
