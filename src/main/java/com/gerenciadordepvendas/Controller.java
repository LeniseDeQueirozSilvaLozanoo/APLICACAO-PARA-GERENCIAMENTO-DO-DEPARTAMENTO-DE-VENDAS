package com.gerenciadordepvendas;

import com.gerenciadordepvendas.services.DepartamentoServico;
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
import java.util.function.Consumer;

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
        loadView("ListaDepartamentos.fxml", (ListaDepartamentoController controller) -> {
            controller.setDepartamentoServico(new DepartamentoServico());
            controller.updateTableView();
        } );
    }

    @FXML
    public void onMenuItemSobreAcao(){ loadView("About.fxml", x -> {});
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();
            Scene scene = Application.getScene();
            VBox aplicationVBox = (VBox) ((ScrollPane) scene.getRoot()).getContent();

            Node aplicationMenu = aplicationVBox.getChildren().get(0);
            aplicationVBox.getChildren().clear();
            aplicationVBox.getChildren().add(aplicationMenu);
            aplicationVBox.getChildren().addAll(newVBox.getChildren());

              T controller = loader.getController();
              initializingAction.accept(controller);
            System.out.println("sdf");

        }
        catch (IOException e){
            Alerts.showAlert("IO Exception", "Erro ao carregar a página", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}