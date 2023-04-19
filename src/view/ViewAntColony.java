package view;


import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Fourmiliere;

/**
 * ViewAntColony est une classe qui représente la vue principale de l'application.
*/
public class ViewAntColony extends HBox{

	//les conteneurs
	private HBox left,bottomQuitBox;
	private VBox right;
	private TabPane interactionPane;
	private MyParamTab parametreTab;
	private MyInfoTab infoTab;
	private MyInitTab initTab;
		
	// Utilitaires
	private Board plateau;
	private Fourmiliere antcolony;
	
	//btn pour quitter
	private Button quit;
	
	/**
	 * Constructeur de la classe ViewAntColony.
	 * @param fm la fourmilière utilisée pour la simulation.
	*/
	public ViewAntColony(Fourmiliere fm) {
		this.antcolony = fm;
		initComponent();
	}

	/**
	 * Méthode qui initialise les différents composants de la vue et les organise dans les conteneurs appropriés.
	*/
	private void initComponent() {
		/////////////////////
		// La gauche
		/////////
		left = new HBox();
		plateau = new Board(antcolony);	
		left.setPadding(new Insets(20));
		left.getChildren().add(plateau);
		left.setAlignment(Pos.CENTER);
		
		/////////////////////
		// La droite
		/////////
		right = new VBox();
		interactionPane = new TabPane();
			
			/////////////////////
			// Les infos
			/////////
			infoTab = new MyInfoTab(antcolony,plateau);
				
			/////////////////////
			// Les Valeurs D'init Alea
			/////////
			initTab = new MyInitTab();
	
			/////////////////////
			// Les parametres
			/////////
			parametreTab = new MyParamTab(infoTab,antcolony);
			
			/////////////////////
			// Le Btn Quitter
			/////////
			bottomQuitBox = new HBox();
			
			quit = new Button("Quit");
			quit.setOnAction(e -> {
				Platform.exit();
			});
			quit.setStyle("-fx-background-color: transparent;-fx-border-color:black;");
			
			bottomQuitBox.setAlignment(Pos.BOTTOM_RIGHT);
			bottomQuitBox.getChildren().add(quit);
			bottomQuitBox.setPadding(new Insets(5));
			
		
		interactionPane.getTabs().addAll(infoTab,initTab,parametreTab);
		MySpring springright = new MySpring("VBox");
		right.getChildren().addAll(interactionPane,springright,bottomQuitBox);
		right.setMinWidth(300);
		
		/////////////////////
		// La HBox principale
		/////////
		MySpring spring = new MySpring("HBox");
		MySpring spring2 = new MySpring("HBox");
		this.getChildren().addAll(spring,left,spring2,right);		
	}

	//////////////////////
	// SETTERS/GETTERS BUTTONS
	//
	public Button getInit() {
		return infoTab.getInit();
	}
	public Button getReset() {
		return infoTab.getReset();
	}

	public Button getConfirmerParamCap() {
		return parametreTab.getConfirmerParamCap();
	}
	public Button getConfirmerParamTaille() {
		return parametreTab.getConfirmerParamTaille();
	}
	public Button getConfirmerInit() {
		return initTab.getConfirmerInit();
	}
	
	public PlayPauseButton getplaypause() {
		return infoTab.getPlaypause();
	}


	//////////////////////
	// SETTERS/GETTERS PLATEAU ET TABS
	//
	public Board getPlateau() {
		return plateau;
	}
	public void setPlateau(Board plateau) {
		left.getChildren().remove(this.plateau);
		this.plateau = plateau;
		left.getChildren().add(plateau);
	}

	public void setNewTabs(MyInfoTab newinfoTab,MyParamTab newparamTab) {
		interactionPane.getTabs().removeAll(this.infoTab,this.initTab,this.parametreTab);
		this.infoTab = newinfoTab;
		this.parametreTab = newparamTab;
		interactionPane.getTabs().addAll(newinfoTab,initTab,newparamTab);
	}
	
	//////////////////////
	// SETTERS/GETTERS LebelTextFields
	//
	public LabelTextField getChangeTaille() {
		return parametreTab.getChangeTaille();
	}

	public LabelTextField getChangecapacite() {
		return parametreTab.getChangecapacite();
	}
	public LabelTextField getProbaFourmi() {
		return initTab.getProbaFourmi();
	}
	public LabelTextField getProbagraines() {
		return initTab.getProbagraines();
	}
	public LabelTextField getProbamurs() {
		return initTab.getProbamurs();
	}

	
	//////////////////////
	// SETTERS/GETTERS Element d'interactions
	//
	public Fourmiliere getAntcolony() {
		return antcolony;
	}

	public void setAntcolony(Fourmiliere antcolony) {
		this.antcolony = antcolony;		
	}
	
}
