package com.ainoall.ferreteria.base;

public class HerramientaManual extends Herramienta {
    private String material;
    private String tipo;
    private float numero;

    public HerramientaManual() {
        super();
    }

    public HerramientaManual(String nombre, String codigo, float precio, int stock, String marca, String material, String tipo, float numero) {
        super(nombre, codigo, precio, stock, marca);
        this.material = material;
        this.tipo = tipo;
        this.numero = numero;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getNumero() {
        return numero;
    }

    public void setNumero(float numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "HerramientaManual{: " + getNombre() + " "  + getTipo() + " " + getNumero() + "  Precio: " + getPrecio() + " Cantidad: " + getStock();
    }
}
