package datamodel;

public class Organization {
	private String name;
	private String address;
	private String proprietorName;
	private String contactNumber;
	private String gstNumber;
	private String policy;

	private static Organization instance ;

	public static Organization getInstance(){
		if (instance == null){
			synchronized (Organization.class){
				if (instance == null){
					instance = new Organization();
				}
			}
		}
		return instance;
	}



	private Organization(){

	}

	private Organization(String name, String address, String proprietorName, String contactNumber, String gstNumber, String policy) {
		this.name = name;
		this.address = address;
		this.proprietorName = proprietorName;
		this.contactNumber = contactNumber;
		this.gstNumber = gstNumber;
		this.policy = policy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProprietorName() {
		return proprietorName;
	}

	public void setProprietorName(String proprietorName) {
		this.proprietorName = proprietorName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}
}
