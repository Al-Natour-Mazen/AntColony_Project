package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import model.Fourmiliere;

/**
 * Cette classe représente l'onglet de paramètres de la simulation de la fourmilière.
 * Elle contient les éléments d'interaction permettant de modifier les paramètres de la simulation tels que la taille du plateau
 * ou la capacité maximale des graines dans la fourmilière.
*/
public class MyParamTab extends Tab{
	
	//les conteneurs
	private VBox paramBox;
	
	//elements d'interaction
	private Button confirmerParamCap, confirmerParamTaille;	
	private LabelTextField changeTaille,changecapacite;
	private Label infoparam;
	
	/**
	 * Constructeur de la classe MyParamTab.
	 * @param infotab l'onglet d'informations de la simulation
	 * @param antcol la fourmilière à afin d'afficher la quantité max des graines par case
	*/
	public MyParamTab(MyInfoTab infotab, Fourmiliere antcol) {
		super("Parametres");
		setClosable(false);
		//on ne peut pas changer les parametres si on est en pleine simulation
		disableProperty().bind(infotab.getPlaypause().isPlayingProperty());
	
		paramBox = new VBox(20);
		setContent(paramBox);
		
		
		infoparam = new Label("Changez les parametres de la Simulation :");
		
		changeTaille = new LabelTextField("Taille du plateau :");
		confirmerParamTaille = new Button("Confirmer");
		SetStyleBtn(confirmerParamTaille);
		changeTaille.getTextField().setMaxWidth(40);
		changeTaille.getChildren().add(confirmerParamTaille);
		
		changecapacite = new LabelTextField("Capacite max graines :");
		changecapacite.setTextFieldInput(String.valueOf(antcol.getQMax()));
		confirmerParamCap = new Button("Confirmer");
		SetStyleBtn(confirmerParamCap);
		changecapacite.getTextField().setMaxWidth(40);
		changecapacite.getChildren().add(confirmerParamCap);
		
		MySpring springparamTop = new MySpring("VBox");
		MySpring springparamBottom = new MySpring("VBox");
		paramBox.getChildren().addAll(springparamTop,infoparam,changeTaille,changecapacite,springparamBottom);
		paramBox.setAlignment(Pos.CENTER);
	}
	
	/**
	 * Méthode permettant de définir le style des boutons de l'interface en leur affectant une couleur de fond transparente
	 * et une bordure noire.
	 * @param btn le bouton dont on souhaite définir le style
	*/
	private void SetStyleBtn(Button btn) {
		btn.setStyle("-fx-background-color: transparent;-fx-border-color:black;");
	}
	
	//////////////////////
	// SETTERS/GETTERS 
	//
	public Button getConfirmerParamCap() {
		return confirmerParamCap;
	}
	public Button getConfirmerParamTaille() {
		return confirmerParamTaille;
	}
	public LabelTextField getChangeTaille() {
		return changeTaille;
	}
	public LabelTextField getChangecapacite() {
		return changecapacite;
	}

}
