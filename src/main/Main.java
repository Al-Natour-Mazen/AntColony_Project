package main;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Fourmiliere;
import view.Board;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Création du modèle
        //Fourmiliere fourmiliere = new Fourmiliere(20, 20, 10);
        Fourmiliere fourmiliere = new Fourmiliere(43,35,10);
		
       
        // On crÃ©e quelques murs
        for (int i =1; i <4; i++)
        	fourmiliere.setMur(i, 2*i, true);
        
        fourmiliere.ajouteFourmi(1, 1);
        fourmiliere.ajouteFourmi(2, 2);
        fourmiliere.ajouteFourmi(3, 3);
        // On pose des graines
        fourmiliere.setQteGraines(10, 10, 1);
        fourmiliere.setQteGraines(10, 11, 2);
        fourmiliere.setQteGraines(10, 12, 3);
        fourmiliere.setQteGraines(10, 13, 4);
        fourmiliere.setQteGraines(10, 14, 5);
        fourmiliere.setQteGraines(10, 15, 6);
        fourmiliere.setQteGraines(10, 16, 7);
        fourmiliere.setQteGraines(10, 17, 8);
        fourmiliere.setQteGraines(10, 18, 9);
        fourmiliere.setQteGraines(10, 19, 10);
   
        // Création de la vue et du contrôleur
       /* Vue vue = new Vue(fourmiliere);
        Controleur controleur = new Controleur(fourmiliere, vue);*/
        
        Board stg = new Board(fourmiliere);
        // Configuration de la scène
        Scene scene = new Scene(stg);
        
        // Configuration de la fenêtre principale
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fourmilière");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

