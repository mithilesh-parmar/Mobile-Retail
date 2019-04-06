package datamodel;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {

	// property values
	private SimpleStringProperty manufacturer = new SimpleStringProperty("");
	private SimpleIntegerProperty quantity = new SimpleIntegerProperty(1);
	private SimpleFloatProperty rate = new SimpleFloatProperty(0f);
	private SimpleFloatProperty amount = new SimpleFloatProperty(0);
	private SimpleStringProperty model = new SimpleStringProperty("");

	private Product p; // extract the values of the product model

	public Order(Product p){
		this.p = p;
		this.manufacturer.set(p.getManufacturer());
		this.rate.set(p.getRate());
		this.model.set(p.getModel());
		// set the amount to rate * quantity
		this.amount.set(this.rate.floatValue() * this.quantity.intValue());
	}

	public void setQuantity(int quantity){
		this.quantity.set(quantity);
		// update amount
		setAmount();
	}

	// getters and setters

	private void setAmount() {
		this.amount.set(p.getRate() * quantity.getValue());
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

	public int getQuantity() {
		return quantity.get();
	}

	public SimpleIntegerProperty quantityProperty() {
		return quantity;
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

	public float getAmount() {
		return amount.get();
	}

	public SimpleFloatProperty amountProperty() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount.set(amount);
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

	public Product getP() {
		return p;
	}

	public void setP(Product p) {
		this.p = p;
	}

	public void increaseQuantityByOne(){
		setQuantity(this.quantity.getValue() + 1);
	}

	public void decreaseQuantityByOne(){
		setQuantity(this.quantity.getValue() - 1);
	}

	@Override
	public boolean equals(Object obj) {
		return p == ((Order)obj).p;
	}
}
