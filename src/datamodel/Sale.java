package datamodel;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.*;

public class Sale {

	/**
	 *			COLUMNS.ID.toString() + " varchar(255), "+
	 * 			COLUMNS.DATE.toString() + " DATE, "+
	 * 			COLUMNS.INVOICE_NUMBER.toString() + " int, "+
	 * 			COLUMNS.CUSTOMER_ID.toString() + " varchar(255), "+
	 * 			COLUMNS.NAME.toString() + " varchar(255), "+
	 * 			COLUMNS.MOBILE_NUMBER.toString() + " varchar(255), "+
	 * 			COLUMNS.ADDRESS.toString() + " varchar(255), "+
	 * 			COLUMNS.PRODUCTS.toString() + " varchar(255), "+
	 * 			COLUMNS.PAYMENT_MODE.toString() + " varchar(255) " +
	 * 			")";
	 */

	private SimpleStringProperty name = new SimpleStringProperty("");
	private	SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
	private SimpleIntegerProperty invoiceNumber = new SimpleIntegerProperty();
	private SimpleStringProperty mobileNumber = new SimpleStringProperty();
	private SimpleStringProperty address = new SimpleStringProperty();
	private SimpleStringProperty products = new SimpleStringProperty();
	private SimpleStringProperty paymentMode = new SimpleStringProperty();

	private Customer customer; // customer
	private Organization organization; // company
	private List<Order> orderList; // orders for the customer
	private UUID id; // id of invoice
//	private int invoiceNumber; // invoice number
//	private LocalDate date; // date of issue
//	private PaymentMode paymentMode; // payment mode for purchase

	public Sale(Customer customer, List<Order> orderList, int invoiceNumber ,PaymentMode paymentMode) {
		id = UUID.randomUUID(); // set a random id
		date.set(LocalDate.now()); // set the local date
		this.customer = customer;
		this.organization = Organization.getInstance();
		this.orderList = orderList;
		this.invoiceNumber.set(invoiceNumber);
		this.paymentMode.set(paymentMode.toString());
	}

	public Sale() {
		customer = new Customer();
		organization = Organization.getInstance();
		orderList = new ArrayList<>();
	}

	public void setId(UUID id) {
		this.id = id;
	}

	// getters and setters

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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

	public void setOrderList(String products){
		StringBuilder stringBuilder = new StringBuilder();

		String[] lines = products.split("\n");
		List<Order> orders = new ArrayList<>();
		for (int i = 0; i < lines.length; i++) {
			Order order = new Order();
			String[] properties = lines[i].split(" ");
			order.setManufacturer(properties[0]);
			stringBuilder.append("Manufacturer: ").append(properties[0]).append("\n");

			order.setModel(properties[1]);
			stringBuilder.append("Model: ").append(properties[1]).append("\n");

			order.setImeiNumber(properties[2]);
			stringBuilder.append("IMEI Number: ").append(properties[2]).append("\n");

			order.setRate(Float.parseFloat(properties[3]));
			stringBuilder.append("Rate: ").append(properties[3]).append("\n\n\n");

			order.getP().setId(UUID.fromString(properties[4]));
			orders.add(order);
		}
		this.orderList = orders;
		this.products.set(stringBuilder.toString());
	}

	public String getProductsListAsString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Order o :orderList){
			stringBuilder
					.append(o.getManufacturer()).append(" ")
					.append(o.getModel()).append(" ")
					.append(o.getImeiNumber()).append(" ")
					.append(o.getRate()).append(" ")
					.append(o.getP().getId()).append("\n");
		}
		return stringBuilder.toString();
	}

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
