package utils;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class IEMIListCell extends ListCell<String> {
	private HBox hBox= new HBox();
	private Label label = new Label();
	private TextField textField = new TextField();

	public IEMIListCell() {

		hBox.setSpacing(10);
		hBox.getChildren().add(label);
		hBox.getChildren().add(textField);
		setGraphic(hBox);
	}

	@Override
	protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null){
			setText(null);
			setGraphic(null);
		}else {
			setGraphic(hBox);
			label.setText(item);
		}
	}

	public void setLabelText(String text){
		label.setText(text);
	}
}
