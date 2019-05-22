package datamodel;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.*;

public class Sale {

	// properties
	private SimpleStringProperty name = new SimpleStringProperty(""); // customer name
	private	SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>(); // sale date
	private SimpleIntegerProperty invoiceNumber = new SimpleIntegerProperty(); // invoice number
	private SimpleStringProperty mobileNumber = new SimpleStringProperty(); // customer mobile number
	private SimpleStringProperty address = new SimpleStringProperty(); // customer address
	private SimpleStringProperty products = new SimpleStringProperty(); // orderlist in string
	private SimpleStringProperty paymentMode = new SimpleStringProperty(); // paymentmode
	private SimpleFloatProperty total_amount = new SimpleFloatProperty(0f);
	private SimpleFloatProperty sgst_amount = new SimpleFloatProperty(0f);
	private SimpleFloatProperty cgst_amount = new SimpleFloatProperty(0f);
	private SimpleFloatProperty igst_amount = new SimpleFloatProperty(0f);

	// objects
	private Customer customer; // customer
	private Organization organization; // company
	private List<Order> orderList; // orders for the customer
	private UUID id; // id of invoice

	public Sale(Customer customer, List<Order> orderList, int invoiceNumber ,PaymentMode paymentMode) {
		id = UUID.randomUUID(); // set a random id
		date.set(LocalDate.now()); // set the local date
		this.customer = customer;
		this.organization = Organization.getInstance();
		this.orderList = orderList;
		setOrderList(getProductsListAsString()); // set products property string from list of products
		this.invoiceNumber.set(invoiceNumber);
		this.paymentMode.set(paymentMode.toString());
		this.name.set(customer.getName());
		this.address.set(customer.getAddress());
		this.mobileNumber.set(customer.getMobileNumber());
		setUpAmounts();
	}

	public Sale() {
		customer = new Customer();
		organization = Organization.getInstance();
		orderList = new ArrayList<>();
		setUpAmounts();
	}

	public void setId(UUID id) {
		this.id = id;
	}

	// getters and setters

	public Customer getCustomer() {
		return customer;
	}

	private void setUpAmounts(){
		sgst_amount.set((total_amount.getValue()*0.06f));
		cgst_amount.set(total_amount.getValue()*0.06f);
		igst_amount.set(total_amount.getValue()*0.12f);
		total_amount.set(total_amount.getValue()+igst_amount.getValue());
		//TODO setup amounts in sale and remove from order class

	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
		// set the properties to respective values
		this.name.set(customer.getName());
		this.address.set(customer.getAddress());
		this.mobileNumber.set(customer.getMobileNumber());
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public String getName() {
		return name.get();
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public LocalDate getDate() {
		return date.get();
	}

	public SimpleObjectProperty<LocalDate> dateProperty() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date.set(date);
	}

	public int getInvoiceNumber() {
		return invoiceNumber.get();
	}

	public SimpleIntegerProperty invoiceNumberProperty() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber.set(invoiceNumber);
	}

	public String getMobileNumber() {
		return mobileNumber.get();
	}

	public SimpleStringProperty mobileNumberProperty() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber.set(mobileNumber);
	}

	public String getAddress() {
		return address.get();
	}

	public SimpleStringProperty addressProperty() {
		return address;
	}

	public void setAddress(String address) {
		this.address.set(address);
	}

	public String getProducts() {
		return products.get();
	}

	public SimpleStringProperty productsProperty() {
		return products;
	}

	public void setProducts(String products) {
		this.products.set(products);
	}

	public String getPaymentMode() {
		return paymentMode.get();
	}

	public SimpleStringProperty paymentModeProperty() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode.set(paymentMode);
	}

	public UUID getId() {
		return id;
	}

	public UUID getCustomerID(){
		return customer.getId();
	}

	public float getSgst_amount() {
		return sgst_amount.get();
	}

	public SimpleFloatProperty sgst_amountProperty() {
		return sgst_amount;
	}

	public void setSgst_amount(float sgst_amount) {
		this.sgst_amount.set(sgst_amount);
	}

	public float getCgst_amount() {
		return cgst_amount.get();
	}

	public SimpleFloatProperty cgst_amountProperty() {
		return cgst_amount;
	}

	public void setCgst_amount(float cgst_amount) {
		this.cgst_amount.set(cgst_amount);
	}

	public float getIgst_amount() {
		return igst_amount.get();
	}

	public SimpleFloatProperty igst_amountProperty() {
		return igst_amount;
	}

	public void setIgst_amount(float igst_amount) {
		this.igst_amount.set(igst_amount);
	}

	/**
	 * helper function which formats the products string passed to it
	 * Manufacturer :
	 * Model:
	 * IEMI nUMBER:
	 * RATE:
	 * and also sets these to order object which is then added to orderlist
	 * used to parse orderlist from database to list to be shown in table
	 * @param products
	 */
	public void setOrderList(String products){
		StringBuilder stringBuilder = new StringBuilder();

		// split the orders
		String[] lines = products.split("\n");

		List<Order> orders = new ArrayList<>();
		for (int i = 0; i < lines.length; i++) {
			Order order = new Order();
			// all the properties of a product
			String[] properties = lines[i].split("&");

			// set the manufacturer property
			order.setManufacturer(properties[0]);
			stringBuilder.append("Manufacturer: ").append(properties[0]).append("\n");

			// set the model property
			order.setModel(properties[1]);
			stringBuilder.append("Model: ").append(properties[1]).append("\n");

			// set the imei number
			order.setImeiNumber(properties[2]);
			stringBuilder.append("IMEI Number: ").append(properties[2]).append("\n");

			// set the rate
			order.setRate(Float.parseFloat(properties[3]));
			stringBuilder.append("Rate: ").append(properties[3]).append("\n\n\n");

			// set the id of product
			order.getP().setId(UUID.fromString(properties[4]));

			// set the amount to rate as there is only one quantity
			order.setAmount((Float.parseFloat(properties[3])));
			// update the total amount (adding all amounts of each order)
			total_amount.set(total_amount.getValue()+order.getAmount());
			orders.add(order);
		}
		this.orderList = orders;
		// set the products property to string of orders
		this.products.set(stringBuilder.toString());
	}

	public float getTotal_amount() {
		return total_amount.get();
	}

	public SimpleFloatProperty total_amountProperty() {
		return total_amount;
	}

	public void setTotal_amount(float total_amount) {
		this.total_amount.set(total_amount);
	}

	/**
	 * @return the orderlist as string
	 */
	public String getProductsListAsString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Order o :orderList){
			stringBuilder
					.append(o.getManufacturer()).append("&")
					.append(o.getModel()).append("&")
					.append(o.getImeiNumber()).append("&")
					.append(o.getRate()).append("&")
					.append(o.getP().getId()).append("\n");
		}
		return stringBuilder.toString();
	}

	// displays the object properties
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("ID ").append(id).append("\n")
				.append("Date ").append(date).append("\n")
				.append("INVOICE_NUMBER ").append(invoiceNumber).append("\n")
				.append("CUSTOMER_ID ").append(customer.getId()).append("\n")
				.append("Name ").append(customer.getName()).append("\n")
				.append("Address ").append(customer.getAddress()).append("\n")
				.append("Products == ").append("\n");
		for (Order o : orderList){
			stringBuilder
					.append("Product Manufac ").append(o.getManufacturer()).append("\n")
					.append("Product model ").append(o.getModel()).append("\n")
					.append("Product IEMI ").append(o.getImeiNumber()).append("\n")
					.append("Product Rate ").append(o.getRate()).append("\n")
					.append("Product ID").append(o.getP().getId()).append("\n");
		}
		stringBuilder.append("Payment Mode ").append(paymentMode).append("\n");
		return stringBuilder.toString();
	}
}
