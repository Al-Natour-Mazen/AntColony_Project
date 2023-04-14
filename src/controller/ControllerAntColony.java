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
		    if(myCustomeAlerteConfirm("Init Game",
		    		"Voulez-vous vraiment initialiser le jeu ?",
		    		"Tout progr�s sera perdu !")) {
		       	resetGame();
				initAleatoire(10,50);
				viewantcolony.getPlateau().updateGrid();
				antcolony.MAJNbGrainesTotal();
		    }
		});
		
		viewantcolony.getReset().setOnAction(e -> {
		    if(myCustomeAlerteConfirm("Reset Game",
		    		"Voulez-vous vraiment r�initialiser le jeu ?",
		    		"Tout progr�s sera perdu !")) {
		    	resetGame();
		    }
		    
		});
		
	}
	
	public void initAleatoire(int nbFourmis, int nbGraines) {
	    // Place nbMurs murs al�atoirement
	    Random rand = new Random();
	    int nbMurs = rand.nextInt(antcolony.getHauteur()+antcolony.getLargeur()* rand.nextInt(randomMur)) / 2;
	    for (int i = 0; i < nbMurs; i++) {
	        int x = rand.nextInt(antcolony.getHauteur()) + 1;
	        int y = rand.nextInt(antcolony.getLargeur()) + 1;
	        antcolony.setMur(x, y, true);
	    }
	    
	    // Place jusqu'� nbFourmis fourmis al�atoirement
	    int nbFourmisPlaces = 0;
	    while (nbFourmisPlaces < nbFourmis) {
	    	int x = rand.nextInt(antcolony.getHauteur()) + 1;
		    int y = rand.nextInt(antcolony.getLargeur()) + 1;
	        if (!antcolony.contientFourmi(x, y) && !antcolony.getMur(x, y)) {
	            antcolony.ajouteFourmi(x, y);
	            nbFourmisPlaces++;
	        }
	    }
	    
	    // Place nbGraines graines al�atoirement
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


	public boolean myCustomeAlerteConfirm(String title,String HeaderText, String ContentText) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(HeaderText);
	    alert.setContentText(ContentText);

	    Optional<ButtonType> result = alert.showAndWait();
	    return result.isPresent() && result.get() == ButtonType.OK;
	}



}
