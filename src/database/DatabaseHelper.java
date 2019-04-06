package database;

import com.sun.rowset.CachedRowSetImpl;
import database.dao.ProductDao;
import datamodel.Product;

import java.sql.*;

import static database.dao.ProductDao.TABLE_NAME;

public abstract class DatabaseHelper {

	private static final String DATABASE_NAME = "MOBILE_RETAIL";
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String CONNECTION_URL = "jdbc:derby:"+DATABASE_NAME+";create=true";
	private static Connection connection;

	/**
	 * function to make a connection to database
	 */
	private static void connectDatabase(){
		// register the driver
		try{
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(CONNECTION_URL);
		} catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * disconnect driver from database
	 */
	private static void disconnectDatabase(){
		try{
			if (connection != null) connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * execute query to get RESULTSET (For Select statement)
	 * @param query
	 * @return
	 */
	public static ResultSet executeSelectQuery(String query){
		Statement statement = null;
		ResultSet resultSet = null;
		CachedRowSetImpl cachedRowSet = null;
		try{
			connectDatabase();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			cachedRowSet = new CachedRowSetImpl(); // cache the resultset
			cachedRowSet.populate(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try{
				// close the connections
				if (resultSet != null) resultSet.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			disconnectDatabase();
		}
		return cachedRowSet;
	}

	/**
	 * used to execute query (drop statement)
	 *
	 * @param s
	 * @param query
	 * @return
	 */
	public static int executeQuery(String query, String parameter){
		PreparedStatement statement = null;
		int result =0;
		try{
			connectDatabase(); // connect to database
			statement = connection.prepareStatement(query);
			statement.setString(1,parameter);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try{
				if (statement != null) statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			disconnectDatabase(); // disconnect from database
		}
		return result;
	}

	/**
	 * used to execute prepared statement (Insert statements)
	 * @param query
	 * @param p product to be inserted
	 * @return
	 */
	public static boolean executePreparedStatement(String query, Product p){
		PreparedStatement statement = null;
		boolean result  = false;
		try{
			connectDatabase();
			statement = connection.prepareStatement(query);
			// set the parameters
			statement.setString(1,p.getId().toString());
			statement.setString(2,p.getManufacturer());
			statement.setString(3,p.getModel());
			statement.setString(4,p.getImeiNumber());
			statement.setFloat(5,p.getRate());
			result =  statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try{
				if (statement != null) statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			disconnectDatabase();
		}
		return result;
	}

	/**
	 * create table if doesn't exists
	 * @param query
	 * @param tableName name of the table to be created
	 * @return
	 */
	public static int createTable(String query,String tableName){
		DatabaseMetaData databaseMetaData ;
		ResultSet resultSet = null;
		Statement statement = null;
		int result = 0;
		try{
			connectDatabase();
			databaseMetaData = connection.getMetaData();
			// find if any table with same name exists
			resultSet = databaseMetaData.getTables(null,null,tableName,null);
			if (!resultSet.next()) {
				// if not the create one
				statement = connection.createStatement();
				result = statement.executeUpdate(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try{
				if (statement != null) statement.close();
				if (resultSet != null) resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			disconnectDatabase();
		}
		return result;
	}

	/**
	 * drop the table from database
	 * @param tableName
	 * @return
	 */
	public static boolean dropTable(String tableName){
		String query = "drop table "+tableName;
		Statement statement = null;
		boolean result = false;
		try{
			connectDatabase();
			System.out.println("Query: "+query);
			statement = connection.createStatement();
			result = statement.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try{
				if (statement !=  null) statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			disconnectDatabase();
		}
		return result;
	}
}
