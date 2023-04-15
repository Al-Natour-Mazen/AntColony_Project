package view;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Fourmiliere;

public class MyInfoTab extends Tab {

	    //les conteneurs
		private HBox bottominfoBox,btnsBox;
		private VBox infoBox;
		private GridPane buttonsGrid;
		
		//elements d'interaction
		private Button loupe,quit,init,reset;
		private Slider vitesseSimulation;
		private PlayPauseButton playpause;

		private ZoomWindow zoomedWindow;
		private LabelWithBind lfourmi,lgraines,lite,lvitesseSimu;
		
		// Utilitaires
		private int tutoZoomWindow = 0;
		private final static int sizePlayBtn = 30;	
		private ObjectProperty<ZoomWindow> zoomWindowProperty;
		private Board plateau;
		private Fourmiliere antcolony;
	
	
	
	public MyInfoTab(Fourmiliere antcol,Board plat) {
		super("Informations");
		/////////////////////
		// Les infos
		/////////
		this.antcolony =antcol;
		this.plateau = plat;
		
		setClosable(false);

		infoBox = new VBox(10);
		setContent(infoBox);
		
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
		buttonsGrid = new GridPane();
		buttonsGrid.setHgap(10); // espace horizontal entre les éléments
		buttonsGrid.setVgap(10); // espace vertical entre les éléments
		
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
		
		init  = new Button("Init");
		init.setMinWidth(50);
		SetStyleBtn(init);
		reset = new Button("Reset");
		reset.setMinWidth(50);
		SetStyleBtn(reset);

		buttonsGrid.add(playpause, 0, 0); // ajouter playpause en haut à gauche
		buttonsGrid.add(loupe, 1, 0); // ajouter loupe en haut à droite
		buttonsGrid.add(init, 0, 1); // ajouter init en bas à gauche
		buttonsGrid.add(reset, 1, 1); // ajouter reset en bas à droite
		
		btnsBox.getChildren().add(buttonsGrid);
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
		MySpring springinfobox2 = new MySpring("VBox");
		infoBox.getChildren().addAll(springinfobox,lvitesseSimu,vitesseSimulation,lfourmi,lgraines,lite,btnsBox,springinfobox2,bottominfoBox);
		infoBox.setAlignment(Pos.CENTER);
		VBox.setVgrow(infoBox, Priority.ALWAYS);
		
	}
	
	private void SetStyleBtn(Button btn) {
		btn.setStyle("-fx-background-color: transparent;-fx-border-color:black;");
	}
	public PlayPauseButton getPlaypause() {
		return playpause;
	}
	
	public void setPlaypause(PlayPauseButton newplaypause) {
		buttonsGrid.add(newplaypause, 0, 0); // ajouter playpause en haut à gauche
		this.playpause = newplaypause;
	}
	public ZoomWindow getZoomedWindow() {
		return zoomedWindow;
	}

	public void setZoomedWindow(ZoomWindow zoomedWindow) {
		this.zoomedWindow = zoomedWindow;
	}
	
	public Button getInit() {
		return init;
	}
	public Button getReset() {
		return reset;
	}

	
	
}
