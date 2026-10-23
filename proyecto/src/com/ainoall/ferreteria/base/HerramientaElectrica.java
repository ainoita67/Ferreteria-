package com.ainoall.ferreteria.base;

public class HerramientaElectrica extends Herramienta {
    private int potencia;
    private boolean inalambrica;

    public HerramientaElectrica() {
        super();
    }

    public HerramientaElectrica(String nombre, String codigo, float precio, int stock, String marca, int potencia, boolean inalambrica) {
        super(nombre, codigo, precio, stock, marca);
        this.potencia = potencia;
        this.inalambrica = inalambrica;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public boolean isInalambrica() {
        return inalambrica;
    }

    public void setInalambrica(boolean inalambrica) {
        this.inalambrica = inalambrica;
    }

    public String cable(){
        if (this.inalambrica){
            return "no lleva cables";
        } else {
            return "lleva cable";
        }
    }

    @Override
    public String toString() {
        return "Herramienta Electrica: " + getNombre() + " " + getMarca() + " " + cable() + "  Precio: " + getPrecio() + " Cantidad: " + getStock();
    }
}
