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
       
    	// Creation du modele
        Fourmiliere fourmiliere = new Fourmiliere(20, 20, 10);
     	
        // Creation de la vue
        ViewAntColony vue = new ViewAntColony(fourmiliere);
        
        // Creation du controleur
        ControllerAntColony controller = new ControllerAntColony(fourmiliere, vue);
               
        // Configuration de la scene
        Scene scene = new Scene(vue);
        
        // on met un ecouteur sur la taille de la vue qui change via le controller pour changer la taille de la scene a un changement
        controller.SizeProperty().addListener((obs, oldVal, newVal) -> {
        	// Redimensionnement de la fenetre en fonction de la nouvelle taille preferee de la scene
        	primaryStage.sizeToScene();
        	// Affichage de la fenetre
        	primaryStage.show();
        });
        
        // si on ferme la fentre principale autrement qu'avec le bouton quitter, il faudra fermer la fenetre de zoom aussi
        primaryStage.setOnCloseRequest(e -> {
        	if(vue.getZoomedWindow() != null)
        		vue.getZoomedWindow().close();
        });
   
        // Configuration de la fentre principale
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fourmiliere");
        primaryStage.show();
        
        //Ajout d'un icon a la fentre 
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        primaryStage.getIcons().add(icon);

    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

