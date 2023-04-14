package view;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Fourmiliere;

public class ViewAntColony extends HBox{

	//les conteneurs
	private HBox left, bottominfoBox,btnsBox;
	private VBox right, infoBox,paramBox, initBox;
	private TabPane interactionPane;
	private Tab infoTab,parametreTab, initTab;
	private GridPane buttons;
	
	//elements d'interaction
	private Button loupe,quit,init,reset, confirmerParamCap, confirmerParamTaille  , confirmerInit;
	private Slider vitesseSimulation;
	private PlayPauseButton playpause;
	private ZoomWindow zoomedWindow;
	private LabelWithBind lfourmi,lgraines,lite,lvitesseSimu;
	private LabelTextField changeTaille,changecapacite,probaFourmi,probagraines,probamurs;
	private Label infoparam,infoInit;
	
	// Utilitaires
	private int tutoZoomWindow = 0;
	private final static int sizePlayBtn = 30;	
	private ObjectProperty<ZoomWindow> zoomWindowProperty;
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
			infoTab = new Tab("Informations");
			infoTab.setClosable(false);

			infoBox = new VBox(10);
			infoTab.setContent(infoBox);
			
			//les labels
			vitesseSimulation = new Slider(1,10, 1);
			vitesseSimulation.setShowTickLabels(true);
			vitesseSimulation.setShowTickMarks(true);
			vitesseSimulation.setMaxWidth(150);
			lvitesseSimu = new LabelWithBind("Vitesse de Simulation : x",vitesseSimulation.valueProperty());
			lfourmi = new LabelWithBind("Nombre de Fourmi :",antcolony.NbFourmiProperty());
			lgraines = new LabelWithBind("Nombre de graines :",antcolony.NbGraineProperty());	
			lite = new LabelWithBind("Nombre d'iterations :",antcolony.IterationProperty());
			
			
			// les btn d'interaction
			btnsBox = new HBox();
			buttons = new GridPane();
			buttons.setHgap(10); // espace horizontal entre les éléments
			buttons.setVgap(10); // espace vertical entre les éléments
			
			playpause = new PlayPauseButton(sizePlayBtn,antcolony,plateau,zoomedWindow);
			playpause.valueSpeedProperty().bind(vitesseSimulation.valueProperty());
			
		    loupe = new Button("Loupe");
		    loupe.setMinWidth(20);
		    SetStyleBtn(loupe);
				zoomWindowProperty = new SimpleObjectProperty<>();
				zoomWindowProperty.addListener((observable, oldZoomWindow, newZoomWindow) -> {
				    if (newZoomWindow != null) {
				        loupe.setDisable(true); // désactiver le bouton si la fenêtre est ouverte
				        newZoomWindow.setOnCloseRequest(e -> {
				            loupe.setDisable(false); // réactiver le bouton lorsque la fenêtre se ferme
				            zoomWindowProperty.set(null); // réinitialiser la propriété à null
				        });
				    }
				});
				loupe.setOnAction(e -> {
				    if (zoomWindowProperty.get() == null) { // vérifier si la propriété est à null
				        zoomedWindow = new ZoomWindow(plateau);
				        zoomWindowProperty.set(zoomedWindow); // affecter la nouvelle fenêtre à la propriété
				        if(tutoZoomWindow == 0) {
				        	@SuppressWarnings("unused")
							MyCustomAlert alert = new MyCustomAlert(AlertType.INFORMATION,"Tuto Zoom",null,">> Si vous souhaitez afficher le plateau principal dans le \n plateau zoome, il suffit de survoler avec votre \n souris le plateau principal :)");				     
				        	tutoZoomWindow++;
				        }
				    }
				});
			
			init  = new Button("init");
			init.setMinWidth(50);
			SetStyleBtn(init);
			reset = new Button("Reset");
			reset.setMinWidth(50);
			SetStyleBtn(reset);
	
			buttons.add(playpause, 0, 0); // ajouter playpause en haut à gauche
			buttons.add(loupe, 1, 0); // ajouter loupe en haut à droite
			buttons.add(init, 0, 1); // ajouter init en bas à gauche
			buttons.add(reset, 1, 1); // ajouter reset en bas à droite
			
			btnsBox.getChildren().add(buttons);
			btnsBox.setAlignment(Pos.CENTER);
			
			//le bas
			bottominfoBox = new HBox();
			quit = new Button("Quit");
			quit.setOnAction(e -> {
				Platform.exit();
			});
			SetStyleBtn(quit);
			bottominfoBox.setAlignment(Pos.BOTTOM_RIGHT);
			bottominfoBox.getChildren().add(quit);
			bottominfoBox.setPadding(new Insets(5));
			
			//ajouter à la VBOx
			MySpring springinfobox = new MySpring("VBox");
			infoBox.getChildren().addAll(springinfobox,lvitesseSimu,vitesseSimulation,lfourmi,lgraines,lite,btnsBox,bottominfoBox);
			infoBox.setAlignment(Pos.CENTER);
			
			/////////////////////
			// Les Valeurs D'init Alea
			/////////
			initTab = new Tab("Initialisations");
			initTab.setClosable(false);
		
		
			initBox = new VBox(20);
			initTab.setContent(initBox);
			
			
			infoInit = new Label("Changez les Valeurs Aléatoires de la Simulation :");
			
			probaFourmi = new LabelTextField("Nombre Fourmi :");
			probaFourmi.setTextFieldInput("7");
			probagraines = new LabelTextField("Densité des graines :");
			probagraines.setTextFieldInput("25");
			//probagraines.setSpacing(7);
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
			parametreTab.disableProperty().bind(playpause.isPlayingProperty());
		
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
		return init;
	}
	public Button getReset() {
		return reset;
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
		return playpause;
	}
	public void setPlaypause(PlayPauseButton newplaypause) {
		buttons.add(newplaypause, 0, 0); // ajouter playpause en haut à gauche
        playpause = newplaypause;  
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
		/*lfourmi.setThepropToBind(antcolony.NbFourmiProperty());
		lgraines.setThepropToBind(antcolony.NbGraineProperty());
		lite.setThepropToBind(antcolony.IterationProperty());	*/	
	}
	
	public ZoomWindow getZoomedWindow() {
		return zoomedWindow;
	}

	public void setZoomedWindow(ZoomWindow zoomedWindow) {
		this.zoomedWindow = zoomedWindow;
	}


	
}
