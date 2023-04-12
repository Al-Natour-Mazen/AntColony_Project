package view;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import model.Fourmiliere;

public class ViewAntColony extends BorderPane{
	
	private Board plateau;
	private Button play,pause,loupe,quit;
	private HBox bottomBox;
	private VBox rightBox;
	private Slider sld;
	private Label lfourmi,lgraines,lite;
	private Fourmiliere antcolony;
	private Rectangle cellulePointee;
	
	public ViewAntColony(Fourmiliere fm) {
		// TODO Auto-generated constructor stub
		this.antcolony = fm;
		initComponent();
		
	}
	
	public void initComponent() {
		plateau = new Board(antcolony);	
		this.setCenter(plateau);
		
		bottomBox = new HBox(10);
			PlayPauseButton btn = new PlayPauseButton(20,antcolony,plateau);
			loupe = new Button("Loupe");
			quit = new Button("Quit");
			quit.setOnAction(e -> {
				Platform.exit();
			});
		
		bottomBox.getChildren().addAll(btn,loupe,quit);
		this.setBottom(bottomBox);
		
		rightBox = new VBox(10);
			sld = new Slider(1,10, 5);
			lfourmi = new Label("Nombre de Fourmi");
			lgraines = new Label("nombre de graines");
			lite = new Label("Nombre d'iterations");
		
		rightBox.getChildren().addAll(sld,lfourmi,lgraines,lite);
		this.setRight(rightBox);
		
		
		btn.valueSpeedProperty().bind(sld.valueProperty());
		
		
		
	}


}
