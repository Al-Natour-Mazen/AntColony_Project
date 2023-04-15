package controller;

import java.util.Optional;
import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import model.Fourmiliere;
import view.Board;
import view.MyCustomAlert;
import view.MyInfoTab;
import view.MyParamTab;
import view.ViewAntColony;

public class ControllerAntColony {
	
	private Fourmiliere antcolony;
	private ViewAntColony viewantcolony;
	private int nbmurs , nbfourmi ,nbgraines ;
	
	public ControllerAntColony(Fourmiliere colony, ViewAntColony vue) {
		this.antcolony = colony;
		this.viewantcolony = vue;
		
		doInitEvents();
		doResetEvents();
		doChangeCapEvent();
		doChangeTailleEvent();	
	}
	
	private void doChangeCapEvent() {
		viewantcolony.getConfirmerParamCap().setOnAction(e ->{
			String newcap = viewantcolony.getChangecapacite().getTextFieldInput();
			try {
			    double cap = Double.parseDouble(newcap);
			    if (cap >= 0 ) {
			    	antcolony.setQMax((int)cap);
			    	@SuppressWarnings("unused")
					MyCustomAlert alert = new MyCustomAlert(AlertType.INFORMATION,"Succès",null,"La nouvelle capacité a été prise en compte !");
			    } 
			} catch (NumberFormatException expt) {
			    // Afficher un message d'erreur si les entrées ne sont pas des nombres valides
				 @SuppressWarnings("unused")
				MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"Les entrées doivent être des nombres valides.");
			}
		});
	}
	
	private void doChangeTailleEvent() {
		viewantcolony.getConfirmerParamTaille().setOnAction(e -> {			
			String newtaille = viewantcolony.getChangeTaille().getTextFieldInput();
			try {
			    double taille = Double.parseDouble(newtaille);
			    if ( taille >= 0) {
			    	
			    	//On creer une nouvelle fourmilere
			        Fourmiliere nvFormuliere = new Fourmiliere((int)taille, (int)taille, antcolony.getQMax());
			        
			        //on creer un nv plateau qui va avec la nouvelle fourmiliere
			        Board nvplateau = new Board(nvFormuliere);	        
			        viewantcolony.setPlateau(nvplateau);
			        
			        //On met à jour la nvl foumiliere dans la vue et dans le controller
			        this.setAntcolony(nvFormuliere);
			        viewantcolony.setAntcolony(nvFormuliere);
			        
			        //On creer les 2 nouvelles onglets pour synchroniser 
			        MyInfoTab nvinfo = new MyInfoTab(nvFormuliere, nvplateau);
			        MyParamTab nvparam = new MyParamTab(nvinfo, nvFormuliere);
			        
			        //On mets les 2 nouvelles onglets dans la vue
			        viewantcolony.setNewTabs(nvinfo,nvparam);
			    
			        //On update le plateau et on re met à jour les actions des boutons init et reset et changement de taille et de capacite
			        viewantcolony.getPlateau().updateGrid();
			        doInitEvents();
			        doResetEvents();
			        doChangeCapEvent();
			        doChangeTailleEvent();
			        
			        @SuppressWarnings("unused")
					MyCustomAlert alert = new MyCustomAlert(AlertType.INFORMATION,"Succès",null,"La nouvelle taille a été prise en compte !"); 
			    } else {
			        // Afficher un message d'erreur si les nombres ne sont pas positifs
			    	 @SuppressWarnings("unused")
					MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"Les entrées doivent être des nombres positifs.");
			    }
			} catch (NumberFormatException expt) {
			    // Afficher un message d'erreur si les entrées ne sont pas des nombres valides
				 @SuppressWarnings("unused")
				MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"Les entrées doivent être des nombres valides.");
			}
		});		 
		
	}
	
	private void doResetEvents() {
		viewantcolony.getReset().setOnAction(e -> {
		    if(myCustomeAlerteConfirm("Reset Simulation",
		    		"Voulez-vous vraiment réinitialiser la simulation ?",
		    		"Tout progrès sera perdu !")) {
		    	resetGame();
		    }
		    
		});
		
	}
	
	private void doInitEvents() {
		viewantcolony.getInit().setOnAction(e ->{
		    if(myCustomeAlerteConfirm("Init Simulation",
		    		"Voulez-vous vraiment initialiser la simulation ?",
		    		"Tout progrès sera perdu !")) {
		    	if(nbfourmi == 0 || nbgraines == 0 || nbmurs == 0) {
		    		@SuppressWarnings("unused")
					MyCustomAlert alert = new MyCustomAlert(AlertType.WARNING,"Attention",null,"Vous avez oublié de confirmer les valeurs d'initialisations !");
		    	}
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
			        @SuppressWarnings("unused")
					MyCustomAlert alert = new MyCustomAlert(AlertType.INFORMATION,"Confirmation",null,"Les Valeurs d'initialisations ont été prise en compte !");
			    } else {
			        // Afficher un message d'erreur si les nombres ne sont pas positifs
			        @SuppressWarnings("unused")
					MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"Les entrées doivent être des nombres positifs.");
			    }	    
			} catch (NumberFormatException expt) {
			    // Afficher un message d'erreur si les entrées ne sont pas des nombres valides
			    @SuppressWarnings("unused")
				MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"Les entrées doivent être des nombres valides.");
			}
		});
	}
	
	private void initAleatoire( int nbMurs,int nbFourmis, int nbGraines) {
	    // Place nbMurs murs aléatoirement
	    Random rand = new Random();
	   // int nbMurs = rand.nextInt(antcolony.getHauteur()+antcolony.getLargeur()* rand.nextInt(randomMur)) / 2;
	    for (int i = 0; i < nbMurs; i++) {
	        int x = rand.nextInt(antcolony.getHauteur()) + 1;
	        int y = rand.nextInt(antcolony.getLargeur()) + 1;
	        antcolony.setMur(y, x, true);
	    }
	    
	    // Place jusqu'à nbFourmis fourmis aléatoirement
	    int nbFourmisPlaces = 0;
	    while (nbFourmisPlaces < nbFourmis) {
	    	int x = rand.nextInt(antcolony.getHauteur()) + 1;
		    int y = rand.nextInt(antcolony.getLargeur()) + 1;
	        if (!antcolony.contientFourmi(y, x) && !antcolony.getMur(y, x)) {
	            antcolony.ajouteFourmi(y, x);
	            nbFourmisPlaces++;
	        }
	    }
	    
	    // Place nbGraines graines aléatoirement
	    for (int i = 0; i < nbGraines; i++) {
	    	int x = rand.nextInt(antcolony.getHauteur()) + 1;
		    int y = rand.nextInt(antcolony.getLargeur()) + 1;

	        if (!antcolony.getMur(y, x)) {
	            int qte = rand.nextInt(antcolony.getQMax() + 1);
	            antcolony.setQteGraines(y, x, qte);
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
	
	public Fourmiliere getAntcolony() {
		return antcolony;
	}

	public void setAntcolony(Fourmiliere antcolony) {
		this.antcolony = antcolony;
	}



}
