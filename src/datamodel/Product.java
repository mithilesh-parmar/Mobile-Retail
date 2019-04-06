package datamodel;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.UUID;

public class Product {

	// properties
	private SimpleStringProperty manufacturer = new SimpleStringProperty("");
	private SimpleStringProperty model = new SimpleStringProperty("");
	private SimpleStringProperty imeiNumber = new SimpleStringProperty("");
	private SimpleObjectProperty<UUID> id = new SimpleObjectProperty<>();
	private SimpleFloatProperty rate = new SimpleFloatProperty();

	public Product(String manufacturer, String model, String iemiNumber, float rate) {
		id.set(UUID.randomUUID()); // set a random id
		this.manufacturer.set(manufacturer);
		this.model.set(model);
		this.imeiNumber.set(iemiNumber);
		this.rate.set(rate);
	}

	public Product() {
		this.manufacturer = new SimpleStringProperty();
		this.model = new SimpleStringProperty();
		this.imeiNumber = new SimpleStringProperty();
		this.id = new SimpleObjectProperty<>();
		this.rate = new SimpleFloatProperty();
	}

	// getters and setters

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

	public UUID getId() {
		return id.get();
	}

	public SimpleObjectProperty<UUID> idProperty() {
		return id;
	}

	public void setId(UUID id) {
		this.id.set(id);
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

	@Override
	public String toString() {
		return  manufacturer.getValue() + " "
				+ model.getValue()  ;
	}


}
