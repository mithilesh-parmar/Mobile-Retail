package test;

import database.DatabaseHelper;
import database.dao.ProductDao;
import database.dao.SalesDao;
import datamodel.*;

import java.util.ArrayList;
import java.util.List;

public class SalesTest {
	public static void main(String[] args) {

		DatabaseHelper.dropTable(SalesDao.TABLE_NAME);
		DatabaseHelper.dropTable(ProductDao.TABLE_NAME);
//		SalesDao salesDao = new SalesDao();
//		Customer customer =new Customer("Abhishek parmar","8561057510","Jecrc");
//		List<Order> orders = new ArrayList<>();
//		orders.add(new Order(new Product("Apple","iPhone10","2131asd31",1312)));
//		salesDao.addSaleToDatabase(new Sale(customer,orders,1,PaymentMode.CASH));
//		salesDao.addSaleToDatabase(new Sale(customer,orders,2,PaymentMode.CARD));
//		salesDao.addSaleToDatabase(new Sale(customer,orders,3,PaymentMode.CARD));
//		salesDao.addSaleToDatabase(new Sale(customer,orders,4,PaymentMode.CARD));
//		salesDao.addSaleToDatabase(new Sale(customer,orders,6,PaymentMode.CARD));
//		salesDao.addSaleToDatabase(new Sale(customer,orders,5,PaymentMode.CARD));
//
//		System.out.println(salesDao.loadSales());
	}
}
