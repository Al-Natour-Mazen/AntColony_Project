package controller;

import java.util.Optional;
import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Fourmiliere;
import view.ViewAntColony;

public class ControllerAntColony {
	
	private Fourmiliere antcolony;
	private ViewAntColony viewantcolony;
	private final static int randomMur = 10;
	
	public ControllerAntColony(Fourmiliere colony, ViewAntColony vue) {
		// TODO Auto-generated constructor stub
		this.antcolony = colony;
		this.viewantcolony = vue;
		
		viewantcolony.getInit().setOnAction(e ->{
			resetGame();
			initAleatoire(10,50);
			viewantcolony.getPlateau().updateGrid();
			antcolony.MAJNbGrainesTotal();
		});
		
		viewantcolony.getReset().setOnAction(e -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		    alert.setTitle("Reset Game");
		    alert.setHeaderText("Voulez-vous vraiment réinitialiser le jeu ?");
		    alert.setContentText("Tout progrès sera perdu !");

		    Optional<ButtonType> result = alert.showAndWait();
		    if (result.isPresent() && result.get() == ButtonType.OK) {
		        resetGame();
		    }
		});
		
	}
	
	public void initAleatoire(int nbFourmis, int nbGraines) {
	    // Place nbMurs murs aléatoirement
	    Random rand = new Random();
	    int nbMurs = rand.nextInt(antcolony.getHauteur()+antcolony.getLargeur()* rand.nextInt(randomMur)) / 2;
	    for (int i = 0; i < nbMurs; i++) {
	        int x = rand.nextInt(antcolony.getHauteur()) + 1;
	        int y = rand.nextInt(antcolony.getLargeur()) + 1;
	        antcolony.setMur(x, y, true);
	    }
	    
	    // Place jusqu'à nbFourmis fourmis aléatoirement
	    int nbFourmisPlaces = 0;
	    while (nbFourmisPlaces < nbFourmis) {
	    	int x = rand.nextInt(antcolony.getHauteur()) + 1;
		    int y = rand.nextInt(antcolony.getLargeur()) + 1;
	        if (!antcolony.contientFourmi(x, y) && !antcolony.getMur(x, y)) {
	            antcolony.ajouteFourmi(x, y);
	            nbFourmisPlaces++;
	        }
	    }
	    
	    // Place nbGraines graines aléatoirement
	    for (int i = 0; i < nbGraines; i++) {
	    	int x = rand.nextInt(antcolony.getHauteur()) + 1;
		    int y = rand.nextInt(antcolony.getLargeur()) + 1;

	        if (!antcolony.getMur(x, y)) {
	            int qte = rand.nextInt(antcolony.getQMax() + 1);
	            antcolony.setQteGraines(x, y, qte);
	        }
	    }
	}
	
	public void resetGame() {
		antcolony.Reset();
		viewantcolony.getPlateau().resestGrid();
	}






}
