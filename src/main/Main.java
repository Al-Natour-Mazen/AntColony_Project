package main;


import controller.ControllerAntColony;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Fourmiliere;
import view.ViewAntColony;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       
    	// Cr�ation du mod�le
        Fourmiliere fourmiliere = new Fourmiliere(20, 20, 10);
     	
        // Cr�ation de la vue
        ViewAntColony vue = new ViewAntColony(fourmiliere);
        
        // Cr�ation du contr�leur
        ControllerAntColony controller = new ControllerAntColony(fourmiliere, vue);
               
        // Configuration de la sc�ne
        Scene scene = new Scene(vue);
        
        // Lier la taille de la sc�ne � la taille de la vue qui change 
        controller.SizeProperty().addListener((obs, oldVal, newVal) -> {
        	// Redimensionnement de la fen�tre en fonction de la nouvelle taille pr�f�r�e de la sc�ne
        	primaryStage.sizeToScene();
        	// Affichage de la fen�tre
        	primaryStage.show();
        });
   

        // Configuration de la fen�tre principale
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fourmili�re");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

