package controller;

import java.util.Optional;
import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import model.Fourmiliere;
import view.Board;
import view.PlayPauseButton;
import view.ViewAntColony;
import view.ViewAntColony2;

public class ControllerAntColony {
	
	private Fourmiliere antcolony;
	private ViewAntColony viewantcolony;
	private final static int randomMur = 10;
	// private final DoubleProperty SizeProperty;  
	
	public ControllerAntColony(Fourmiliere colony, ViewAntColony vue) {
		this.antcolony = colony;
		this.viewantcolony = vue;
		
		viewantcolony.getInit().setOnAction(e ->{
		    if(myCustomeAlerteConfirm("Init Game",
		    		"Voulez-vous vraiment initialiser le jeu ?",
		    		"Tout progrès sera perdu !")) {
		       	resetGame();
				initAleatoire(10,50);
				viewantcolony.getPlateau().updateGrid();
				antcolony.MAJNbGrainesTotal();
		    }
		});
		
		viewantcolony.getReset().setOnAction(e -> {
		    if(myCustomeAlerteConfirm("Reset Game",
		    		"Voulez-vous vraiment réinitialiser le jeu ?",
		    		"Tout progrès sera perdu !")) {
		    	resetGame();
		    }
		    
		});
		/*ViewAntColony.getChnagerSize().setOnAction(e -> {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Enter a Number");
			dialog.setHeaderText("Please enter a number:");
			dialog.setContentText("Number:");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
			    try {
			        int number = Integer.parseInt(result.get());
			        Fourmiliere nvFormuliere = new Fourmiliere(number, number, number);
			        Board nvplateau = new Board(nvFormuliere);
			        PlayPauseButton nvbtn = new PlayPauseButton(viewantcolony.getPlaypause().getSize(), nvFormuliere, nvplateau, viewantcolony.getZoomedWindow());
			        viewantcolony.setPlateau(nvplateau);
			        
			        this.setAntcolony(nvFormuliere);
			        viewantcolony.setAntcolony(nvFormuliere);
			        
			        viewantcolony.setPlaypause(nvbtn, viewantcolony.getPlayPauseindex());
			        
			        viewantcolony.getPlateau().updateGrid();
			       
			        
			    } catch (NumberFormatException excption) {
			        // Gérer l'erreur si l'utilisateur a saisi autre chose qu'un nombre
			    }
			}

		});*/
			 
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


	public boolean myCustomeAlerteConfirm(String title,String HeaderText, String ContentText) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(HeaderText);
	    alert.setContentText(ContentText);

	    Optional<ButtonType> result = alert.showAndWait();
	    return result.isPresent() && result.get() == ButtonType.OK;
	}
	
	public Fourmiliere getAntcolony() {
		return antcolony;
	}

	public void setAntcolony(Fourmiliere antcolony) {
		this.antcolony = antcolony;
	}


	/*  public DoubleProperty IterationProperty() {
	      return IterationProperty;
	  }
	  public double getIteration() {
	      return IterationProperty.get();
	  }		
	  public void setIteration(double val) {
		  IterationProperty.set(val);
	  }	
});*/

}
