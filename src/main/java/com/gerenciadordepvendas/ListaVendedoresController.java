package com.gerenciadordepvendas;

import com.gerenciadordepvendas.db.DbIntegrityException;
import com.gerenciadordepvendas.model.entities.Department;
import com.gerenciadordepvendas.model.entities.Seller;
import com.gerenciadordepvendas.services.DataChangeListener;
import com.gerenciadordepvendas.services.DepartamentoServico;
import com.gerenciadordepvendas.services.VendedoresServico;
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

public class ListaVendedoresController implements Initializable, DataChangeListener {

    private VendedoresServico vendedoresServico;

    @FXML
    private TableView<Seller> sellerTableView;
    @FXML
    private TableColumn<Seller, Integer> tableColumnId;
    @FXML
    private TableColumn<Seller, String> tableColumnName;

    @FXML
    private TableColumn<Seller, Seller> tableColumnEDIT;

    @FXML
    private TableColumn<Seller, Seller> tableColumnREMOVE;

    @FXML
    private Button buttonNovo;

    private ObservableList<Seller> observableList;

    @FXML
    public void onBtNewAction(ActionEvent event){
        Stage parentState = Utils.currentStage(event);
        Seller seller = new Seller();
        createDialogForm(seller,"DepartmentForm.fxml",parentState);
    }

    public void setVendedoresServico(VendedoresServico vendedoresServico) {
        this.vendedoresServico = vendedoresServico;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes(){
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        Stage stage = (Stage) Application.getScene().getWindow();
        sellerTableView.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView(){
        if(vendedoresServico == null){
            throw new IllegalStateException("O serviço está nulo!");
        }
        List<Seller> list = vendedoresServico.findAll();
        observableList = FXCollections.observableArrayList(list);
        sellerTableView.setItems(observableList);
        initEditButtons();
        initRemoveButtons();
    }

    public void createDialogForm(Seller vendedoresObj, String absoluteName, Stage parentStage){
//        try{
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//            Pane pane = loader.load();
//
//            VendedoresFormularioController vendedoresFormularioController = loader.getController();
//            vendedoresFormularioController.setSeller(vendedoresObj);
//            vendedoresFormularioController.setSellerServico(new VendedoresServico());
//            vendedoresFormularioController.subscribeDataChaneListener(this);
//            vendedoresFormularioController.updateFormData();
//
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Digite os dados do departamento");
//            dialogStage.setScene(new Scene(pane));
//            dialogStage.setResizable(false);
//            dialogStage.initOwner(parentStage);
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.showAndWait();
//
//
//        }catch (IOException e){
//            Alerts.showAlert("IO Exception","Error loadview",e.getMessage(), ERROR);
//        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("Editar");
            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, "SellerForm.fxml",Utils.currentStage(event)));}
        });
    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("Remover");
            @Override
            protected void updateItem(Seller obj, boolean empty) {
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

    private void removeEntity(Seller obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Tem certeza que deseja excluir o item?");
        if (result.get() == ButtonType.OK){
            if (vendedoresServico == null){
                throw new IllegalStateException("Serviço está nulo!");
            }
            try{
                vendedoresServico.remove(obj);
                updateTableView();
            }
            catch (DbIntegrityException e){
                Alerts.showAlert("Erro ao remover linha", null, e.getMessage(), ERROR);
            }

        }
    }


}
