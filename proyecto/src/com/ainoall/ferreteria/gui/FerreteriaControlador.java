package com.ainoall.ferreteria.gui;

import com.ainoall.ferreteria.base.Herramienta;
import com.ainoall.ferreteria.base.HerramientaElectrica;
import com.ainoall.ferreteria.base.HerramientaManual;
import util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.Properties;

public class FerreteriaControlador implements ActionListener, ListSelectionListener, WindowListener {
    private Ventana vista;
    private FerreteriaModelo modelo;
    private File ultimaRutaExportada;

    public FerreteriaControlador(Ventana vista, FerreteriaModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        try {
            cargarDatosConfiguracion();
        } catch (IOException e) {
            System.out.println("No es encontro el archivo " + e.getMessage());
        }
        addActionListener(this);
        addListSelectionListener(this);
        addWindowListener(this);
    }

    private boolean hayCamposVacios() {
        if (vista.nombreTxt.getText().isEmpty() ||
                vista.codigoTxt.getText().isEmpty() ||
                vista.precioTxt.getText().isEmpty() ||
                vista.stockTxt.getText().isEmpty() ||
                vista.marcaTxt.getText().isEmpty() ||
                vista.fechaUltCompraDPicker.getText().isEmpty()) {
            return true;
        }
        if (vista.electricaRadioButton.isSelected()) {
            if (vista.potenciaTxt.getText().isEmpty()) {
                return true;
            }
        } else {
            if (vista.materialTxt.getText().isEmpty() ||
                    vista.tipoTxt.getText().isEmpty() ||
                    vista.numeroTxt.getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void limpiarCampos() {
        vista.nombreTxt.setText(null);
        vista.codigoTxt.setText(null);
        vista.precioTxt.setText(null);
        vista.stockTxt.setText(null);
        vista.marcaTxt.setText(null);
        vista.fechaUltCompraDPicker.setText(null);
        vista.potenciaTxt.setText(null);
        vista.inalambricaCheckBox.setSelected(false);
        vista.materialTxt.setText(null);
        vista.tipoTxt.setText(null);
        vista.numeroTxt.setText(null);
        vista.codigoTxt.requestFocus();
    }

    public void refrescar() {
        vista.dlmHerramienta.clear();
        for (Herramienta unaHerramienta : modelo.obtenerHerramientas()) {
            vista.dlmHerramienta.addElement(unaHerramienta);
        }
    }

    private void addActionListener(ActionListener listener) {
        vista.nuevoButton.addActionListener(listener);
        vista.importarButton.addActionListener(listener);
        vista.exportarButton.addActionListener(listener);
        vista.electricaRadioButton.addActionListener(listener);
        vista.manualRadioButton.addActionListener(listener);
    }

    private void addListSelectionListener(ListSelectionListener listener) {
        vista.list1.addListSelectionListener(listener);
    }

    private void addWindowListener(WindowListener listener) {
        vista.frame.addWindowListener(listener);
    }

    private void cargarDatosConfiguracion() throws IOException {
        Properties configuracion = new Properties();
        configuracion.load(new FileReader("ferreteria.conf"));
        ultimaRutaExportada = new File(configuracion.getProperty("ultimaRutaExportada"));
    }

    private void actualizarDatosConfiguracion(File ultimaRutaExportada) {
        this.ultimaRutaExportada = ultimaRutaExportada;
    }

    private void guardarDatosConfiguracion() throws IOException {
        Properties configuracion = new Properties();
        configuracion.setProperty("ultimaRutaExportada", ultimaRutaExportada.getAbsolutePath());
        configuracion.store(new PrintWriter("ferreteria.conf"), "Datos configuracion ferreteria");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        switch (actionCommand) {
            case "Nuevo":
                if (hayCamposVacios()) {
                    Util.mensajeError("Los siguientes campos no pueden " +
                            "estar vacios: \nNombre\nCodigo\nPrecio\nStock\nMarca" +
                            "\nFecha Ultima Compra\n" + vista.datosEspecificosLbl.getText());
                    break;
                }
                if (modelo.existeCodigo(vista.codigoTxt.getText())) {
                    Util.mensajeError("Ya existe una herramienta con ese codigo");
                    break;
                }
                if (vista.electricaRadioButton.isSelected()) {
                    modelo.altaHerramientaElectrica(vista.nombreTxt.getText(), vista.codigoTxt.getText(),
                            Float.parseFloat(vista.precioTxt.getText()), Integer.parseInt(vista.stockTxt.getText()),
                            vista.marcaTxt.getText(), vista.fechaUltCompraDPicker.getDate(),
                            Integer.parseInt(vista.potenciaTxt.getText()), vista.inalambricaCheckBox.isSelected());
                } else {
                    modelo.altaHerramientaManual(vista.nombreTxt.getText(), vista.codigoTxt.getText(),
                            Float.parseFloat(vista.precioTxt.getText()), Integer.parseInt(vista.stockTxt.getText()),
                            vista.marcaTxt.getText(), vista.fechaUltCompraDPicker.getDate(),
                            vista.materialTxt.getText(), vista.tipoTxt.getText(),
                            Float.parseFloat(vista.numeroTxt.getText()));
                }
                limpiarCampos();
                refrescar();
                break;
            case "Importar":
                break;
            case "Exportar":
                break;
            case "Manual":
                vista.datosEspecificosLbl.setText("Material\nTipo\nNumero");
                break;
            case "Electrica":
                vista.datosEspecificosLbl.setText("Potencia");
                break;
        }

    }


    @Override
    public void windowClosing(WindowEvent e) {
        int resp = Util.mensajeConfirmacion("Deseas cerrar la ventana", "Salir");
        if (resp == JOptionPane.OK_OPTION) {
            try {
                guardarDatosConfiguracion();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            Herramienta herramientaSeleccionada = (Herramienta) vista.list1.getSelectedValue();
            vista.nombreTxt.setText(herramientaSeleccionada.getNombre());
            vista.codigoTxt.setText(herramientaSeleccionada.getCodigo());
            vista.precioTxt.setText(String.valueOf(herramientaSeleccionada.getPrecio()));
            vista.stockTxt.setText(String.valueOf(herramientaSeleccionada.getStock()));
            vista.marcaTxt.setText(herramientaSeleccionada.getMarca());
            vista.fechaUltCompraDPicker.setDate(herramientaSeleccionada.getFechaUltComprra());
            if (herramientaSeleccionada instanceof HerramientaElectrica) {
                vista.electricaRadioButton.doClick();
                vista.potenciaTxt.setText(String.valueOf(((HerramientaElectrica) herramientaSeleccionada).getPotencia()));
                vista.inalambricaCheckBox.setSelected(((HerramientaElectrica) herramientaSeleccionada).isInalambrica());
            } else {
                vista.manualRadioButton.doClick();
                vista.materialTxt.setText(((HerramientaManual) herramientaSeleccionada).getMaterial());
                vista.tipoTxt.setText(((HerramientaManual) herramientaSeleccionada).getTipo());
                vista.numeroTxt.setText(String.valueOf(((HerramientaManual) herramientaSeleccionada).getNumero()));
            }
        }
    }

    //no los uso
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }



}