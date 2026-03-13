package com.darkredgm.demo.Models;

import com.darkredgm.querymc.Annotations.BelongsTo;
import com.darkredgm.querymc.Annotations.Column;
import com.darkredgm.querymc.Annotations.Primary;
import com.darkredgm.querymc.Database.Model;

public class Prestamo extends Model {

    public Prestamo(){

    }
    public Prestamo(Integer id, int id_libro, String nif, String fecha_inicio, String fecha_fin, boolean devuelto) {
        this.libro = new Libro();
        libro.setId_libro(id_libro);

        this.id = id;
        this.usuario = new Usuario();
        usuario.setNif(nif);

        this.fechaInicio = fecha_inicio;
        this.fechaFin = fecha_fin;
        this.devuelto = devuelto;
    }

    @Primary
    @Column
    private Integer id;

//    @Column
//    //Clave foranea
//    private int idLibro;

    @BelongsTo( model = Libro.class )
    private Libro libro;

//    @Column
//    //Clave foranea
//    private String nif;

    @BelongsTo( model =  Usuario.class, column = "usuario_nif")
    private Usuario usuario;

    @Column
    private String fechaInicio;

    @Column
    private String fechaFin;

    @Column
    private boolean devuelto;

    public int getId_libro() {
        return libro.getId_libro();
    }

    public void setId_libro(int id_libro) {
        this.libro.setId_libro(id_libro);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNif() {
        return usuario.getNif();
    }

    public String getFecha_inicio() {
        return fechaInicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fechaInicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fechaFin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fechaFin = fecha_fin;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", id_libro=" + getId_libro() +
                ", nif='" + getNif() + '\'' +
                ", fecha_inicio='" + fechaInicio + '\'' +
                ", fecha_fin='" + fechaFin + '\'' +
                ", devuelto=" + devuelto +
                '}';
    }
}


