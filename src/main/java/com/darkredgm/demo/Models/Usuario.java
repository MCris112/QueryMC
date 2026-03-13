package com.darkredgm.demo.Models;

import com.darkredgm.querymc.Annotations.Column;
import com.darkredgm.querymc.Annotations.Primary;
import com.darkredgm.querymc.Database.Model;

public class Usuario extends Model {

    public Usuario(){}

    public Usuario(String nif, String nombre, String apellidos, String direccion, String telefono) {
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    @Primary(autoincrement = false)
    @Column(nullable = false)
    private String nif;

    @Column
    private String nombre;

    @Column
    private String apellidos;

    @Column
    private String direccion;

    @Column
    private String telefono;

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    

}
