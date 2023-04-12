package view;

import javafx.application.Platform;
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
	private Label lfourmi,lgraines,lite;
	private Fourmiliere antcolony;
	private PlayPauseButton playpause;
	
	public ViewAntColony(Fourmiliere fm) {
		// TODO Auto-generated constructor stub
		this.antcolony = fm;
		initComponent();
		
	}
	
	public void initComponent() {
		plateau = new Board(antcolony);	
		this.setCenter(plateau);
		
		bottomBox = new HBox(10);
		    playpause = new PlayPauseButton(20,antcolony,plateau);
			loupe = new Button("Loupe");
			loupe.setOnAction(e->{
				ZoomWindow zm = new ZoomWindow(plateau);
			});
			quit = new Button("Quit");
			quit.setOnAction(e -> {
				Platform.exit();
			});
		
		bottomBox.getChildren().addAll(playpause,loupe,quit);
		this.setBottom(bottomBox);
		
		rightBox = new VBox(10);
			sld = new Slider(1,100, 5);
			sld.setShowTickLabels(true);
			sld.setShowTickMarks(true);
			lfourmi = new Label("Nombre de Fourmi");
			lgraines = new Label("nombre de graines");
			lite = new Label("Nombre d'iterations");
		
		rightBox.getChildren().addAll(sld,lfourmi,lgraines,lite);
		this.setRight(rightBox);
		
		
		playpause.valueSpeedProperty().bind(sld.valueProperty());
		
		
		
	}


}
