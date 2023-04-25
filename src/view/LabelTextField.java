package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Cette classe repr�sente un �l�ment d'interface graphique compos� d'un label et d'un champ de texte pour la saisie de donn�es.
*/
public class LabelTextField extends HBox{
	
	private Label lab;
	private TextField tf;

	/**
	 * Constructeur qui cr�e un �l�ment LabelTextField.
	 * @param text le texte du label associ� au champ de texte
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
	 * M�thode qui renvoie le texte saisi dans le champ de texte associ� � l'�l�ment LabelTextField.
	 * @return le texte saisi dans le champ de texte associ� � l'�l�ment LabelTextField
	*/
	public String getTextFieldInput() {
		return tf.getText();
	}
	
	/**
	 * M�thode qui d�finit le texte affich� dans le champ de texte associ� � l'�l�ment LabelTextField.
	 * @param text le texte � afficher dans le champ de texte associ� � l'�l�ment LabelTextField
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
