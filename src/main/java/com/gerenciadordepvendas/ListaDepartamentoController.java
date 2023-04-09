package com.gerenciadordepvendas;

import com.gerenciadordepvendas.model.entities.Department;
import com.gerenciadordepvendas.services.DepartamentoServico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListaDepartamentoController implements Initializable {

    private DepartamentoServico departamentoServico;

    @FXML
    private TableView<Department> departamentoTableView;
    @FXML
    private TableColumn<Department, Integer> tableColumnId;
    @FXML
    private TableColumn<Department, String> tableColumnName;
    @FXML
    private Button buttonNovo;

    private ObservableList<Department> observableList;

    @FXML
    public void onBtNewAction(){
        System.out.println("Teste botão");
    }

    public void setDepartamentoServico(DepartamentoServico departamentoServico) {
        this.departamentoServico = departamentoServico;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes(){
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        Stage stage = (Stage) Application.getScene().getWindow();
        departamentoTableView.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView(){
        if(departamentoServico == null){
            throw new IllegalStateException("O serviço está nulo!");
        }
        List<Department> list = departamentoServico.findAll();
        observableList = FXCollections.observableArrayList(list);
        departamentoTableView.setItems(observableList);
    }
}
