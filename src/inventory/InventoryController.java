package inventory;

import datamodel.Product;
import datamodel.Repository;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

	public TableView<Product> inventoryTable;
	public TextField manufacturerTextField;
	public TextField modelTextField;
	public TextField rateTextField;
	public Button addButton;
	public TextField iemiNumberTextField;
	public TableColumn columnManufacturer;
	public TableColumn columnModel;
	public TableColumn columnIEMINumber;
	public TableColumn columnRate;
	private Repository repository = Repository.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inventoryTable.setItems(repository.getProductList()); // show all available products in table
		addButton.setOnAction(event -> addProductToDatabase()); // add event handler
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
				isTextPresent(iemiNumberTextField)){
			repository.addProductToInventory(new Product(
					getText(manufacturerTextField),
					getText(modelTextField),
					getText(iemiNumberTextField),
					Float.parseFloat(getText(rateTextField))
			));
			clearTextFields();
		}
	}

	/**
	 * remove product from database
	 */
	public void removeProductFromDatabase(){
		repository.removeProductFromDatabase(inventoryTable.getSelectionModel().getSelectedItem());
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
		iemiNumberTextField.clear();
	}


	/**
	 * on pressing backspace remove product from database
	 * @param keyEvent
	 */
	public void onInventoryTableKeyPressed(KeyEvent keyEvent) {
		KeyCode keyCode = keyEvent.getCode();
		if (keyCode == KeyCode.BACK_SPACE){
			repository.removeProductFromInventroy(inventoryTable.getSelectionModel().getSelectedItem());
		}
	}
}
