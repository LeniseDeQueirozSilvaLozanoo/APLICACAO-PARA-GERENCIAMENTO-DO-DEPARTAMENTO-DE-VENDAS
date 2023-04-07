package com.gerenciadordepvendas;

import com.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
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
    public void onMenuItemDepartamentoAcao(){loadView("ListaDepartamentos.fxml");
    }


    @FXML
    public void onMenuItemSobreAcao(){ loadView("About.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void loadView(String absoluteName){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();
            Scene scene = Application.getScene();
            VBox aplicationVBox = (VBox) ((ScrollPane) scene.getRoot()).getContent();

            Node aplicationMenu = aplicationVBox.getChildren().get(0);
            aplicationVBox.getChildren().clear();
            aplicationVBox.getChildren().add(aplicationMenu);
            aplicationVBox.getChildren().addAll(newVBox.getChildren());
        }
        catch (IOException e){
            Alerts.showAlert("IO Exception", "Error ao carregar a página", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}