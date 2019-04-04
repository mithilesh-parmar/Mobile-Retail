package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import datamodel.Order;
import datamodel.Product;
import datamodel.Repository;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
	public ListView<Product> itemsList;
	public TableView<Order> invoiceTable;
	private Repository repository = Repository.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		repository.loadProducts();
		invoiceTable.setItems(repository.getOrdersList());
		itemsList.setItems(repository.getProductList());
	}

	public void onItemsListClicked(MouseEvent mouseEvent) {
		if (mouseEvent.getClickCount() == 2 ){
			addOrderToInvoice();
		}
	}

	public void onItemsListPressed(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER){
			addOrderToInvoice();
		}
	}

	private void addOrderToInvoice(){
		System.out.println("Selected Product "+itemsList.getSelectionModel().getSelectedItem());
		repository.addOrder(new Order(itemsList.getSelectionModel().getSelectedItem()));
	}
}
