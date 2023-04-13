package view;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Fourmiliere;

public class ViewAntColony extends BorderPane{
	
	private Board plateau;

	private Button loupe,quit,init,reset;
	private HBox bottomBox;
	private VBox rightBox;
	private Slider vitesseSimulation;
	private Fourmiliere antcolony;
	private PlayPauseButton playpause;
	private ZoomWindow zoomedWindow;
	private LabelWithBind lfourmi,lgraines,lite;
	private int tutoZoomWindow = 0;
	
	private ObjectProperty<ZoomWindow> zoomWindowProperty;
	
	public ViewAntColony(Fourmiliere fm) {
		this.antcolony = fm;
		initComponent();
	}
	
	public void initComponent() {
		plateau = new Board(antcolony);	
		this.setCenter(plateau);
		BorderPane.setAlignment(plateau, Pos.CENTER);
		
		bottomBox = new HBox(10);
		    playpause = new PlayPauseButton(20,antcolony,plateau,zoomedWindow);
			
		    /*****************/
		    /* Le Btn Loupe  */
		    loupe = new Button("Loupe");
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
			        	Alert myPopUp = new Alert(AlertType.INFORMATION);
			        	myPopUp.setTitle("Tuto Zoom");
			        	myPopUp.setContentText(">> Si vous souhaitez afficher le plateau principal dans le \n plateau zoome, il suffit de survoler avec votre \n souris le plateau principal :)");
			        	myPopUp.showAndWait();
			        	tutoZoomWindow++;
			        }
			    }
			});
			
			
			
			
			
			init  = new Button("init");
			reset = new Button("Reset");
			quit = new Button("Quit");
			quit.setOnAction(e -> {
				Platform.exit();
			});
		
		bottomBox.getChildren().addAll(playpause,loupe,init,reset,quit);
		this.setBottom(bottomBox);
		
		rightBox = new VBox(10);
			vitesseSimulation = new Slider(1,10, 1);
			vitesseSimulation.setShowTickLabels(true);
			vitesseSimulation.setShowTickMarks(true);
			lfourmi = new LabelWithBind("Nombre de Fourmi :",antcolony.NbFourmiProperty());
			//on met à jour le nombre de graine pour le label
			//antcolony.MAJNbGrainesTotal();
			lgraines = new LabelWithBind("Nombre de graines :",antcolony.NbGraineProperty());	
			lite = new LabelWithBind("Nombre d'iterations :",antcolony.IterationProperty());
		
		rightBox.getChildren().addAll(vitesseSimulation,lfourmi,lgraines,lite);
		this.setRight(rightBox);
		
		
		playpause.valueSpeedProperty().bind(vitesseSimulation.valueProperty());
		//System.out.println(antcolony.NbFourmiProperty().equals(antcolony.NbGraineProperty())); // Affiche "false" si les propriétés sont différentes
		setPadding(new Insets(20));
		
	}

	public Button getInit() {
		return init;
	}
	public Board getPlateau() {
		return plateau;
	}
	public Button getReset() {
		return reset;
	}


}
