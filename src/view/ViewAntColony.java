package view;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Fourmiliere;

public class ViewAntColony extends HBox{

	//les conteneurs
	private HBox left;
	private VBox right,paramBox,initBox;
	private TabPane interactionPane;
	private Tab parametreTab, initTab;
	private MyInfoTab infoTab;
	


	//elements d'interaction
	private Button confirmerParamCap, confirmerParamTaille  , confirmerInit;	
	private LabelTextField changeTaille,changecapacite,probaFourmi,probagraines,probamurs;
	private Label infoparam,infoInit;
	
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
			initTab = new Tab("Initialisations");
			initTab.setClosable(false);
		
		
			initBox = new VBox(20);
			initTab.setContent(initBox);
			
			
			infoInit = new Label("Changez les Valeurs d'initialisations de la Simulation :");
			
			probaFourmi = new LabelTextField("Nombre Fourmi :");
			probaFourmi.setTextFieldInput("7");
			
			probagraines = new LabelTextField("Densité des graines :");
			probagraines.setTextFieldInput("25");
			
			probamurs = new LabelTextField("Densité des Murs :");
			probamurs.setTextFieldInput("90");
		
			confirmerInit= new Button("Confirmer");
			SetStyleBtn(confirmerInit);
			
			MySpring springintiTop = new MySpring("VBox");
			MySpring springintiBottom = new MySpring("VBox");
			initBox.getChildren().addAll(springintiTop,infoInit,probaFourmi,probagraines,probamurs,confirmerInit,springintiBottom);
			initBox.setAlignment(Pos.CENTER);
			

			/////////////////////
			// Les parametres
			/////////
			parametreTab = new Tab("Paramètres");
			parametreTab.setClosable(false);
			//on ne peut pas changez les parametres si on est en pleine simulation
			parametreTab.disableProperty().bind(infoTab.getPlaypause().isPlayingProperty());
		
			paramBox = new VBox(20);
			parametreTab.setContent(paramBox);
			
			
			infoparam = new Label("Changez les parametres de la Simulation :");
			
			changeTaille = new LabelTextField("Taille plateau :");
			confirmerParamTaille = new Button("Confirmer");
			SetStyleBtn(confirmerParamTaille);
			changeTaille.getTextField().setMaxWidth(40);
			changeTaille.getChildren().add(confirmerParamTaille);
			
			changecapacite = new LabelTextField("Capacite max graines :");
			changecapacite.setTextFieldInput(String.valueOf(antcolony.getQMax()));
			confirmerParamCap = new Button("Confirmer");
			SetStyleBtn(confirmerParamCap);
			changecapacite.getTextField().setMaxWidth(40);
			changecapacite.getChildren().add(confirmerParamCap);
			
			MySpring springparamTop = new MySpring("VBox");
			MySpring springparamBottom = new MySpring("VBox");
			paramBox.getChildren().addAll(springparamTop,infoparam,changeTaille,changecapacite,springparamBottom);
			paramBox.setAlignment(Pos.CENTER);

		
		interactionPane.getTabs().addAll(infoTab,initTab,parametreTab);
		right.getChildren().add(interactionPane);
		right.setMinWidth(300);
		
		MySpring spring = new MySpring("HBox");
		MySpring spring2 = new MySpring("HBox");
		this.getChildren().addAll(spring,left,spring2,right);

		
	}
	
	private void SetStyleBtn(Button btn) {
		btn.setStyle("-fx-background-color: transparent;-fx-border-color:black;");
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
		return confirmerParamCap;
	}
	public Button getConfirmerParamTaille() {
		return confirmerParamTaille;
	}
	public Button getConfirmerInit() {
		return confirmerInit;
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
		return changeTaille;
	}

	public LabelTextField getChangecapacite() {
		return changecapacite;
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
