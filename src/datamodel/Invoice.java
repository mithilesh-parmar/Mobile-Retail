package datamodel;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Invoice {

	private Customer customer;
	private Organization organization;
	private List<Order> orderList;
	private UUID id;
	private int invoiceNumber;
	private LocalDate date;
	private PaymentMode paymentMode;

	public Invoice(Customer customer, List<Order> orderList, int invoiceNumber, PaymentMode paymentMode) {
		id = UUID.randomUUID();
		date = LocalDate.now();
		this.customer = customer;
		this.organization = Organization.getInstance();
		this.orderList = orderList;
		this.invoiceNumber = invoiceNumber;
		this.paymentMode = paymentMode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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

	public int getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public UUID getId() {
		return id;
	}
}
