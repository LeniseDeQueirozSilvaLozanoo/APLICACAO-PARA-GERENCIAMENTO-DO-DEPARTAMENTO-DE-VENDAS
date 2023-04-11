package com.gerenciadordepvendas;

import com.gerenciadordepvendas.db.DbIntegrityException;
import com.gerenciadordepvendas.model.entities.Department;
import com.gerenciadordepvendas.services.DataChangeListener;
import com.gerenciadordepvendas.services.DepartamentoServico;
import com.util.Alerts;
import com.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.ERROR;

public class ListaDepartamentoController implements Initializable, DataChangeListener {

    private DepartamentoServico departamentoServico;

    @FXML
    private TableView<Department> departamentoTableView;
    @FXML
    private TableColumn<Department, Integer> tableColumnId;
    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private TableColumn<Department, Department> tableColumnEDIT;

    @FXML
    private TableColumn<Department, Department> tableColumnREMOVE;

    @FXML
    private Button buttonNovo;

    private ObservableList<Department> observableList;

    @FXML
    public void onBtNewAction(ActionEvent event){
        Stage parentState = Utils.currentStage(event);
        Department departamento = new Department();
        createDialogForm(departamento,"DepartmentForm.fxml",parentState);
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
        initEditButtons();
        initRemoveButtons();
    }

    public void createDialogForm(Department departamentoObj, String absoluteName, Stage parentStage){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            DepartamentoFormularioController departamentoFormularioController = loader.getController();
            departamentoFormularioController.setDepartment(departamentoObj);
            departamentoFormularioController.setDepartmentServico(new DepartamentoServico());
            departamentoFormularioController.subscribeDataChaneListener(this);
            departamentoFormularioController.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Digite os dados do departamento");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();


        }catch (IOException e){
            Alerts.showAlert("IO Exception","Error loadview",e.getMessage(), ERROR);
        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Department, Department>() {
            private final Button button = new Button("Editar");
            @Override
            protected void updateItem(Department obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, "DepartmentForm.fxml",Utils.currentStage(event)));}
        });
    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Department, Department>() {
            private final Button button = new Button("Remover");
            @Override
            protected void updateItem(Department obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Department obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Tem certeza que deseja excluir o item?");
        if (result.get() == ButtonType.OK){
            if (departamentoServico == null){
                throw new IllegalStateException("Serviço está nulo!");
            }
            try{
                departamentoServico.remove(obj);
                updateTableView();
            }
            catch (DbIntegrityException e){
                Alerts.showAlert("Erro ao remover linha", null, e.getMessage(), ERROR);
            }

        }
    }


}
