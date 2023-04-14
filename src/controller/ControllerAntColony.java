package controller;

import java.util.Optional;
import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import model.Fourmiliere;
import view.Board;
import view.PlayPauseButton;
import view.ViewAntColony;

public class ControllerAntColony {
	
	private Fourmiliere antcolony;
	private ViewAntColony viewantcolony;
	private final static int randomMur = 10;
	private int nbmurs , nbfourmi ,nbgraines;
	
	public ControllerAntColony(Fourmiliere colony, ViewAntColony vue) {
		this.antcolony = colony;
		this.viewantcolony = vue;
		
		viewantcolony.getInit().setOnAction(e ->{
		    if(myCustomeAlerteConfirm("Init Game",
		    		"Voulez-vous vraiment initialiser le jeu ?",
		    		"Tout progrès sera perdu !")) {
		       	resetGame();
		       	initAleatoire(nbmurs,nbfourmi,nbgraines);
				viewantcolony.getPlateau().updateGrid();
				antcolony.MAJNbGrainesTotal();
		    }
		});
		
		
		viewantcolony.getConfirmerInit().setOnAction(e -> {
			String fourmi = viewantcolony.getProbaFourmi().getTextFieldInput();
			String graines = viewantcolony.getProbagraines().getTextFieldInput();
			String murs = viewantcolony.getProbamurs().getTextFieldInput();
			try {
				double Knbmurs = Double.parseDouble(murs);
			    double Knbfourmi = Double.parseDouble(fourmi);
			    double Knbgraines = Double.parseDouble(graines);
			  
				if (Knbmurs >= 0 && Knbfourmi >= 0 && Knbgraines >=0) {
					nbmurs = (int) Knbmurs;
					nbfourmi = (int) Knbfourmi;
					nbgraines = (int) Knbgraines;
			        myCustomAlert(AlertType.INFORMATION,"Confirmation",null,"Les Valeurs Aleatoires ont été prise en compte !");	
			    } else {
			        // Afficher un message d'erreur si les nombres ne sont pas positifs
			        myCustomAlert(AlertType.ERROR,"Erreur",null,"Les entrées doivent être des nombres positifs.");
			    }	    
			} catch (NumberFormatException expt) {
			    // Afficher un message d'erreur si les entrées ne sont pas des nombres valides
			    myCustomAlert(AlertType.ERROR,"Erreur",null,"Les entrées doivent être des nombres valides.");
			}
		});
		
		viewantcolony.getReset().setOnAction(e -> {
		    if(myCustomeAlerteConfirm("Reset Game",
		    		"Voulez-vous vraiment réinitialiser le jeu ?",
		    		"Tout progrès sera perdu !")) {
		    	resetGame();
		    }
		    
		});
		viewantcolony.getConfirmerParam().setOnAction(e -> {
			
			String newcap = viewantcolony.getChangecapacite().getTextFieldInput();
			String newtaille = viewantcolony.getChangeTaille().getTextFieldInput();
			try {
			    double cap = Double.parseDouble(newcap);
			    double taille = Double.parseDouble(newtaille);
			    if (cap >= 0 && taille >= 0) {
			        Fourmiliere nvFormuliere = new Fourmiliere((int)taille, (int)taille, (int)cap);
			        Board nvplateau = new Board(nvFormuliere);
			        PlayPauseButton nvbtn = new PlayPauseButton(viewantcolony.getPlaypause().getSize(), nvFormuliere, nvplateau, viewantcolony.getZoomedWindow());
			        viewantcolony.setPlateau(nvplateau);
			        
			        this.setAntcolony(nvFormuliere);
			        viewantcolony.setAntcolony(nvFormuliere);
			        
			        viewantcolony.setPlaypause(nvbtn);
			        
			        viewantcolony.getPlateau().updateGrid();
			    } else {
			        // Afficher un message d'erreur si les nombres ne sont pas positifs
			        Alert alert = new Alert(AlertType.ERROR);
			        alert.setTitle("Erreur");
			        alert.setHeaderText(null);
			        alert.setContentText("Les deux entrées doivent être des nombres positifs.");
			        alert.showAndWait();
			    }
			} catch (NumberFormatException expt) {
			    // Afficher un message d'erreur si les entrées ne sont pas des nombres valides
			    Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Erreur");
			    alert.setHeaderText(null);
			    alert.setContentText("Les deux entrées doivent être des nombres valides.");
			    alert.showAndWait();
			}
		});
			 
	}
	
	public void initAleatoire( int nbMurs,int nbFourmis, int nbGraines) {
	    // Place nbMurs murs aléatoirement
	    Random rand = new Random();
	   // int nbMurs = rand.nextInt(antcolony.getHauteur()+antcolony.getLargeur()* rand.nextInt(randomMur)) / 2;
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


	private boolean myCustomeAlerteConfirm(String title,String HeaderText, String ContentText) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(HeaderText);
	    alert.setContentText(ContentText);

	    Optional<ButtonType> result = alert.showAndWait();
	    return result.isPresent() && result.get() == ButtonType.OK;
	}
	
	private void myCustomAlert(AlertType alerttype,String title,String HeaderText, String ContentText) {
		  Alert alert = new Alert(alerttype);
		  alert.setTitle(title);
		  alert.setHeaderText(HeaderText);
		  alert.setContentText(ContentText);
		  alert.showAndWait();
	}
	
	
	public Fourmiliere getAntcolony() {
		return antcolony;
	}

	public void setAntcolony(Fourmiliere antcolony) {
		this.antcolony = antcolony;
	}



}
