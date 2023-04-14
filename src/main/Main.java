package main;


import controller.ControllerAntColony;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Fourmiliere;
import view.ViewAntColony;
import view.ViewAntColony2;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       
    	// Cr�ation du mod�le
        Fourmiliere fourmiliere = new Fourmiliere(20, 20, 10);
     	
        // Cr�ation de la vue
        ViewAntColony vue = new ViewAntColony(fourmiliere);
        
        // Cr�ation du contr�leur
        @SuppressWarnings("unused")
		ControllerAntColony controleur = new ControllerAntColony(fourmiliere, vue);
               
        // Configuration de la sc�ne
        Scene scene = new Scene(vue);
        
        // Configuration de la fen�tre principale
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fourmili�re");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

