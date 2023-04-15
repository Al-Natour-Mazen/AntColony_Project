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
       
    	// Création du modèle
        Fourmiliere fourmiliere = new Fourmiliere(20, 20, 10);
     	
        // Création de la vue
        ViewAntColony vue = new ViewAntColony(fourmiliere);
        
        // Création du contrôleur
        ControllerAntColony controller = new ControllerAntColony(fourmiliere, vue);
               
        // Configuration de la scène
        Scene scene = new Scene(vue);
        
        // Lier la taille de la scène à la taille de la vue qui change 
        controller.SizeProperty().addListener((obs, oldVal, newVal) -> {
        	// Redimensionnement de la fenêtre en fonction de la nouvelle taille préférée de la scène
        	primaryStage.sizeToScene();
        	// Affichage de la fenêtre
        	primaryStage.show();
        });
   

        // Configuration de la fenêtre principale
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fourmilière");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

