package datamodel;

import database.dao.ProductDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.CSVHelper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Repository {

	private static Repository instance ; // singleton instance
	private ObservableList<Product> productList; // product list loaded from database
	private ObservableList<Order> ordersList; // list for tableview
	private String PRODUCT_FILE = "Products.csv"; // file to save data to
	private ProductDao productDao; // dao for database operations

	/**
	 * Synchronized method for getting an instance
	 * @return
	 */
	public static Repository getInstance(){
		if (instance == null){
			synchronized (Repository.class){
				instance = new Repository();
			}
		}
		return instance;
	}

	/**
	 * load products into productlist from database
	 */
	public void loadProducts() {
		productList.addAll(productDao.loadProducts());
	}

	/**
	 * add a product to database
	 * @param p
	 */
	public void saveProductToDatabase(Product p){
		productDao.addProductToDatabase(p);
	}

	/**
	 * private constructor
	 * initialize lists and dao
	 */
	private Repository(){
		productList = FXCollections.observableArrayList();
		ordersList = FXCollections.observableArrayList();
		productDao = new ProductDao();
	}

	/**
	 * add order to tableview
	 * if already consists then increase its quantity
	 * @param p
	 */
	public void addOrder(Order p){
		if (ordersList.contains(p)) ordersList.get(ordersList.indexOf(p)).increaseQuantityByOne();
		else ordersList.add(p);
	}

	/**
	 * remove order form tableview on backspace press
	 * @param p
	 */
	public void removeOrder(Product p){
		ordersList.remove(p);
	}

	/**
	 * observable list for tableview
	 * @return
	 */
	public ObservableList<Order> getOrdersList() {
		return ordersList;
	}

	/**
	 * observable list for listview
	 * @return
	 */
	public ObservableList<Product> getProductList() {
		return productList;
	}


}
