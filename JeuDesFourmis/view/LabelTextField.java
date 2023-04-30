package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Cette classe représente un élément d'interface graphique composé d'un label et d'un champ de texte pour la saisie de données.
*/
public class LabelTextField extends HBox{
	
	private Label lab;
	private TextField tf;

	/**
	 * Constructeur qui crée un élément LabelTextField.
	 * @param text le texte du label associé au champ de texte
	*/
	public LabelTextField(String text) {
		// TODO Auto-generated constructor stub
		super(15);
		lab = new Label(text);
		lab.setMinWidth(150);
		tf = new TextField();
		
		getChildren().addAll(lab,tf);
		setAlignment(Pos.CENTER);
	}
	
	
	/**
	 * Méthode qui renvoie le texte saisi dans le champ de texte associé à l'élément LabelTextField.
	 * @return le texte saisi dans le champ de texte associé à l'élément LabelTextField
	*/
	public String getTextFieldInput() {
		return tf.getText();
	}
	
	/**
	 * Méthode qui définit le texte affiché dans le champ de texte associé à l'élément LabelTextField.
	 * @param text le texte à afficher dans le champ de texte associé à l'élément LabelTextField
	*/
	public void setTextFieldInput(String text) {
		tf.setText(text);
	}
	
	//////////////////////
	// SETTERS/GETTERS 
	//
	 public TextField getTextField() {
		return tf;
	}


}
