package com.darkredgm.demo.Models;

import com.darkredgm.querymc.Annotations.Column;
import com.darkredgm.querymc.Annotations.DBColDefault;
import com.darkredgm.querymc.Annotations.Primary;
import com.darkredgm.querymc.Database.Model;

import java.util.Date;

public class Libro extends Model {

    public Libro(){}

    public Libro(Integer id_libro, String editorial, int numero_hojas, String titulo, String autor, String genero, double precio, boolean bestseller, String imageUrl) {
        this.idLibro = id_libro;
        this.editorial = editorial;
        this.numeroHojas = numero_hojas;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.precio = precio;
        this.bestseller = bestseller;
        this.imageUrl = imageUrl;
    }

    @Primary
    @Column(nullable = false)
    private Integer idLibro;

    @Column
    private String editorial;

    @Column
    private int numeroHojas;

    @Column
    private String titulo;

    @Column
    private String autor;

    @Column
    private String genero;

    @Column
    private double precio;

    @Column
    private boolean bestseller;

    @Column
    private String imageUrl;

    @DBColDefault("CURRENT_DATE()")
    @Column
    private Date createdAt;

    public int getId_libro() {
        return idLibro;
    }

    public void setId_libro(int id_libro) {
        this.idLibro = id_libro;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getNumero_hojas() {
        return numeroHojas;
    }

    public void setNumero_hojas(int numero_hojas) {
        this.numeroHojas = numero_hojas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public boolean isBestseller() {
        return bestseller;
    }

    public void setBestseller(boolean bestseller) {
        this.bestseller = bestseller;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "idLibro=" + idLibro +
                ", editorial='" + editorial + '\'' +
                ", numeroHojas=" + numeroHojas +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", genero='" + genero + '\'' +
                ", precio=" + precio +
                ", bestseller=" + bestseller +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
