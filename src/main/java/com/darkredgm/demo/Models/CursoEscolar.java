package com.darkredgm.demo.Models;

import com.darkredgm.querymc.Annotations.Primary;
import com.darkredgm.querymc.Annotations.Column;
import com.darkredgm.querymc.Database.Model;

import java.sql.Date;

public class CursoEscolar extends Model {

    @Primary
    @Column
    private int id;

    @Column
    private Date anoInicio;

    @Column
    private Date anoFim;

    public CursoEscolar() {
    }

    public CursoEscolar(int id, Date anoInicio, Date anoFim) {
        this.id = id;
        this.anoInicio = anoInicio;
        this.anoFim = anoFim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getAnoInicio() {
        return anoInicio;
    }

    public void setAnoInicio(Date anoInicio) {
        this.anoInicio = anoInicio;
    }

    public Date getAnoFim() {
        return anoFim;
    }

    public void setAnoFim(Date anoFim) {
        this.anoFim = anoFim;
    }


    @Override
    public String getDatabaseName() {
        return "cristopher_matriculas";
    }
}
