package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

/**
 * Classe representant un onglet d'initialisation pour une simulation de fourmis.
*/
public class MyInitTab extends Tab{
	
	//les conteneurs
	private VBox initBox;
	
	//elements d'interaction
	private Button confirmerInit;	

	//les Labels
	private LabelTextField probaFourmi,probagraines,probamurs;
	private Label infoInit;
	
	/**
	 * Constructeur par defaut de la classe MyInitTab.
	 * Il initialise l'onglet avec les differents elements necessaires.
	*/
	public MyInitTab() {
		super("Initialisations");
		setClosable(false);
	
		initBox = new VBox(20);
		setContent(initBox);
		
		//Un label d'information
		infoInit = new Label("Changez les valeurs d'initialisations de la Simulation :");
		
		//Les labels/Textefields pour les valeurs d'initialisations
		probaFourmi = new LabelTextField("Nombre de Fourmi :");
		probaFourmi.setTextFieldInput("8");
		
		probagraines = new LabelTextField("Densite des Graines :");
		probagraines.setTextFieldInput("110");
		
		probamurs = new LabelTextField("Densite des Murs :");
		probamurs.setTextFieldInput("140");
	
		//Le btn pour confirmer
		confirmerInit= new Button("Confirmer");
		SetStyleBtn(confirmerInit);
		
		MySpring springintiTop = new MySpring("VBox");
		MySpring springintiBottom = new MySpring("VBox");
		initBox.getChildren().addAll(springintiTop,infoInit,probaFourmi,probagraines,probamurs,confirmerInit,springintiBottom);
		initBox.setAlignment(Pos.CENTER);	
	}
	
	/**
	 * Methode permettant de definir le style des boutons de l'interface en leur affectant une couleur de fond transparente
	 * et une bordure noire.
	 * @param btn le bouton dont on souhaite définir le style
	*/
	private void SetStyleBtn(Button btn) {
		btn.setStyle("-fx-background-color: transparent;-fx-border-color:black;");
	}
	
	//////////////////////
	// SETTERS/GETTERS 
	//
	public Button getConfirmerInit() {
		return confirmerInit;
	}
	public LabelTextField getProbaFourmi() {
		return probaFourmi;
	}
	public LabelTextField getProbagraines() {
		return probagraines;
	}
	public LabelTextField getProbamurs() {
		return probamurs;
	}

}
