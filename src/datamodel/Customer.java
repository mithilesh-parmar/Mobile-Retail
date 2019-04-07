package datamodel;

import java.util.UUID;

public class Customer {
	// properties
	private UUID id;
	private String name;
	private String mobileNumber;
	private String address;

	public Customer(String name, String mobileNumber, String address) {
		id = UUID.randomUUID(); // set a random id
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.address = address;
	}

	public Customer() {

	}

	// getters and setters
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
