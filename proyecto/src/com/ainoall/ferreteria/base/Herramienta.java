package com.ainoall.ferreteria.base;

import java.time.LocalDate;

public class Herramienta {
    private String nombre;
    private String codigo;
    private float precio;
    private int stock;
    private String marca;
    private LocalDate fechaUltComprra;

    public Herramienta(){

    }

    public Herramienta(String nombre, String codigo, float precio, int stock, String marca) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
        this.stock = stock;
        this.marca = marca;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public LocalDate getFechaUltComprra() {
        return fechaUltComprra;
    }

    public void setFechaUltComprra(LocalDate fechaUltComprra) {
        this.fechaUltComprra = fechaUltComprra;
    }
}
