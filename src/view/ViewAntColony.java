package view;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Fourmiliere;

public class ViewAntColony extends BorderPane{
	
	private Board plateau;
	private Button loupe,quit;
	private HBox bottomBox;
	private VBox rightBox;
	private Slider sld;
	private Label lgraines,lite;
	private Fourmiliere antcolony;
	private PlayPauseButton playpause;
	private ZoomWindow zoomedWindow;
	private LabelWithBind lfourmi;
	
	private ObjectProperty<ZoomWindow> zoomWindowProperty;
	
	public ViewAntColony(Fourmiliere fm) {
		this.antcolony = fm;
		initComponent();
	}
	
	public void initComponent() {
		plateau = new Board(antcolony);	
		this.setCenter(plateau);
		
		bottomBox = new HBox(10);
		    playpause = new PlayPauseButton(20,antcolony,plateau,zoomedWindow);
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
			        ZoomWindow zoomWindow = new ZoomWindow(plateau);
			        zoomWindowProperty.set(zoomWindow); // affecter la nouvelle fenêtre à la propriété
			    }
			});
			
			
			
			
			
			
			quit = new Button("Quit");
			quit.setOnAction(e -> {
				Platform.exit();
			});
		
		bottomBox.getChildren().addAll(playpause,loupe,quit);
		this.setBottom(bottomBox);
		
		rightBox = new VBox(10);
			sld = new Slider(1,10, 10);
			sld.setShowTickLabels(true);
			sld.setShowTickMarks(true);
			lfourmi = new LabelWithBind("Nombre de Fourmi :",antcolony.NbFourmiProperty());
			lgraines = new Label("nombre de graines");
			lite = new Label("Nombre d'iterations");
		
		rightBox.getChildren().addAll(sld,lfourmi,lgraines,lite);
		this.setRight(rightBox);
		
		
		playpause.valueSpeedProperty().bind(sld.valueProperty());
		
		
		
	}


}
