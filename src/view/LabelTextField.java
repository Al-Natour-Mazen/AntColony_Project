package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LabelTextField extends HBox{
	
	private Label lab;
	private TextField tf;
	
	public LabelTextField(String text) {
		// TODO Auto-generated constructor stub
		super(15);
		lab = new Label(text);
		lab.setMinWidth(100);
		tf = new TextField();
		
		getChildren().addAll(lab,tf);
		setAlignment(Pos.CENTER);
	}
	
	public String getTextFieldInput() {
		return tf.getText();
	}

}
