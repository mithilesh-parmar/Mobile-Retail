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
	private SimpleStringProperty imeiNumber = new SimpleStringProperty("");
	private SimpleStringProperty description = new SimpleStringProperty("");
//	private SimpleFloatProperty total_amount = new SimpleFloatProperty(0f);
//	private SimpleFloatProperty sgst_amount = new SimpleFloatProperty(0f);
//	private SimpleFloatProperty cgst_amount = new SimpleFloatProperty(0f);
//	private SimpleFloatProperty igst_amount = new SimpleFloatProperty(0f);


	private Product p; // extract the values of the product model

	public Order(Product p){
		this.p = p;
		this.manufacturer.set(p.getManufacturer());
		this.rate.set(p.getRate());
		this.model.set(p.getModel());
		this.imeiNumber.set(p.getImeiNumber());
		// set the amount to rate * quantity
		//TODO individual gst or as a combined gst
		this.amount.set(this.rate.getValue() * this.quantity.getValue());
		this.description.set(getDescription());
	}

	public Order() {
		p = new Product();
	}




	public void setDescription(String description) {
		this.description.set(description);
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

	public SimpleIntegerProperty quantityProperty() {
		return quantity;
	}

	public float getRate() { return rate.get(); }

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

	public String getImeiNumber() {
		return imeiNumber.get();
	}

	public SimpleStringProperty imeiNumberProperty() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber.set(imeiNumber);
	}

	public int getQuantity(){
		return quantity.get();
	}

	public String getRateStringValue(){
		return String.valueOf(rate.get());
	}

	public String getDescription(){
		StringBuilder str = new StringBuilder();
		String[] numbers = imeiNumber.get().split(",");
		for (int i = 1; i <=numbers.length ; i++) {
			str.append("IMEI ").append(i).append(": ").append(numbers[i-1]).append("\n");
		}
		return manufacturer.get() + " " +
				model.get() + "\n" +
				"IMEI: "+imeiNumber.get() + "\n";
	}

	@Override
	public boolean equals(Object obj) {
		return p == ((Order)obj).p;
	}
}
