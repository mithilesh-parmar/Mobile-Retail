package utils;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;



public class IemiInputLayout extends GridPane {
	private Label label;
	private TextField textField;

	public IemiInputLayout(){
		setHgap(10);
		setVgap(10);
		label = new Label();
		textField = new TextField();
		add(label,0,0);
		add(textField,1,0);

	}

	public void setLabelText(String text){
		label.setText(text);
	}

	public String getData(){
		return textField.getText();
	}
}
