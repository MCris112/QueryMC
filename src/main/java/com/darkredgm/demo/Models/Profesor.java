package com.darkredgm.demo.Models;

import com.darkredgm.querymc.Annotations.HasMany;
import com.darkredgm.querymc.Annotations.Primary;
import com.darkredgm.querymc.Annotations.Column;
import com.darkredgm.querymc.Collections.MCList;
import com.darkredgm.querymc.Database.Model;

public class Profesor extends Model {

    @Primary
    @Column
    private int id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String especialidad;

    @Column
    private String telefono;

    @HasMany( model = Asignatura.class)
    private MCList<Asignatura> asignaturas;

    public Profesor() {
    }

    public Profesor(int id, String nombre, String apellido, String especialidad, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    @Override
    public String getDatabaseName() {
        return "cristopher_matriculas";
    }
}
