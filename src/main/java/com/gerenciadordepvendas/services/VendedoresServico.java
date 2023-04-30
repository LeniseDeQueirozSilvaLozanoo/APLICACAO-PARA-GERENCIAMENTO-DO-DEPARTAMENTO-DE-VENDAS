package com.gerenciadordepvendas.services;

import com.gerenciadordepvendas.model.dao.DaoFactory;
import com.gerenciadordepvendas.model.dao.DepartmentDao;
import com.gerenciadordepvendas.model.dao.SellerDao;
import com.gerenciadordepvendas.model.entities.Department;
import com.gerenciadordepvendas.model.entities.Seller;

import java.util.List;

public class VendedoresServico {

    private SellerDao dao = DaoFactory.createSellerDao();
    public List<Seller>findAll(){
            return dao.findAll();
    }

    public  void saveOrUpdate(Seller sellerObject){
        if(sellerObject.getId() == null){
            dao.insert(sellerObject);
        }
        else {
            dao.update(sellerObject);
        }
    }

    public void remove(Seller obj) {
        dao.deleteById(obj.getId());
    }
}
