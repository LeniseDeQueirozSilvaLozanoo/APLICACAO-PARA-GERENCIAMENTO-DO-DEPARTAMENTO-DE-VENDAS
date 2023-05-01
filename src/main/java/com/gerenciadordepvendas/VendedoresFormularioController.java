package com.gerenciadordepvendas;

import com.gerenciadordepvendas.db.DbException;
import com.gerenciadordepvendas.model.entities.Department;
import com.gerenciadordepvendas.model.entities.Seller;
import com.gerenciadordepvendas.model.exceptions.ValidationException;
import com.gerenciadordepvendas.services.DataChangeListener;
import com.gerenciadordepvendas.services.DepartamentoServico;
import com.gerenciadordepvendas.services.VendedoresServico;
import com.util.Alerts;
import com.util.Constraints;
import com.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class VendedoresFormularioController implements Initializable {

    private Seller entity;
    private VendedoresServico vendedoresServico;

    private DepartamentoServico departamentoServico;
    private List<DataChangeListener> dataChangeListeners =  new ArrayList<>();

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
     private DatePicker dbBirthDate;
    @FXML
    private TextField txtBaseSalary;

    @FXML
    private ComboBox<Department> comboBoxDepartment;

    @FXML
    private Label labelErrorName;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorBirthDate;

    @FXML
    private Label labelErrorBaseSalary;

    @FXML
    private Button  btSalve;

    @FXML
    private Button btCancel;
    @FXML
    private ObservableList<Department> obsList;
    public void setServico(VendedoresServico vendedoresServico, DepartamentoServico departamentoServico) {
        this.vendedoresServico = vendedoresServico;
        this.departamentoServico = departamentoServico;
    }

    public void setSeller(Seller entity){
        this.entity = entity;
    }

    public void subscribeDataChaneListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }
    @FXML
    private void onBtSaveAction(ActionEvent event){
        if(entity == null){
            throw new IllegalStateException("Vendedor nulo");
        }
        if(vendedoresServico == null){
            throw new IllegalStateException("Serviço está nulo");
        }
        try {
            entity = getFormData();
            vendedoresServico.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        }
        catch (ValidationException e){
            setErrorMessages(e.getErrors());
        }

        catch (DbException e){
            Alerts.showAlert("Erro ao salvar Vendedor",null, e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }

    private Seller getFormData() {
        Seller sellerObject = new Seller();
        ValidationException exception = new ValidationException("Erro de validação");
        sellerObject.setId(Utils.tryParseToInt(txtId.getText()));

        if(txtName.getText() == null || txtName.getText().trim().equals("")){
            exception.addError("name", "Campo não pode ser vazio");
        }
        sellerObject.setName(txtName.getText());

        if(txtEmail.getText() == null || txtEmail.getText().trim().equals("")){
            exception.addError("email", "Campo não pode ser vazio");
        }
        sellerObject.setEmail(txtEmail.getText());


        if(dbBirthDate.getValue() == null){
            exception.addError("birthDate", "Campo não pode ser vazio");
        }
        else {
            Instant instant = Instant.from(dbBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            sellerObject.setBirthDate(Date.from(instant));
        }

        if(txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().equals("")){
            exception.addError("baseSalary", "Campo não pode ser vazio");
        }
        sellerObject.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));
        sellerObject.setDepartment(comboBoxDepartment.getValue());

        if(exception.getErrors().size() > 0){
            throw exception;
        }

        return sellerObject;
    }

    @FXML
    private void onBtCancelAction(ActionEvent event){
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void initalizaNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName,70);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail,90);
        Utils.formatDatePicker(dbBirthDate,"dd/MM/yyyy");
        initializeComboBoxDepartment();
    }

    public void updateFormData(){
        if(entity == null){
            throw new IllegalStateException("Vendedor está nulo");
        }
        if(entity.getId()==null){
            txtId.setText("");
        }
        else {
            txtId.setText(String.valueOf(entity.getId()));
        }
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
        if(entity.getBirthDate() != null) {
            dbBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }
        if(entity.getDepartment() == null){
            comboBoxDepartment.getSelectionModel().selectFirst();
        }
        else{
            comboBoxDepartment.setValue(entity.getDepartment());
        }
    }

    public void loadAssociatedObjects(){
        if(departamentoServico == null){
            throw new IllegalStateException("Departamento Serviço está nulo!");
        }
        List<Department> list = departamentoServico.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obsList);
    }

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();

        labelErrorName.setText((fields.contains("name") ? errors.get("name") : ""));
        labelErrorEmail.setText((fields.contains("email") ? errors.get("email") : ""));
        labelErrorBirthDate.setText((fields.contains("birthDate") ? errors.get("birthDate") : ""));
        labelErrorBaseSalary.setText((fields.contains("baseSalary") ? errors.get("baseSalary") : ""));
    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }

}
