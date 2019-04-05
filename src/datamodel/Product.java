package datamodel;

import java.util.UUID;

public class Product {

	// properties
	private String manufacturer;
	private String model;
	private String imeiNumber;
	private UUID id;
	private float rate;

	public Product(String manufacturer, String model, String iemiNumber, float rate) {
		id = UUID.randomUUID(); // set a random id
		this.manufacturer = manufacturer;
		this.model = model;
		this.imeiNumber = iemiNumber;
		this.rate = rate;
	}

	public Product() {

	}

	// getters and setters

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getImeiNumber() {
		System.out.println("Returning iemi:  "+imeiNumber);
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return  manufacturer + " " + model + " \n" + imeiNumber ;
	}

	@Override
	public boolean equals(Object obj) {
		Product p = (Product) obj;
		System.out.println(p);
		return super.equals(obj);
	}


}
