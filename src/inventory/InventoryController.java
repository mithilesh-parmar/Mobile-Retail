package inventory;

import datamodel.Product;
import datamodel.Repository;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

	public TableView<Product> inventoryTable;
	public TextField manufacturerTextField;
	public TextField modelTextField;
	public TextField rateTextField;
	public Button addButton;

	public TableColumn columnManufacturer;
	public TableColumn columnModel;
	public TableColumn columnIEMINumber;
	public TableColumn columnRate;

	public GridPane inventoryItemInputGridPane;
	public TextField iemiNumberTextField1;
	public TextField iemiNumberTextField2;
	public Label iemiLabel2;
	private Repository repository = Repository.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		iemiNumberTextField1.setTextFormatter(new TextFormatter<>(
				(change -> {
					change.setText(change.getText().toUpperCase());
					return change;
				})
		));

		iemiNumberTextField2.setTextFormatter(new TextFormatter<>(
				(change -> {
					change.setText(change.getText().toUpperCase());
					return change;
				})
		));

		manufacturerTextField.setTextFormatter(new TextFormatter<>(
				(change -> {
					change.setText(change.getText().toUpperCase());
					return change;
				})
		));

		modelTextField.setTextFormatter(new TextFormatter<>(
				(change -> {
					change.setText(change.getText().toUpperCase());
					return change;
				})
		));

		rateTextField.setTextFormatter(new TextFormatter<>(
				(change -> {
					change.setText(change.getText().toUpperCase());
					return change;
				})
		));


		inventoryTable.setItems(repository.getProductList()); // show all available products in table
		addButton.setOnAction(event -> addProductToDatabase()); // add event handler
		rateTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				rateTextField.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

		iemiNumberTextField2.setVisible(false);
		iemiLabel2.setVisible(false);

		iemiNumberTextField1.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length()>0 && !iemiNumberTextField2.isVisible()){
				iemiNumberTextField2.setVisible(true);
				iemiLabel2.setVisible(true);
			}
			else if (newValue.length() <=0 && iemiNumberTextField2.isVisible()){
				iemiNumberTextField2.setVisible(false);
				iemiLabel2.setVisible(true);
			}
		});

	}


	/**
	 * add product to database
	 * extract info from textfields
	 * then add it to database
	 */
	private void addProductToDatabase() {
		if (isTextPresent(manufacturerTextField) &&
				isTextPresent(modelTextField) &&
				isTextPresent(rateTextField) &&
				(isTextPresent(iemiNumberTextField1)||isTextPresent(iemiNumberTextField2))){
			String model = getText(modelTextField);
			model = model.replace(" ","");
			String iemiText;
			if (isTextPresent(iemiNumberTextField1) && isTextPresent(iemiNumberTextField2)){
				iemiText = iemiNumberTextField1.getText()+","+iemiNumberTextField2.getText();
			}else {
				iemiText = isTextPresent(iemiNumberTextField1)? iemiNumberTextField1.getText():iemiNumberTextField2.getText();
			}
			repository.addProductToInventory(new Product(getText(manufacturerTextField),
					model,
					iemiText,
					Float.parseFloat(getText(rateTextField))));
			clearTextFields();
		}
	}

	/**
	 * remove product from database
	 */
	public void removeProductFromDatabase(){
		repository.removeProductFromInventroy(inventoryTable.getSelectionModel().getSelectedItem());
	}

	/**
	 * helper function to check if text is present in textfield
	 * @param textField
	 * @return
	 */
	private boolean isTextPresent(TextField textField){
		return textField.getText().length() > 0;
	}

	/**
	 * helper function to get string from textfield
	 * @param textField
	 * @return
	 */
	private String getText(TextField textField){
		return textField.getText();
	}

	/**
	 * clear textfields
	 * called after order is confirmed
	 */
	private void clearTextFields(){
		manufacturerTextField.clear();
		modelTextField.clear();
		rateTextField.clear();
		if (isTextPresent(iemiNumberTextField1))iemiNumberTextField1.clear();
		if (isTextPresent(iemiNumberTextField2))iemiNumberTextField2.clear();
	}

	/**
	 * on pressing backspace remove product from database
	 * @param keyEvent
	 */
	public void onInventoryTableKeyPressed(KeyEvent keyEvent) {
		KeyCode keyCode = keyEvent.getCode();
		if (keyCode == KeyCode.BACK_SPACE){
			Product product = inventoryTable.getSelectionModel().getSelectedItem();
			if (showAlert(product));repository.removeProductFromInventroy(product);
		}
	}

	/**
	 * alert dialog to confirm deletion of inventory item
	 * @param selectedItem
	 * @return
	 */
	private boolean showAlert(Product selectedItem){
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		String str =
				"Manufacturer: " + selectedItem.getManufacturer() + "\n" +
				"Model: " + selectedItem.getModel() + "\n" +
				"IEMI NUMBER: " + selectedItem.getImeiNumber() + "\n" +
				"Rate: " + selectedItem.getRate() + "\n" ;
		alert.setContentText(str);
		alert.initOwner(inventoryTable.getScene().getWindow());


		Optional<ButtonType> response = alert.showAndWait();

		return response.get() == ButtonType.OK ;
	}

	public void onImeiTextFieldKeyPressed(KeyEvent keyEvent) {
		KeyCode keyCode = keyEvent.getCode();
		if (keyCode.equals(KeyCode.ENTER)){
			addProductToDatabase();
		}
	}
}
