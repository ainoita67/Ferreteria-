package com.ainoall.ferreteria.gui;

import com.ainoall.ferreteria.base.Herramienta;
import com.ainoall.ferreteria.base.HerramientaManual;
import com.ainoall.ferreteria.base.HerramientaElectrica;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class FerreteriaModelo {

    private ArrayList<Herramienta> listaHerramientas;

    public FerreteriaModelo() {
        listaHerramientas = new ArrayList<>();
    }

    public ArrayList<Herramienta> obtenerHerramientas() {
        return listaHerramientas;
    }

    public void altaHerramientaElectrica(String nombre, String codigo, float precio,
                                         int stock, String marca, LocalDate fechaUltCompra,
                                         int potencia, boolean inalambrica) {
        HerramientaElectrica nuevaElectrica = new HerramientaElectrica(nombre, codigo, precio, stock, marca, potencia, inalambrica);
        nuevaElectrica.setFechaUltComprra(fechaUltCompra);
        listaHerramientas.add(nuevaElectrica);
    }

    public void altaHerramientaManual(String nombre, String codigo, float precio,
                                      int stock, String marca, LocalDate fechaUltCompra,
                                      String material, String tipo, float numero) {
        HerramientaManual nuevaManual = new HerramientaManual(nombre, codigo, precio, stock, marca, material, tipo, numero);
        nuevaManual.setFechaUltComprra(fechaUltCompra);
        listaHerramientas.add(nuevaManual);
    }

    public boolean existeCodigo(String codigo) {
        for (Herramienta unaHerramienta : listaHerramientas) {
            if (unaHerramienta.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }

    public void exportarXML(File fichero) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation dom = builder.getDOMImplementation();
        Document documento = dom.createDocument(null, "xml", null);

        //añado el nodo raiz
        Element raiz = documento.createElement("herramientas");
        documento.getDocumentElement().appendChild(raiz);

        Element nodoHerramienta = null;
        Element nodoDatos = null;
        Text texto = null;

        for (Herramienta unaHerramienta : listaHerramientas) {
            //añadimos dentro de herramientas, electricas o manuales
            if (unaHerramienta instanceof HerramientaElectrica) {
                nodoHerramienta = documento.createElement("HerramientaElectrica");
            } else {
                nodoHerramienta = documento.createElement("HerramientaManual");
            }
            raiz.appendChild(nodoHerramienta);

            //dentro de la etiqueta herramienta -> nombre, codigo ....
            nodoDatos = documento.createElement("nombre");
            nodoHerramienta.appendChild(nodoDatos);
            texto = documento.createTextNode(unaHerramienta.getNombre());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("codigo");
            nodoHerramienta.appendChild(nodoDatos);
            texto = documento.createTextNode(unaHerramienta.getCodigo());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("precio");
            nodoHerramienta.appendChild(nodoDatos);
            texto = documento.createTextNode(String.valueOf(unaHerramienta.getPrecio()));
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("stock");
            nodoHerramienta.appendChild(nodoDatos);
            texto = documento.createTextNode(String.valueOf(unaHerramienta.getStock()));
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("marca");
            nodoHerramienta.appendChild(nodoDatos);
            texto = documento.createTextNode(unaHerramienta.getMarca());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("fechaultcompra");
            nodoHerramienta.appendChild(nodoDatos);
            texto = documento.createTextNode(unaHerramienta.getFechaUltComprra().toString());
            nodoDatos.appendChild(texto);

            //ponemos los campos diferentes
            if (unaHerramienta instanceof HerramientaElectrica) {
                nodoDatos = documento.createElement("potencia");
                nodoHerramienta.appendChild(nodoDatos);
                texto = documento.createTextNode(String.valueOf(((HerramientaElectrica) unaHerramienta).getPotencia()));
                nodoDatos.appendChild(texto);

                nodoDatos = documento.createElement("inalambrica");
                nodoHerramienta.appendChild(nodoDatos);
                texto = documento.createTextNode(String.valueOf(((HerramientaElectrica) unaHerramienta).isInalambrica()));
                nodoDatos.appendChild(texto);
            } else {
                nodoDatos = documento.createElement("material");
                nodoHerramienta.appendChild(nodoDatos);
                texto = documento.createTextNode(((HerramientaManual) unaHerramienta).getMaterial());
                nodoDatos.appendChild(texto);

                nodoDatos = documento.createElement("tipo");
                nodoHerramienta.appendChild(nodoDatos);
                texto = documento.createTextNode(((HerramientaManual) unaHerramienta).getTipo());
                nodoDatos.appendChild(texto);

                nodoDatos = documento.createElement("numero");
                nodoHerramienta.appendChild(nodoDatos);
                texto = documento.createTextNode(String.valueOf(((HerramientaManual) unaHerramienta).getNumero()));
                nodoDatos.appendChild(texto);
            }
        }
        //guardo en fichero
        Source source = new DOMSource(documento);
        Result resultado = new StreamResult(fichero);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, resultado);
    }

    public void importarXML(File fichero) throws ParserConfigurationException, IOException, SAXException {
        listaHerramientas = new ArrayList<Herramienta>();
        HerramientaElectrica nuevaElectrica = null;
        HerramientaManual nuevaManual = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(fichero);

        NodeList listaElementos = documento.getElementsByTagName("*");

        for (int i = 0; i < listaElementos.getLength(); i++) {
            Element nodoHerramienta = (Element) listaElementos.item(i);

            if (nodoHerramienta.getTagName().equals("HerramientaElectrica")) {
                nuevaElectrica = new HerramientaElectrica();
                nuevaElectrica.setNombre(nodoHerramienta.getChildNodes().item(0).getTextContent());
                nuevaElectrica.setCodigo(nodoHerramienta.getChildNodes().item(0).getTextContent());
                nuevaElectrica.setPrecio(Float.parseFloat(nodoHerramienta.getChildNodes().item(0).getTextContent()));
                nuevaElectrica.setStock(Integer.parseInt(nodoHerramienta.getChildNodes().item(0).getTextContent()));
                nuevaElectrica.setMarca(nodoHerramienta.getChildNodes().item(0).getTextContent());
                nuevaElectrica.setFechaUltComprra(LocalDate.parse(nodoHerramienta.getChildNodes().item(0).getTextContent()));
                nuevaElectrica.setPotencia(Integer.parseInt(nodoHerramienta.getChildNodes().item(0).getTextContent()));
                nuevaElectrica.setInalambrica(Boolean.parseBoolean(nodoHerramienta.getChildNodes().item(0).getTextContent()));
                listaHerramientas.add(nuevaElectrica);
            } else {
                if (nodoHerramienta.getTagName().equals("HerramientaManual")) {
                    nuevaManual = new HerramientaManual();
                    nuevaManual.setNombre(nodoHerramienta.getChildNodes().item(0).getTextContent());
                    nuevaManual.setCodigo(nodoHerramienta.getChildNodes().item(0).getTextContent());
                    nuevaManual.setPrecio(Float.parseFloat(nodoHerramienta.getChildNodes().item(0).getTextContent()));
                    nuevaManual.setStock(Integer.parseInt(nodoHerramienta.getChildNodes().item(0).getTextContent()));
                    nuevaManual.setMarca(nodoHerramienta.getChildNodes().item(0).getTextContent());
                    nuevaManual.setFechaUltComprra(LocalDate.parse(nodoHerramienta.getChildNodes().item(0).getTextContent()));
                    nuevaManual.setMaterial(nodoHerramienta.getChildNodes().item(0).getTextContent());
                    nuevaManual.setTipo(nodoHerramienta.getChildNodes().item(0).getTextContent());
                    nuevaManual.setNumero(Float.parseFloat(nodoHerramienta.getChildNodes().item(0).getTextContent()));
                    listaHerramientas.add(nuevaManual);
                }
            }

        }
    }
}