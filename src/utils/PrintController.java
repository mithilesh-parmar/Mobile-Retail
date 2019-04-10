package utils;

import datamodel.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PrintController implements Initializable {
	public Label organizationDetails;
	public Label customerDetails;
	public TableView<Order> invoiceTable;
	public Label policyLabel;
	public Label totalAmount;
	public Label gstTax;
	public Label payableAmount;
	public Label invoiceNumber;
	public Label date;
	public Label paymentMode;
	public TableColumn<String,String> columnProducts;
	public TableColumn columnRate;
	private Sale sale;
	private Repository repository = Repository.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Font font = new Font("monospaced",8);
		organizationDetails.setFont(font);
		customerDetails.setFont(font);
		policyLabel.setFont(font);
		totalAmount.setFont(font);
		payableAmount.setFont(font);
		gstTax.setFont(font);
		paymentMode.setFont(font);

		Organization org= Organization.getInstance();
		org.setName("Mobile 360");
		org.setProprietorName("Mithilesh Parmar");
		org.setAddress("Jecrc University, Sitapura, Jaipur");
		org.setContactNumber("8561057510");
		org.setGstNumber("12345678532");
		org.setPolicy("No return Policy");

		setOrganizationDetails();
		setCustomerDetails(new Customer("Mithilesh Parmar",
				"8561057510",
				"Gm Sales Cooperation, Raniwara Road, Sanchore"));

		invoiceNumber.setText("Invoice Number: "+String.valueOf(repository.getInvoiceNumberProperty()));
		date.setText("Dated: "+LocalDate.now().toString());
		paymentMode.setText("Mode: "+PaymentMode.CASH.toString());

		List<Order> orders = new ArrayList<>();
		orders.add(new Order(new Product("Samsung","s10","1290738972168122131",650000)));
		orders.add(new Order(new Product("Samsung","s10 Plus","1290232132e8122131",69000)));

		Sale sale = new Sale();
		sale.setOrderList(orders);


		payableAmount.setText("Payable Amount: "+repository.totalPayableAmountProperty().getValue());
		gstTax.setText("GST: "+String.valueOf(repository.getGSTPercentage()+"%"));
		policyLabel.setText("No return policy");

	}


	public void setSale(Sale s){
		this.sale = s;
		setOrganizationDetails();
		setCustomerDetails(sale.getCustomer());
		List<Order> sales = repository.getOrdersList();
		System.out.println(sales.size());
		invoiceTable.setItems(repository.getOrdersList());
		totalAmount.setText("Total: "+repository.totalAmountProperty().getValue());
		payableAmount.setText("Payable Amount: "+repository.totalPayableAmountProperty().getValue());
		gstTax.setText(String.valueOf(repository.getGSTPercentage()));

	}

	private void setOrganizationDetails() {
		Organization organization = Organization.getInstance();

		String str =
				organization.getName() + "\n" +
						organization.getProprietorName() + "\n" +
						organization.getAddress() + "\n" +
						organization.getContactNumber() + "\n";
		organizationDetails.setText(str);

	}

	private void setCustomerDetails(Customer customer){
		String stringBuilder =
				customer.getName() + "\n" +
				customer.getMobileNumber() + "\n" +
				customer.getAddress() + "\n";
		customerDetails.setText(stringBuilder);
	}

}
