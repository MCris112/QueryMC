package com.darkredgm.demo.Models;

import com.darkredgm.querymc.Annotations.DBColPrimary;
import com.darkredgm.querymc.Annotations.DbColumn;
import com.darkredgm.querymc.Database.Model;

import java.sql.Date;

public class CursoEscolar extends Model {

    @DBColPrimary
    @DbColumn
    private int id;

    @DbColumn
    private Date anoInicio;

    @DbColumn
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
