package test;

import database.DatabaseHelper;
import database.dao.ProductDao;
import datamodel.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

import static database.dao.ProductDao.TABLE_NAME;

public class DatabaseTest {

	public static void main(String[] args) throws SQLException {
		ProductDao dao = new ProductDao();
		for (int i = 1; i < 10; i++) {
			dao.addProductToDatabase(new Product("Apple","iPhone "+i,String.valueOf(new Random().nextDouble()),20000*i));
		}
		System.out.println(dao.loadProducts());
	}
}
