package datamodel;

import database.dao.ProductDao;
import database.dao.SalesDao;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableFloatValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Arrays;

public class Repository {

	private static Repository instance ; // singleton instance
	private ObservableList<Product> productList; // product list loaded from database
	private ObservableList<Order> ordersList; // list for tableview
	private ObservableList<PaymentMode> paymentModes; // all available payment modes-
	private ObservableList<Sale> salesList; // sales list
	private ProductDao productDao; // dao for database operations
	private SalesDao salesDao; // dao for database operations
	private FloatProperty totalAmount; // total amount of products
	private FloatProperty totalPayableAmount; // total payable amount including gst
	private IntegerProperty invoiceNumberProperty = new SimpleIntegerProperty(1); // invoice number
	private final String INVOICE_FILE = "src/res/invoice_file.txt";
	private File invoiceFile = new File(INVOICE_FILE);
	private FileWriter writer;
	private FileReader reader;


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
	 * load sales from database into list
	 */

	public void loadSales(){
		salesList.addAll(salesDao.loadSales());
		invoiceNumberProperty.set(getInvoiceNumber());
	}

	/**
	 * add a product to database
	 * @param p
	 */
	public boolean saveProductToDatabase(Product p){
		return productDao.addProductToDatabase(p);
	}

	/**
	 * sace sale item to database
	 * @param s
	 */
	public boolean saveSaleToDatabase(Sale s){
		return salesDao.addSaleToDatabase(s);
	}

	/**
	 * remove product from database
	 * @param p
	 */
	private void removeProductFromDatabase(Product p){productDao.removeProductFromDatabase(p);}

	/**
	 * private constructor
	 * initialize lists and dao
	 */
	private Repository(){
		productList = FXCollections.observableArrayList();
		ordersList = FXCollections.observableArrayList();
		paymentModes = FXCollections.observableArrayList();
		salesList = FXCollections.observableArrayList();
		totalAmount = new SimpleFloatProperty(0);
		totalPayableAmount = new SimpleFloatProperty(0);
		productDao = new ProductDao();
		salesDao = new SalesDao();


		if (!invoiceFile.exists()) {
			try {
				 invoiceFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		loadSales();
		loadProducts();

		// add listener to invoice number
//		salesList.addListener((ListChangeListener<Sale>) c -> invoiceNumberProperty.set(salesList.size() + 1));
	}

	/**
	 * add order to tableview
	 * if already consists then increase its quantity
	 * @param p
	 */
	public void addOrder(Order p){
		ordersList.add(p);
		setTotalAmount(totalAmount.getValue()+p.getAmount());
	}

	/**
	 * add product to database and the product list
	 * called when product is added from inventory tab
	 * @param p
	 */

	public void addProductToInventory(Product p){
		productList.add(p);
		saveProductToDatabase(p);
	}

	/**
	 * add sale to salelist
	 * @param s
	 */
	public void addSale(Sale s){
		salesList.add(s);
		saveInvoiceNumberToFile(s.getInvoiceNumber()+1);
		invoiceNumberProperty.set(getInvoiceNumber());
		System.out.println(saveSaleToDatabase(s));
	}

	public void saveInvoiceNumberToFile(int number){
		try {
			writer = new FileWriter(invoiceFile);
			System.out.println("Writing to file: "+number);
			writer.write(number);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public int getInvoiceNumber(){
		int n = -1;
		if (invoiceFile.length()<=0)return 1;
		try {

			reader = new FileReader(invoiceFile);
			n = reader.read();
			System.out.println("Invoice Number "+n);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try{
				if (reader!=null)reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return n;
	}
	/**
	 * remove product from database
	 * called when product is removed from inventory table
	 *
	 * @param p
	 */
	public void removeProductFromInventroy(Product p) {
		productList.remove(p);
		removeProductFromDatabase(p);
	}

	/**
	 * set the total amount to totalamount property
	 * calculate gst amount
	 * add it to total amount
	 * @param amount
	 */

	private void setTotalAmount(float amount){
		// set the totalamount to previous plus the
		totalAmount.set(amount);
		float gst = getGSTPercentage(); // get the gst percentage
		float gstAmount = (gst / 100) * totalAmount.getValue(); // calculate gst amount
		float value = totalAmount.getValue() + gstAmount;  // add gstamount to totalamount
		setTotalPayableAmount(value);
	}

	/**
	 * set total payable amount with gst
	 * @param amount
	 */
	private void setTotalPayableAmount(float amount){
		totalPayableAmount.set(amount);
	}
	/**
	 * remove order form tableview on backspace press
	 * @param p
	 */
	public void removeOrder(Order p){
		ordersList.remove(p);
		// change the total amount
		setTotalAmount(totalAmount.getValue() - p.getAmount());
	}

	/**
	 * returns the gst percentage for goods
	 * @return
	 */
	public int getGSTPercentage(){
		return 12 ;
	}


	/**
	 * returns available payment modes
	 * @return
	 */
	public ObservableList<PaymentMode> getPaymentModes(){
		paymentModes.addAll(Arrays.asList(PaymentMode.values()));
		return paymentModes;
	}

	/**
	 * observable list of sales
	 * @return
	 */
	public ObservableList<Sale> getSalesList() {
		return salesList;
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

	/**
	 * clear the order by clearing orders list and setting totalpayableamount and totalamount to 0
	 */
	public void clearOrder(){
		totalAmount.set(0f);
		totalPayableAmount.set(0f);
		ordersList.clear();
	}

	// getters and setters
	public FloatProperty totalAmountProperty() {
		return totalAmount;
	}

	public FloatProperty totalPayableAmountProperty() {
		return totalPayableAmount;
	}

	public int getInvoiceNumberProperty() {
		return invoiceNumberProperty.get();
	}

	public IntegerProperty invoiceNumberPropertyProperty() {
		return invoiceNumberProperty;
	}

	public void setInvoiceNumberProperty(int invoiceNumberProperty) {
		this.invoiceNumberProperty.set(invoiceNumberProperty);
	}

	/**
	 * close all the connections
	 * shutdown database
	 * perform all the write operations
	 */
	//TODO
	public void closeProgram() {

	}

	public void removeSaleFromDatabase(Sale sale) {
		salesDao.removeSaleFromDatabase(sale);
	}
}
