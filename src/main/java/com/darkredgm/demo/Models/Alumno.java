package com.darkredgm.demo.Models;

import com.darkredgm.querymc.Annotations.DBColPrimary;
import com.darkredgm.querymc.Annotations.DbColumn;
import com.darkredgm.querymc.Database.Model;

public class Alumno extends Model {

    @DBColPrimary
    @DbColumn
    private int numeroMatricula;

    @DbColumn
    private String nombre;

    @DbColumn
    private String apellidos;

    @DbColumn
    private String fechaNacimiento;

    @DbColumn
    private String telefono;

    public Alumno() {
    }

    public Alumno(int numeroMatricula, String nombre, String apellidos, String fechaNacimiento, String telefono) {
        this.numeroMatricula = numeroMatricula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
    }

    public int getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(int numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
