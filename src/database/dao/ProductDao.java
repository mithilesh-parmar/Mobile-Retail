package database.dao;

import database.DatabaseHelper;
import database.EntityInterface;
import datamodel.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductDao implements EntityInterface {

	// Columns for product table
	public enum COLUMNS{
		ID,
		MANUFACTURER,
		MODEL,
		IEMINUMBER,
		RATE;
	}

	public static final String TABLE_NAME = "PRODUCTS";

	// insert query for product
	private final String INSERT_QUERY = "INSERT INTO "+TABLE_NAME+" (" +
			COLUMNS.ID.toString() + "," +
			COLUMNS.MANUFACTURER.toString() + "," +
			COLUMNS.MODEL.toString() + "," +
			COLUMNS.IEMINUMBER.toString() + "," +
			COLUMNS.RATE.toString() +
			")  ";

	// create table query
	private final String CREAT_TABLE_QUERY = "CREATE TABLE "+TABLE_NAME+" ( " +
			COLUMNS.ID.toString() +" varchar(255), "+
			COLUMNS.MANUFACTURER.toString() + " varchar(255), "+
			COLUMNS.MODEL.toString() + " varchar(255), "+
			COLUMNS.IEMINUMBER.toString() + " LONG VARCHAR, "+
			COLUMNS.RATE.toString() + " float(4)"+
			")";

	public ProductDao() {
		createTable(); // create table on first initialization if not exists
	}

	/**
	 * load products from database
	 * @return
	 */
	public List<Product> loadProducts(){
		String query = "SELECT * FROM "+TABLE_NAME;
		ResultSet resultSet = DatabaseHelper.executeSelectQuery(query);
		return parseProductsResultSet(resultSet);
	}


	/**
	 * add product to database
	 * @param product to be added to database
	 */

	public boolean addProductToDatabase(Product product){
		String query = INSERT_QUERY + " VALUES (?,?,?,?,?)";
		return DatabaseHelper.executePreparedStatement(query,product);
	}

	/**
	 * remove product from database
	 * @param product to be removed
	 */
	public void removeProductFromDatabase(Product product){
		String query = "DELETE FROM  "+TABLE_NAME +" WHERE "+COLUMNS.ID + " = (?)";
		DatabaseHelper.executeQuery(query,product.getId().toString());
	}

	/**
	 * create table if does not exists
	 */
	@Override
	public void createTable() {
		DatabaseHelper.createTable(CREAT_TABLE_QUERY,TABLE_NAME);
	}

	/**
	 * parse the resultset received into a list of products
	 * @param resultSet
	 * @return
	 */
	private List<Product> parseProductsResultSet(ResultSet resultSet){
		List<Product> productList = new ArrayList<>();
		try{
			while (resultSet.next()){
				// while there is data
				Product product = new Product();
				// set the parameters to product
				product.setId(UUID.fromString(resultSet.getString(COLUMNS.ID.toString())));
				product.setManufacturer(resultSet.getString(COLUMNS.MANUFACTURER.toString()));
				product.setModel(resultSet.getString(COLUMNS.MODEL.toString()));
				product.setImeiNumber(resultSet.getString(COLUMNS.IEMINUMBER.toString()));
				product.setRate(resultSet.getFloat(COLUMNS.RATE.toString()));
				productList.add(product);
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
		return productList;
	}


}
