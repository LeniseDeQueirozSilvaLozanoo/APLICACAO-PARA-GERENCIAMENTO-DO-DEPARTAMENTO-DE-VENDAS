package com.gerenciadordepvendas;

import com.entidades.Departamento;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ListaDepartamentoController implements Initializable {

    @FXML
    private TableView<Departamento> departamentoTableView;
    @FXML
    private TableColumn<Departamento, Integer> tableColumnId;
    @FXML
    private TableColumn<Departamento, String> tableColumnNome;
    @FXML
    private Button buttonNovo;

    @FXML
    public void onBtNewAction(){
        System.out.println("Teste botão");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void initializeNodes(){
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
//        Stage stage = (Stage) Application.getScene().getWindow();
//        departamentoTableView.prefHeightProperty().bind(stage.heightProperty());

    }
}
