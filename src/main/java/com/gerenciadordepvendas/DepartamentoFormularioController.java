package com.gerenciadordepvendas;

import com.gerenciadordepvendas.db.DbException;
import com.gerenciadordepvendas.model.entities.Department;
import com.gerenciadordepvendas.model.exceptions.ValidationException;
import com.gerenciadordepvendas.services.DataChangeListener;
import com.gerenciadordepvendas.services.DepartamentoServico;
import com.util.Alerts;
import com.util.Constraints;
import com.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class DepartamentoFormularioController implements Initializable {

    private Department entity;
    private DepartamentoServico departamentoServico;
    private List<DataChangeListener> dataChangeListeners =  new ArrayList<>();

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private Label labelErrorName;
    @FXML
    private Button  btSalve;
    @FXML
    private Button btCancel;

    public void setDepartmentServico(DepartamentoServico departamentoServico) {
        this.departamentoServico = departamentoServico;
    }

    public void setDepartment(Department entity){
        this.entity = entity;
    }

    public void subscribeDataChaneListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }
    @FXML
    private void onBtSaveAction(ActionEvent event){
        if(entity == null){
            throw new IllegalStateException("Departamento nulo");
        }
        if(departamentoServico == null){
            throw new IllegalStateException("Serviço está nulo");
        }
        try {
            entity = getFormData();
            departamentoServico.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        }
        catch (ValidationException e){
            setErrorMessages(e.getErrors());
        }

        catch (DbException e){
            Alerts.showAlert("Erro ao salvar Departamento",null, e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }

    private Department getFormData() {
        Department departmentObject = new Department();
        ValidationException exception = new ValidationException("Erro de validação");
        departmentObject.setId(Utils.tryParseToInt(txtId.getText()));
        if(txtName.getText() == null || txtName.getText().trim().equals("")){
            exception.addError("name", "Campo não pode ser vazio");
        }
        departmentObject.setName(txtName.getText());

        if(exception.getErrors().size() > 0){
            throw exception;
        }

        return departmentObject;
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
        Constraints.setTextFieldMaxLength(txtName,30);
    }

    public void updateFormData(){
        if(entity == null){
            throw new IllegalStateException("Departamento está nulo");
        }
        if(entity.getId()==null){
            txtId.setText("");
        }
        else {
            txtId.setText(String.valueOf(entity.getId()));
        }
        txtName.setText(entity.getName());
    }

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();
        if (fields.contains("name")){
            labelErrorName.setText(errors.get("name"));

        }
    }
}
