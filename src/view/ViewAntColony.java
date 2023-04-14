package view;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Fourmiliere;

public class ViewAntColony extends HBox{

	//les conteneurs
	private HBox left;
	private VBox right;
	private TabPane interactionPane;
	private MyParamTab parametreTab;
	private MyInfoTab infoTab;
	private MyInitTab initTab;
		
	// Utilitaires
	private Board plateau;
	private Fourmiliere antcolony;
	
	
	public ViewAntColony(Fourmiliere fm) {
		// TODO Auto-generated constructor stub
		this.antcolony = fm;
		initComponent();
	}


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
			
		
		interactionPane.getTabs().addAll(infoTab,initTab,parametreTab);
		right.getChildren().add(interactionPane);
		right.setMinWidth(300);
		
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

	public PlayPauseButton getPlaypause() {
		return infoTab.getPlaypause();
	}
	public void setPlaypause(PlayPauseButton newplaypause) {
		infoTab.setPlaypause(newplaypause);  
	}

	//////////////////////
	// SETTERS/GETTERS BUTTONS
	//
	public Board getPlateau() {
		return plateau;
	}
	public void setPlateau(Board plateau) {
		left.getChildren().remove(this.plateau);
		this.plateau = plateau;
		left.getChildren().add(plateau);
	}
	
	
	public MyInfoTab getInfoTab() {
		return infoTab;
	}


	public void setInfoTab(MyInfoTab newinfoTab) {
		interactionPane.getTabs().removeAll(this.infoTab,initTab,parametreTab);
		this.infoTab = newinfoTab;
		interactionPane.getTabs().addAll(newinfoTab,initTab,parametreTab);
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
	
	public ZoomWindow getZoomedWindow() {
		return infoTab.getZoomedWindow();
	}

	public void setZoomedWindow(ZoomWindow zoomedWindow) {
		infoTab.setZoomedWindow(zoomedWindow);;
	}


	
}
