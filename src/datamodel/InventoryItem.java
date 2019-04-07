package datamodel;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InventoryItem {

	private Product product;
	private SimpleStringProperty manufacturer;
	private SimpleStringProperty model;
	private SimpleStringProperty imeiNumber;
	private SimpleFloatProperty rate;
	private SimpleIntegerProperty quantity;

	public InventoryItem(Product product,int quantity) {
		this.product = product;
		manufacturer = new SimpleStringProperty(product.getManufacturer());
		model = new SimpleStringProperty(product.getModel());
		imeiNumber = new SimpleStringProperty(product.getImeiNumber());
		rate = new SimpleFloatProperty(product.getRate());
		this.quantity = new SimpleIntegerProperty(quantity);
	}
	public InventoryItem(){
		manufacturer = new SimpleStringProperty();
		model = new SimpleStringProperty();
		imeiNumber = new SimpleStringProperty();
		rate = new SimpleFloatProperty();
		this.quantity = new SimpleIntegerProperty();
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getManufacturer() {
		return manufacturer.get();
	}

	public SimpleStringProperty manufacturerProperty() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer.set(manufacturer);
	}

	public String getModel() {
		return model.get();
	}

	public SimpleStringProperty modelProperty() {
		return model;
	}

	public void setModel(String model) {
		this.model.set(model);
	}

	public String getImeiNumber() {
		return imeiNumber.get();
	}

	public SimpleStringProperty imeiNumberProperty() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber.set(imeiNumber);
	}

	public float getRate() {
		return rate.get();
	}

	public SimpleFloatProperty rateProperty() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate.set(rate);
	}

	public int getQuantity() {
		return quantity.get();
	}

	public SimpleIntegerProperty quantityProperty() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity.set(quantity);
	}
}
