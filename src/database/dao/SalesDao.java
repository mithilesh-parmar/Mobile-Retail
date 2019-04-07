package database.dao;

import  database.DatabaseHelper;
import database.EntityInterface;
import datamodel.Customer;
import datamodel.PaymentMode;
import datamodel.Sale;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SalesDao implements EntityInterface {

	// table Columns
	public enum COLUMNS{
//		ID,
		DATE,
		INVOICE_NUMBER,
		CUSTOMER_ID,
		NAME,
		MOBILE_NUMBER,
		ADDRESS,
		PRODUCTS,
		PAYMENT_MODE;
	}

	public static final String TABLE_NAME = "SALES";

	private final String INSERT_QUERY = "INSERT INTO "+TABLE_NAME + " ( " +
//			COLUMNS.ID.toString() + ", "+
			COLUMNS.DATE.toString() + ", "+
			COLUMNS.INVOICE_NUMBER.toString() + ", "+
			COLUMNS.CUSTOMER_ID.toString() + ", "+
			COLUMNS.NAME.toString() + ", "+
			COLUMNS.MOBILE_NUMBER.toString() + ", "+
			COLUMNS.ADDRESS.toString() + ", "+
			COLUMNS.PRODUCTS.toString() + ", "+
			COLUMNS.PAYMENT_MODE.toString()  +
			") values (?,?,?,?,?,?,?,?)";


	private final String CREATE_TABLE_QUERY = "CREATE TABLE "+TABLE_NAME + " ( "+
//			COLUMNS.ID.toString() + " varchar(255), "+
			COLUMNS.DATE.toString() + " DATE, "+
			COLUMNS.INVOICE_NUMBER.toString() + " INTEGER NOT NULL PRIMARY KEY,  "+
			COLUMNS.CUSTOMER_ID.toString() + " varchar(255), "+
			COLUMNS.NAME.toString() + " varchar(255), "+
			COLUMNS.MOBILE_NUMBER.toString() + " varchar(255), "+
			COLUMNS.ADDRESS.toString() + " varchar(255), "+
			COLUMNS.PRODUCTS.toString() + " varchar(255), "+
			COLUMNS.PAYMENT_MODE.toString() + " varchar(255) " +
			")";

	public SalesDao(){
		createTable();
	}

	/**
	 * load Sales from database
	 * @return
	 */
	public List<Sale> loadSales(){
		String query = "SELECT * FROM "+TABLE_NAME;
		ResultSet resultSet = DatabaseHelper.executeSelectQuery(query);
		return parseProductsResultSet(resultSet);
	}

	/**
	 * parse the resultset received into a list of products
	 * @param resultSet
	 * @return
	 */
	private List<Sale> parseProductsResultSet(ResultSet resultSet){
		List<Sale> saleList = new ArrayList<>();
		try{
			while (resultSet.next()){
				// while there is data
				Sale sale = new Sale();
				// set the parameters to product

				/**
				 * 			statement.setString(1,s.getId().toString());
				 * 			statement.setDate(2, Date.valueOf(s.getDate()));
				 * 			statement.setInt(3,s.getInvoiceNumber());
				 * 			statement.setString(4,s.getCustomer().getId().toString());
				 * 			statement.setString(5,s.getCustomer().getName());
				 * 			statement.setString(6,s.getCustomer().getMobileNumber());
				 *
				 * 			statement.setString(7,s.getCustomer().getAddress());
				 * 			statement.setString(8,s.getProductsListAsString());
				 * 			statement.setString(9,s.getPaymentMode().toString());
				 */

//				sale.setId(UUID.fromString(resultSet.getString(COLUMNS.ID.toString())));
				sale.setDate(LocalDate.parse(resultSet.getString(COLUMNS.DATE.toString())));
				sale.setInvoiceNumber(resultSet.getInt(COLUMNS.INVOICE_NUMBER.toString()));
				Customer customer = new Customer();
				customer.setId(UUID.fromString(resultSet.getString(COLUMNS.CUSTOMER_ID.toString())));
				customer.setName(resultSet.getString(COLUMNS.NAME.toString()));
				customer.setMobileNumber(resultSet.getString(COLUMNS.MOBILE_NUMBER.toString()));
				customer.setAddress(resultSet.getString(COLUMNS.ADDRESS.toString()));
				sale.setCustomer(customer);
				sale.setOrderList(resultSet.getString(COLUMNS.PRODUCTS.toString()));
				sale.setPaymentMode(resultSet.getString(COLUMNS.PAYMENT_MODE.toString()));
				saleList.add(sale);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try{
				if (resultSet != null) resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return saleList;
	}


	public void addSaleToDatabase(Sale s){
		DatabaseHelper.executePreparedStatement(INSERT_QUERY,s);
	}

	/**
	 * create table if does not exists
	 */
	@Override
	public void createTable() {
		DatabaseHelper.createTable(CREATE_TABLE_QUERY,TABLE_NAME);
	}

}
