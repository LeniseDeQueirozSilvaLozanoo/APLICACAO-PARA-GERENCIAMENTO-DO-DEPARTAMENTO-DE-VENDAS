package com.gerenciadordepvendas;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private MenuItem menuItemVendedor;
    @FXML
    private MenuItem menuItemDepartamento;
    @FXML
    private MenuItem menuItemSobre;

    @FXML
    public void onMenuItemVendedorAcao(){
        System.out.println("Teste Vendedor");
    }


    @FXML
    public void onMenuItemDepartamentoAcao(){
        System.out.println("Teste Departamento");
    }


    @FXML
    public void onMenuItemSobreAcao(){
        System.out.println("Teste Sobre");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}