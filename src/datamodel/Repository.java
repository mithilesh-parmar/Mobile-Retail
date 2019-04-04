package datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.CSVHelper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Repository {

	private static Repository instance ;
	private ObservableList<Product> productList;
	private ObservableList<Order> ordersList;
	private String PRODUCT_FILE = "Products.csv";

	public static Repository getInstance(){
		if (instance == null){
			synchronized (Repository.class){
				instance = new Repository();
			}
		}
		return instance;
	}

	public void loadProducts() {
		productList.addAll(new CSVHelper(PRODUCT_FILE).readFile());
	}

	public void saveProductToFile(Product p){
		new CSVHelper(PRODUCT_FILE).saveToFile(p);
	}

	private Repository(){
		productList = FXCollections.observableArrayList();
		ordersList = FXCollections.observableArrayList();
	}

	public void addProduct(Product p){
		productList.add(p);
	}
	public void removeProduct(Product p){
		productList.remove(p);
	}

	public void addOrder(Order p){
		if (ordersList.contains(p)) ordersList.get(ordersList.indexOf(p)).increaseQuantityByOne();
		else ordersList.add(p);
	}

	public void removeOrder(Product p){
		ordersList.remove(p);
	}

	public ObservableList<Order> getOrdersList() {
		return ordersList;
	}

	public ObservableList<Product> getProductList() {
		return productList;
	}


}
