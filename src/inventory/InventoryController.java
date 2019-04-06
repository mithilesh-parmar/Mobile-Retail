package inventory;

import datamodel.Product;
import datamodel.Repository;
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
	private Repository repository = Repository.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		repository.loadProducts();
		inventoryTable.setItems(repository.getProductList());
		addButton.setOnAction(event -> addProductToDatabase());
	}

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

	public void removeProductFromDatabase(){
		repository.removeProductFromDatabase(inventoryTable.getSelectionModel().getSelectedItem());
	}

	private boolean isTextPresent(TextField textField){
		return textField.getText().length() > 0;
	}

	private String getText(TextField textField){
		return textField.getText();
	}

	private void clearTextFields(){
		manufacturerTextField.clear();
		modelTextField.clear();
		rateTextField.clear();
		iemiNumberTextField.clear();
	}


	public void onInventoryTableKeyPressed(KeyEvent keyEvent) {
		KeyCode keyCode = keyEvent.getCode();
		if (keyCode == KeyCode.BACK_SPACE){
			repository.removeProductFromInventroy(inventoryTable.getSelectionModel().getSelectedItem());
		}
	}
}
