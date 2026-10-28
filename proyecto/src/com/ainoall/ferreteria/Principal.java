package com.ainoall.ferreteria;

import com.ainoall.ferreteria.base.Herramienta;
import  com.ainoall.ferreteria.base.HerramientaElectrica;
import  com.ainoall.ferreteria.base.HerramientaManual;
import com.ainoall.ferreteria.gui.FerreteriaControlador;
import com.ainoall.ferreteria.gui.FerreteriaModelo;
import  com.ainoall.ferreteria.gui.Ventana;

public class Principal {
    public static void main(String[] args) {
        Ventana vista = new Ventana();
        FerreteriaModelo modelo = new FerreteriaModelo();
        FerreteriaControlador controlador = new FerreteriaControlador(vista,modelo);
    }
}
