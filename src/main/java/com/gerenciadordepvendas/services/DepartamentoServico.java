package com.gerenciadordepvendas.services;

import com.gerenciadordepvendas.model.dao.DaoFactory;
import com.gerenciadordepvendas.model.dao.DepartmentDao;
import com.gerenciadordepvendas.model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartamentoServico {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();
    public List<Department>findAll(){
            return dao.findAll();
    }

    public  void saveOrUpdate(Department departamentoObject){
        if(departamentoObject.getId() == null){
            dao.insert(departamentoObject);
        }
        else {
            dao.update(departamentoObject);
        }
    }
}
