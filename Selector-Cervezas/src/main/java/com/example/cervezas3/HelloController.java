package com.example.cervezas3;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class HelloController
{
    ArrayList<Cerveza> cervezas = new ArrayList<>();
    @FXML
    private Label label1;
    @FXML
    private ComboBox comboBox1;
    @FXML
    private ComboBox comboBox2;
    @FXML
    private Button button1;
    @FXML
    protected void cargarCervezas1()
    {
        cervezas.add(new Cerveza("rubia", "mahou", 1.00));
        cervezas.add(new Cerveza("rubia", "amstel", 1.50));
        cervezas.add(new Cerveza("rubia", "alhambra", 1.70));
        cervezas.add(new Cerveza("tostada", "mahou maestra", 2.00));
        cervezas.add(new Cerveza("tostada", "amstel oro", 2.50));
        cervezas.add(new Cerveza("tostada", "alhambra roja", 2.70));
        cervezas.add(new Cerveza("negra", "ginness", 3.00));
        cervezas.add(new Cerveza("negra", "leffe negra", 3.50));
        cervezas.add(new Cerveza("negra", "1906 black", 3.70));
    }
    @FXML
    protected void initialize()
    {
        // Cargar las cervezas al iniciar la aplicación a través del método initialize
        cargarCervezas1();
    }
    @FXML
    protected void F1()
    {
        // Eliminar los elementos de comboBox2
        comboBox2.getItems().clear();
        // Volcar, vuelta a vuelta, los objetos de la ArrayList "cervezas" en el objeto "cerveza"
        // para cargar los elementos de comboBox2, dependiendo de lo seleccionado en comboBox1
        for (Cerveza cerveza: cervezas)
        {
            // Si el atributo "tipoCerveza" del objeto "cerveza"
            // coincide con el elemento seleccionado de comboBox1...
            if(cerveza.tipoCerveza.contains(comboBox1.getValue().toString()))
            {
                // ...añadir a comboBox2 el atributo "nombreCerveza"
                comboBox2.getItems().add(cerveza.nombreCerveza);
            }
        }
        // Establecer el primer elemento de comboBox2 como el valor por defecto
        comboBox2.getSelectionModel().selectFirst();
    }
    @FXML
    protected void F2() {
        // Buscar en la ArrayList "cervezas"...
        for (Cerveza cerveza : cervezas) {
            // Si el atributo "tipoCerveza" del objeto "cerveza"
            // coincide con el elemento seleccionado de comboBox1...
            if (cerveza.nombreCerveza.contains(comboBox2.getValue().toString())) {
                // mostrar en "label1" el atributo "pvpCerveza" (convertido de double a string)
                label1.setText(String.valueOf(cerveza.pvpCerveza) + " €");
            }
        }
    }
}