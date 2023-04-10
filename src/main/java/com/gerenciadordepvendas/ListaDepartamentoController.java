package com.gerenciadordepvendas;

import com.gerenciadordepvendas.model.entities.Department;
import com.gerenciadordepvendas.services.DepartamentoServico;
import com.util.Alerts;
import com.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
    public void onBtNewAction(ActionEvent event){
        Stage parentState = Utils.currentStage(event);
        createDialogForm("DepartmentForm.fxml",parentState);
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

    public void createDialogForm(String absoluteName, Stage parentStage){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Digite os dados do departamento");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();


        }catch (IOException e){
            Alerts.showAlert("IO Exception","Error loadview",e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
