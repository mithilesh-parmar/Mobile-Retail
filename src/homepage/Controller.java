package homepage;

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

	public ListView<Product> itemsList; // products (Mobiles shown on left listview)
	public TableView<Order> invoiceTable; // center tableview acts as an invoice table
	private Repository repository = Repository.getInstance(); // repo singleton

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		repository.loadProducts(); // locad products from database
		invoiceTable.setItems(repository.getOrdersList()); // set the items for tableview
		itemsList.setItems(repository.getProductList()); // set the items for listview (loaded from database)
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
	 * @param keyEvent
	 */

	public void onItemsListPressed(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER){
			addOrderToInvoice();
		}
	}

	/**
	 * helper function for adding the product selected to tableview
	 * called on double click and enter pressed in listview
	 */

	private void addOrderToInvoice(){
		repository.addOrder(new Order(itemsList.getSelectionModel().getSelectedItem()));
	}
}
