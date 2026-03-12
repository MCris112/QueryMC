package com.darkredgm.demo.Models;

import com.darkredgm.querymc.Annotations.BelongsTo;
import com.darkredgm.querymc.Annotations.Primary;
import com.darkredgm.querymc.Annotations.Column;
import com.darkredgm.querymc.Database.Model;

public class Asignatura extends Model {

    @Primary
    @Column
    private int codigo;

    @Column
    private String nombre;

    @Column
    private int numeroHoras;

    @BelongsTo( model = Profesor.class )
    private Profesor profesor;

    public Asignatura() {
    }

    public Asignatura(String nombre, int numeroHoras) {
        this.nombre = nombre;
        this.numeroHoras = numeroHoras;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroHoras() {
        return numeroHoras;
    }

    public void setNumeroHoras(int numeroHoras) {
        this.numeroHoras = numeroHoras;
    }

    @Override
    public String getDatabaseName() {
        return "cristopher_matriculas";
    }
}
