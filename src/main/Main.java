package main;


import controller.ControllerAntColony;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        
        // on met un ecouteur sur la taille de la vue qui change via le controller pour changer la taille de la scène à un changement
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
        
        //Ajout d'un icon à la fentre 
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        primaryStage.getIcons().add(icon);

    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

