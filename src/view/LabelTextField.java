package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Cette classe represente un element d'interface graphique compose d'un label et d'un champ de texte pour la saisie de donnees.
*/
public class LabelTextField extends HBox{
	
	private Label lab;
	private TextField tf;

	/**
	 * Constructeur qui cree un element LabelTextField.
	 * @param text le texte du label associe au champ de texte
	*/
	public LabelTextField(String text) {
		// TODO Auto-generated constructor stub
		super(15);
		lab = new Label(text);
		lab.setMinWidth(120);
		tf = new TextField();
		
		getChildren().addAll(lab,tf);
		setAlignment(Pos.CENTER);
	}
	
	
	/**
	 * Methode qui renvoie le texte saisi dans le champ de texte associe a l'element LabelTextField.
	 * @return le texte saisi dans le champ de texte associe à l'element LabelTextField
	*/
	public String getTextFieldInput() {
		return tf.getText();
	}
	
	/**
	 * Methode qui definit le texte affiche dans le champ de texte associe a l'element LabelTextField.
	 * @param text le texte a afficher dans le champ de texte associé a l'element LabelTextField
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
