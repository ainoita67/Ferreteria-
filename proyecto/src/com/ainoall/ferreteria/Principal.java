package com.ainoall.ferreteria;

import com.ainoall.ferreteria.base.Herramienta;
import  com.ainoall.ferreteria.base.HerramientaElectrica;
import  com.ainoall.ferreteria.base.HerramientaManual;

public class Principal {
    public static void main(String[] args) {
        Ventana vista = new Ventana();
        VehiculosModelo modelo = new VehiculosModelo();
        VehiculosControlador controlador = new VehiculosControlador(vista,modelo);
    }
}
