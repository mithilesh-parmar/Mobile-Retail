package homepage;

import datamodel.PaymentMode;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import datamodel.Order;
import datamodel.Product;
import datamodel.Repository;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import utils.Animations;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		repository.loadProducts(); // locad products from database
		invoiceTable.setItems(repository.getOrdersList()); // set the items for tableview
		itemsList.setItems(repository.getProductList()); // set the items for listview (loaded from database)

		dateLabel.setText(String.valueOf(LocalDate.now())); // set current system date
		gstLabel.setText(String.valueOf(repository.getGSTPercentage()) + "%"); // set gst percentage
		totalLabel.textProperty().bind(repository.totalAmountProperty().asString()); // bind the totalAmount property
		totalPayableLabel.textProperty().bind(repository.totalPayableAmountProperty().asString()); // set the totalpayableAmount proprty
		paymentModeComboBox.setItems(repository.getPaymentModes());//set the available payment methods
		paymentModeComboBox.getSelectionModel().select(0); // select the cash as default payment method
		// add search property to searchtextfield
		searchTextField.textProperty().addListener((observable, oldValue, newValue) -> performSearch(oldValue,newValue));

		printButton.setOnAction(event -> confirmOrder()); // add handler
		clearButton.setOnAction(event -> clearOrder()); // add handler

		homeButton.setOnAction(event -> changePageTo(mainPage));

		inventoryButton.setOnAction(event -> {
			try {
				changePageTo(FXMLLoader.load(getClass().getResource("/inventory/inventory.fxml")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		reportsButton.setOnAction(event -> changePageTo(null));
		exitButton.setOnAction(event -> closeProgram());


	}


	private void changePageTo(Node node){
		mainBorderPane.setCenter(node);
	}

	private void closeProgram() {

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

	if (isTextPresent(nameTextField) && isTextPresent(phoneNumberTextField) && isTextPresent(addressTextField) && isOrderPresent()){
		boolean result = showAlert();
		// TODO increase the invoice number
		if (result) clearOrder();
	}else {
		showErrorDialog();
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

		if (buttonType.get() == ButtonType.OK) return true;

		return false;
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
		}else if (keyCode == KeyCode.ADD || keyCode == KeyCode.PLUS){
			// increase the quantity by one
			repository.increaseTheQuantityByOne(invoiceTable.getSelectionModel().getSelectedItem());
		}else if (keyCode == KeyCode.MINUS || keyCode == KeyCode.SUBTRACT){
			// decrease the quantity by one
			Order order = invoiceTable.getSelectionModel().getSelectedItem();
			if (order.getQuantity() == 1){
				invoiceTable.getItems().remove(order);
			}
			repository.decreaseTheQuantityByOne(order);
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

}
