package com.gerenciadordepvendas.model.dao;

import com.gerenciadordepvendas.db.DB;
import com.gerenciadordepvendas.model.dao.impl.DepartmentDaoJDBC;
import com.gerenciadordepvendas.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
