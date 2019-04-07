package homepage;

import database.dao.SalesDao;
import datamodel.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import utils.Animations;
import utils.PrinterHelper;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

	public ListView<Product> itemsList; // products (Mobiles shown on left listview)
	public TableView<Order> invoiceTable; // center tableview acts as an invoice table
	public TextField searchTextField;
	public Button printButton;
	public Button clearButton;
	public TextField nameTextField;
	public TextField phoneNumberTextField;
	public TextField addressTextField;
	public ComboBox<datamodel.PaymentMode> paymentModeComboBox;
	public Label invoiceNumberLabel;
	public Label dateLabel;
	public Label totalLabel;
	public Label gstLabel;
	public Label totalPayableLabel;
	public Button homeButton;
	public Button inventoryButton;
	public Button reportsButton;
	public Button exitButton;
	public BorderPane mainBorderPane;
	public HBox mainPage;
	private Repository repository = Repository.getInstance(); // repo singleton instance
	// key combinations for shortcuts
	private final KeyCombination printKeyCombination = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
	private final KeyCombination clearKeyCombination = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);


	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		repository.loadProducts(); // load products from database
		invoiceTable.setItems(repository.getOrdersList()); // set the items for tableview
		itemsList.setItems(repository.getProductList()); // set the items for listview (loaded from database)
		itemsList.getSelectionModel().selectFirst();// set initial focus to product list

		invoiceNumberLabel.textProperty().bind(repository.invoiceNumberPropertyProperty().asString()); // set the invoice number

		dateLabel.setText(String.valueOf(LocalDate.now())); // set current system date
		gstLabel.setText(String.valueOf(repository.getGSTPercentage()) + "%"); // set gst percentage
		totalLabel.textProperty().bind(repository.totalAmountProperty().asString()); // bind the totalAmount property
		totalPayableLabel.textProperty().bind(repository.totalPayableAmountProperty().asString()); // set the totalpayableAmount proprty
		paymentModeComboBox.setItems(repository.getPaymentModes());//set the available payment methods
		paymentModeComboBox.getSelectionModel().select(0); // select the cash as default payment method
		// add search property to searchtextfield
		searchTextField.textProperty().addListener((observable, oldValue, newValue) -> performSearch(oldValue,newValue));

		// set eventhandlers
		printButton.setOnAction(event -> confirmOrder()); // add handler
		clearButton.setOnAction(event -> clearOrder()); // add handler

		// sidebar buttons

		homeButton.setOnAction(event -> {changePageTo(mainPage);});

		inventoryButton.setOnAction(event -> {
			try {
				// change center node to inventory window
				changePageTo(FXMLLoader.load(getClass().getResource("/inventory/inventory.fxml")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		// change center node to reports window
		reportsButton.setOnAction(event -> {
			try {
				changePageTo(FXMLLoader.load(getClass().getResource("/sales/sales.fxml")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		// close the program
		exitButton.setOnAction(event -> closeProgram());

		// set phone textfield to be numberic only
		phoneNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				phoneNumberTextField.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
	}

	/**
	 * change the center node of borderpane to provided node
	 * @param node
	 */
	private void changePageTo(Node node){
		mainBorderPane.setCenter(node);
	}

	/**
	 * close the program
	 */
	private void closeProgram() {
		repository.closeProgram();
		System.exit(0);
	}


	/**
	 * called for filtering the observable list to show only those products that
	 * matches the search text criteria  i.e. contains manufacturer or model name
	 * @param oldValue
	 * @param newValue
	 */
	private void performSearch(String oldValue, String newValue) {
		// if pressing backspace then set initial values to list
		if (oldValue != null && (newValue.length() < oldValue.length())){
			itemsList.setItems(repository.getProductList());
		}

		// convert the searched text to uppercase
		String searchtext = newValue.toUpperCase();

		ObservableList<Product> subList  = FXCollections.observableArrayList();
		for (Product p : itemsList.getItems()){
			String text = p.getManufacturer().toUpperCase() + " " + p.getModel().toUpperCase();
			// if the search text contains the manufacturer then add it to sublist
			if (text.contains(searchtext)){
				subList.add(p);
			}

		}
		// set the items to listview that matches
		itemsList.setItems(subList);
	}


	/**
	 * confirm the order called when print button is clicked
	 */
	private void confirmOrder() {

	if (isTextPresent(nameTextField) &&
			isTextPresent(phoneNumberTextField) &&
			isTextPresent(addressTextField) &&
			isOrderPresent()){

		if (showAlert()){
			// save sale to database
			addSaleToDatabase();
			// print the order
			PrinterHelper.print("Printing Bill");
			// clear the order
			clearOrder();
		}
	}else {
		showErrorDialog();
		if (!nameTextField.isFocused())nameTextField.requestFocus();
	}

	}

	/**
	 * add sale to database on confirming sale
	 */
	private void addSaleToDatabase() {

		Customer customer =new Customer();
		customer.setName( nameTextField.getText());
		customer.setMobileNumber(phoneNumberTextField.getText());
		customer.setAddress(addressTextField.getText());
		List<Order> orderList = new ArrayList<>(invoiceTable.getItems());
		repository.addSale(
				new Sale(
						customer,
						orderList,  // all the products in invoice table (order) in arraylist form
						repository.getInvoiceNumberProperty(), // latest invoice number from repository
						paymentModeComboBox.getSelectionModel().getSelectedItem() // payment mode
				)
		);

		// remove the products from inventory
		for (Order o :orderList){
			repository.removeProductFromInventroy(o.getP());
		}

	}

	/**
	 * show error dialog when all the customer details are not entered
	 */
	private void showErrorDialog() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initOwner(printButton.getScene().getWindow());
		alert.setContentText("All fields are mandatory");
		alert.show();
	}

	/**
	 * helper functions to check if there is text present in textfield
	 * @param textField
	 * @return
	 */

	private boolean isTextPresent(TextField textField){
		return textField.getText().length() > 0;
	}

	/**
	 * helper function to check if there are orders present in invoice table
	 * @return
	 */
	private boolean isOrderPresent(){
		return invoiceTable.getItems().size() > 0;
	}

	/**
	 * show alert to confirm order
	 * shows details of customer and the orders placed
	 * @return
	 */
	private boolean showAlert(){
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("Name: ").append(nameTextField.getText()).append("\n")
				.append("Phone Number: ").append(phoneNumberTextField.getText()).append("\n")
				.append("Address: ").append(addressTextField.getText()).append("\n")
				.append("Payment Mode: ").append(paymentModeComboBox.getSelectionModel().getSelectedItem()).append("\n\n")
				.append("Orders: ").append("\n");
		for (Order o:invoiceTable.getItems()){
			stringBuilder.append(o.getManufacturer()).append(" ").append(o.getModel()).append(" ").append(o.getQuantity()).append("\n");
		}
		alert.setContentText(stringBuilder.toString());
		alert.initOwner(printButton.getScene().getWindow());

		Optional<ButtonType> buttonType = alert.showAndWait();

		return buttonType.get() == ButtonType.OK;
	}

	/**
	 * clears the invoice table and the textfields
	 */
	private void clearOrder() {
		nameTextField.clear();
		phoneNumberTextField.clear();
		addressTextField.clear();
		paymentModeComboBox.getSelectionModel().select(0);
		repository.clearOrder();
		if (invoiceTable.getItems().size() > 0){
			invoiceTable.getItems().clear();
		}
	}

	/**
	 * called on click in listview
	 * on double click adds the product selected to tableview as an order
	 * @param mouseEvent
	 */
	public void onItemsListClicked(MouseEvent mouseEvent) {
		if (mouseEvent.getClickCount() == 2 ){
			addOrderToInvoice();
		}
	}

	/**
	 * same as above
	 * on enter pressed adds the product selected to tableview
	 * on backspace change focus to searchtext field
	 * @param keyEvent
	 */

	public void onItemsListPressed(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER){
			addOrderToInvoice();
		}else if (keyEvent.getCode() == KeyCode.BACK_SPACE){
			// if backspace is pressed and searchtext field is not focused then request focus
			if (!searchTextField.isFocused()){
				searchTextField.requestFocus(); // request focus
				searchTextField.clear(); // clear the text to restore list to show all products
			}
		}else if (printKeyCombination.match(keyEvent)){
			confirmOrder();
		}else if (clearKeyCombination.match(keyEvent)){
			clearOrder();
		}
	}

	/**
	 * helper function for adding the product selected to tableview
	 * called on double click and enter pressed in listview
	 */

	private void addOrderToInvoice(){
		repository.addOrder(new Order(itemsList.getSelectionModel().getSelectedItem()));
	}

	/**
	 * used to change focus to listview on pressing enter key
	 * @param keyEvent
	 */
	public void onSearchTextEntered(KeyEvent keyEvent) {
		// if the list view is not focused then request focus
		if (!itemsList.isFocused()
				&& keyEvent.getCode() == KeyCode.ENTER)
			itemsList.requestFocus();
	}

	/**
	 * on key pressed in tableview
	 * shift focus to listview on left key pressed
	 * remove order on backspace pressed
	 * @param keyEvent
	 */
	public void onTableKeyPressed(KeyEvent keyEvent) {
		KeyCode keyCode = keyEvent.getCode();
		if (keyCode == KeyCode.BACK_SPACE){
			// delete the order from invoice table
			repository.removeOrder(invoiceTable.getSelectionModel().getSelectedItem());
		}else if (keyCode == KeyCode.LEFT){
			// shift the focus to listview
			if (!itemsList.isFocused()) itemsList.requestFocus();
		}else if (printKeyCombination.match(keyEvent)){
			confirmOrder();
		}else if (clearKeyCombination.match(keyEvent)){
			clearOrder();
		}
	}

	/**
	 * handles the animation for sidebar
	 * @param mouseEvent
	 */
	public void handleOnMouseEntered(MouseEvent mouseEvent) {
		HBox view = (HBox)mouseEvent.getSource();
		Pane indicatorPane = (Pane) view.getChildren().get(0);
		indicatorPane.setStyle("-fx-background-color:derive(red,70%);");
		Animations.makeButtonOutAnimations(50,view);
	}

	/**
	 * handles the animation for sidebar
	 * @param mouseEvent
	 */
	public void handleOnMouseExited(MouseEvent mouseEvent) {
		HBox view = (HBox)mouseEvent.getSource();
		Pane indicatorPane = (Pane) view.getChildren().get(0);
		indicatorPane.setStyle("-fx-background-color:#2d3041;");
		Animations.makeButtonInAnimations(50,view);
	}

	/**
	 * add keyboard shortcuts for print and clear button to all textfields and labels
	 * @param keyEvent
	 */
	public void addAccelerators(KeyEvent keyEvent) {
		 if (printKeyCombination.match(keyEvent)){
			confirmOrder();
		}else if (clearKeyCombination.match(keyEvent)){
			clearOrder();
		}
	}

}
