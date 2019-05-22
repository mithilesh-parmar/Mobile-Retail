package sales;

import datamodel.Order;
import datamodel.Product;
import datamodel.Repository;
import datamodel.Sale;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SalesController implements Initializable {
	public TableView<Sale> salesTable;
	private Repository repository = Repository.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		salesTable.setItems(repository.getSalesList());

	}

	public void handleOnKeyPressedSalesTable(KeyEvent keyEvent) {
		KeyCode keyCode = keyEvent.getCode();
		if (keyCode.equals(KeyCode.ENTER) && showAlert()){
			returnProducts();
		}
	}

	private boolean showAlert(){
		return true;
	}

	private void returnProducts(){
		Sale sale = salesTable.getSelectionModel().getSelectedItem();
		List<Order> orders= sale.getOrderList();

		List<Product> products = new ArrayList<>();
		for (Order o :orders){
			// return all products and add it to inventory
			products.add(new Product(o.getManufacturer(),o.getModel(),o.getImeiNumber(),o.getRate()));

		}
		for (Product p:products){
			repository.addProductToInventory(p);
		}

		repository.removeSaleFromDatabase(sale);
		salesTable.getItems().remove(sale);
	}
}
