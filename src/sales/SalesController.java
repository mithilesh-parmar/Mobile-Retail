package sales;

import datamodel.Repository;
import datamodel.Sale;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ResourceBundle;

public class SalesController implements Initializable {
	public TableView<Sale> salesTable;
	private Repository repository = Repository.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		salesTable.setItems(repository.getSalesList());
		// TODO sales items are being nullified check repository
	}
}
