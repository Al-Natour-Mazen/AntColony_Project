package view;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Fourmiliere;

public class ViewAntColony extends BorderPane{
	
	private Board plateau;

	private Button loupe,quit,init,reset;
	private HBox bottomBox;
	private VBox rightBox;
	private Slider sld;
	private Fourmiliere antcolony;
	private PlayPauseButton playpause;
	private ZoomWindow zoomedWindow;
	private LabelWithBind lfourmi,lgraines,lite;
	
	private ObjectProperty<ZoomWindow> zoomWindowProperty;
	
	public ViewAntColony(Fourmiliere fm) {
		this.antcolony = fm;
		initComponent();
	}
	
	public void initComponent() {
		plateau = new Board(antcolony);	
		this.setCenter(plateau);
		BorderPane.setAlignment(plateau, Pos.CENTER);
		plateau.setPadding(new Insets(10));
		
		bottomBox = new HBox(10);
		    playpause = new PlayPauseButton(20,antcolony,plateau,zoomedWindow);
			
		    /*****************/
		    /* Le Btn Loupe  */
		    loupe = new Button("Loupe");
			zoomWindowProperty = new SimpleObjectProperty<>();
			zoomWindowProperty.addListener((observable, oldZoomWindow, newZoomWindow) -> {
			    if (newZoomWindow != null) {
			        loupe.setDisable(true); // d�sactiver le bouton si la fen�tre est ouverte
			        newZoomWindow.setOnCloseRequest(e -> {
			            loupe.setDisable(false); // r�activer le bouton lorsque la fen�tre se ferme
			            zoomWindowProperty.set(null); // r�initialiser la propri�t� � null
			        });
			    }
			});
			loupe.setOnAction(e -> {
			    if (zoomWindowProperty.get() == null) { // v�rifier si la propri�t� est � null
			        ZoomWindow zoomWindow = new ZoomWindow(plateau);
			        zoomWindowProperty.set(zoomWindow); // affecter la nouvelle fen�tre � la propri�t�
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
			sld = new Slider(1,10, 10);
			sld.setShowTickLabels(true);
			sld.setShowTickMarks(true);
			lfourmi = new LabelWithBind("Nombre de Fourmi :",antcolony.NbFourmiProperty());
			//on met � jour le nombre de graine pour le label
			antcolony.MAJNbGrainesTotal();
			lgraines = new LabelWithBind("Nombre de graines :",antcolony.NbGraineProperty());
			
			lite = new LabelWithBind("Nombre d'iterations :",antcolony.IterationProperty());
		
		rightBox.getChildren().addAll(sld,lfourmi,lgraines,lite);
		this.setRight(rightBox);
		
		
		playpause.valueSpeedProperty().bind(sld.valueProperty());
		//System.out.println(antcolony.NbFourmiProperty().equals(antcolony.NbGraineProperty())); // Affiche "false" si les propri�t�s sont diff�rentes
		
		
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
