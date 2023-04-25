package controller;

import java.util.Optional;
import java.util.Random;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.control.ButtonType;
import model.Fourmiliere;
import view.Board;
import view.MyCustomAlert;
import view.MyInfoTab;
import view.MyParamTab;
import view.ViewAntColony;

/**
 * Le controleur pour la simulation de la fourmiliere. Cette classe gere les interactions entre la vue et la fourmiliere.
 */
public class ControllerAntColony {
	
	private Fourmiliere antcolony;
	private ViewAntColony viewantcolony;
	private int nbmurs , nbfourmi ,nbgraines ;
	private final DoubleProperty SizeProperty;
	
	/*****/
	/*
	 * les coefficients de densit�. Ils permettent de calculer le nombre maximal de graines, de murs et de fourmis en fonction de la taille de la colonie de fourmis.
	 * Afin que la simulation soit plus clair car s'il y a trop de fourmis ou graines cela pourrait g�ner la compr�hension des evenements donc on limite. 
	 */
	private final static double coefDensiteFourmis = 0.076;
	private final static double coefDensiteGraines = 0.21;
	private final static double coefDensiteMurs = 0.55;
	
	/**
	 * Cr�e un nouveau contrleur pour la simulation de la fourmili�re.
	 *
	 * @param colony la fourmili�re utilis�e dans la simulation
	 * @param vue la vue utilis�e pour afficher la simulation
	 */
	public ControllerAntColony(Fourmiliere colony, ViewAntColony vue) {
		this.antcolony = colony;
		this.viewantcolony = vue;	
		SizeProperty = new SimpleDoubleProperty();
		
		doInitEvents();
		doResetEvent();
		doChangeCapEvent();
		doChangeTailleEvent();	
		doEventsChangeTerrainBoard();
	}
	
	/**
	 * Met en place l'�v�nement de changement de capacit�.
	 * 
	 */
	private void doChangeCapEvent() {
		viewantcolony.getConfirmerParamCap().setOnAction(e ->{
			String newcap = viewantcolony.getChangecapacite().getTextFieldInput();
			try {
			    double cap = Double.parseDouble(newcap);
			    if (cap > 0 ) {
			    	antcolony.setQMax((int)cap);
			    	@SuppressWarnings("unused")
					MyCustomAlert alert = new MyCustomAlert(AlertType.INFORMATION,"Succes",null,"La nouvelle capacite a ete prise en compte !");
			    } else {
				    @SuppressWarnings("unused")
				    MyCustomAlert alert = new MyCustomAlert(AlertType.INFORMATION,"Erreur",null,"La nouvelle capacite doit etre superieur � 0 !");
			    }
			} catch (NumberFormatException expt) {
			    // Afficher un message d'erreur si les entr�es ne sont pas des nombres valides
				 @SuppressWarnings("unused")
				MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"L'entree doit �tre un nombre valide.");
			}
		});
	}
	
	/**
	 * Met en place l'�v�nement de changement de taille.
	 */
	private void doChangeTailleEvent() {
		viewantcolony.getConfirmerParamTaille().setOnAction(e -> {		
			 if(myCustomeAlerteConfirm("Change Taille Simulation",
			    		"Voulez-vous vraiment changer la taille du plateau ?",
			    		"Tout progres sera perdu !")) { 
				 //on recupere la taille
				 	String newtaille = viewantcolony.getChangeTaille().getTextFieldInput();
					try {
					    double taille = Double.parseDouble(newtaille);
					    if ( taille >= 20) {
					    	
					    	//On creer une nouvelle fourmilere
					        Fourmiliere nvFormuliere = new Fourmiliere((int)taille, (int)taille, antcolony.getQMax());
					        
					        //on creer un nv plateau qui va avec la nouvelle fourmiliere
					        Board nvplateau = new Board(nvFormuliere);	        
					        viewantcolony.setPlateau(nvplateau);
					        
					        //On met � jour la nvl foumiliere dans la vue et dans le controller
					        this.setAntcolony(nvFormuliere);
					        viewantcolony.setAntcolony(nvFormuliere);
					        
					        // on Ferme l'ancienne fenetre de zoom s'elle existe car on va changer de plateau
				        	if(viewantcolony.getZoomedWindow() != null) 
				        		viewantcolony.getZoomedWindow().close();
					        
					        //On creer les 2 nouvelles onglets pour synchroniser les informations avec la nouvelle fourmiliere
					        MyInfoTab nvinfo = new MyInfoTab(nvFormuliere, nvplateau);
					        MyParamTab nvparam = new MyParamTab(nvinfo, nvFormuliere);
					        
					        //On mets les 2 nouvelles onglets dans la vue
					        viewantcolony.setNewTabs(nvinfo,nvparam);
					    
					        //On update le plateau et on re update les actions des boutons init et reset et changement de taille et de capacite
					        //pour tout synchroniser avec avec la nouvelle fourmiliere et le nouveau plateau
					        viewantcolony.getPlateau().updateGrid();
					        doInitEvents();
					        doResetEvent();
					        doChangeCapEvent();
					        doChangeTailleEvent();
					        doEventsChangeTerrainBoard();
					        
					        //On update la taille de la fentre principale
					        //Techniquement ici la varibale taille ne sert � rien, c'est seulement pour update la prop et notifier dans le main pour adapter la taille de la fentre
					        setSize(taille);
					        
					        @SuppressWarnings("unused")
							MyCustomAlert alert = new MyCustomAlert(AlertType.INFORMATION,"Succes",null,"La nouvelle taille a ete prise en compte !"); 
					        
					    } else {
					        // Afficher un message d'erreur si les nombres ne sont pas positifs
					    	 @SuppressWarnings("unused")
							MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"L'entree doit etre un nombre superieur ou egale � 20 (taille minimale d'une fourmiliere).");
					    }
					} catch (NumberFormatException expt) {
					    // Afficher un message d'erreur si l'entr�e n est pas un nombre valide
						 @SuppressWarnings("unused")
						MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"L'entree doit �tre un nombre valide.");
					}
			    }	
		});		 	
	}
	
	/**
	 * Met en place l'�v�nement de r�initialisation.
	 */
	private void doResetEvent() {
		viewantcolony.getReset().setOnAction(e -> {
		    if(myCustomeAlerteConfirm("Reset Simulation",
		    		"Voulez-vous vraiment reinitialiser la simulation ?",
		    		"Tout progres sera perdu !")) {
		    	resetGame();
		    }
		    
		});
	}
	

	/**
	 * Met en place les evenements d'initialisation.
	 */
	private void doInitEvents() {
		viewantcolony.getInit().setOnAction(e ->{
		    if(myCustomeAlerteConfirm("Init Simulation",
		    		"Voulez-vous vraiment initialiser la simulation ?",
		    		"Tout progrss sera perdu !")) {
		    	if(nbfourmi == 0 || nbgraines == 0 || nbmurs == 0) {
		    		@SuppressWarnings("unused")
					MyCustomAlert alert = new MyCustomAlert(AlertType.WARNING,"Attention",null,"Vous avez oublie de confirmer les valeurs d'initialisations !");
		    	}else {
		    	  	resetGame();
			       	initAleatoire(nbmurs,nbfourmi,nbgraines);
					viewantcolony.getPlateau().updateGrid(); // on MAJ le plateau 
					if(viewantcolony.getZoomedWindow() != null) // on MAJ le plateau zoom s'il existe
		        		viewantcolony.getZoomedWindow().updateZoomGrid();
					antcolony.MAJNbGrainesTotal();
		    	}
		    }
		});
		
		
		viewantcolony.getConfirmerInit().setOnAction(e -> {
			
			//On recupere les 3 entr�es des textfields 
			String fourmi = viewantcolony.getProbaFourmi().getTextFieldInput();
			String graines = viewantcolony.getProbagraines().getTextFieldInput();
			String murs = viewantcolony.getProbamurs().getTextFieldInput();
			try {
				double Knbmurs = Double.parseDouble(murs);
			    double Knbfourmi = Double.parseDouble(fourmi);
			    double Knbgraines = Double.parseDouble(graines);
			    
				if (Knbmurs > 0 && Knbfourmi > 0 && Knbgraines > 0) {
					
				    //On fait des verficataion car si on met un nombre trop eleve par rapport � la taille du plateau, le modele aura tendance � crash
					int nbMaxFourmis = (int) (antcolony.getHauteur() * antcolony.getLargeur() * coefDensiteFourmis ); 
				    boolean nbFourmisValid = nbMaxFourmis >= Knbfourmi;

				    int nbCases =  antcolony.getLargeur() * antcolony.getHauteur();
				    int nbCasesBordure = ( antcolony.getLargeur() + antcolony.getHauteur()) * 2;
				    int nbCasesInterieures = nbCases - nbCasesBordure;
				    
				    int nbMursMax = (int) (nbCasesInterieures * coefDensiteMurs); 
				    boolean densiteMursValid = nbMursMax >= Knbmurs;
				    
					int nbGrainesMax = (int) (((nbCasesInterieures * antcolony.getQMax()) - nbMursMax)* coefDensiteGraines);
					boolean densiteGrainesValid = nbGrainesMax >= Knbgraines; 
									    
				    if(densiteMursValid && nbFourmisValid && densiteGrainesValid) {
				    	nbmurs = (int) Knbmurs;
						nbfourmi = (int) Knbfourmi;
						nbgraines = (int) Knbgraines;
				        @SuppressWarnings("unused")
						MyCustomAlert alert = new MyCustomAlert(AlertType.INFORMATION,"Confirmation",null,"Les valeurs d'initialisations ont ete prise en compte !");
				    }
				    else {
				    	@SuppressWarnings("unused")
						MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur","Pour un plateau de taille "+ antcolony.getHauteur()+ "*"+ antcolony.getLargeur() +
																						", les valeurs max d'initialisations sont : \n" ," Nombre de Fourmi : " + nbMaxFourmis + "\n" +
																																		 " Densite des Graines : " + nbGrainesMax + "\n" + 
																																		 " Densite des Murs : " + nbMursMax );
				    }
			    } else {
			        // Afficher un message d'erreur si les nombres ne sont pas positifs
			        @SuppressWarnings("unused")
					MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"Les entrees doivent etre des nombres positifs, strictement superieur a 0.");
			    }	    
			} catch (NumberFormatException expt) {
			    // Afficher un message d'erreur si les entr�es ne sont pas des nombres valides
			    @SuppressWarnings("unused")
				MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"Les entrees doivent etre des nombres valides.");
			}
		});
	}
	
	   /**
     * Configure les �v�nements de la souris pour changer l'�tat de la grille (ajout de fourmi ou changement d'�tat du mur).
     */
    private void doEventsChangeTerrainBoard() {
    	//pour l'ajout des murs/fourmis
    	viewantcolony.getPlateau().setOnMouseClicked(event -> {
    		//on fait les changements seulement si la simulation n'est pas en mode play
    		if(!viewantcolony.getplaypause().getisPlaying()) {
    			int x = (int) (event.getX() / viewantcolony.getPlateau().getCellSize());
		        int y = (int) (event.getY() / viewantcolony.getPlateau().getCellSize());
	        
	        	if (event.getButton() == MouseButton.PRIMARY) {
		            if (event.isShiftDown() && !antcolony.contientFourmi(x, y)) {
		                // Ajoute une fourmi � la position (x, y)
		                antcolony.ajouteFourmi(x, y);
		            } else if (antcolony.getQteGraines(x, y) == 0) {
	                	// Change l'�tat du mur � la position (x, y)
    	                antcolony.setMur(x, y, !antcolony.getMur(x, y));
		            }
    	        }
	        	viewantcolony.getPlateau().updateGrid(); // on MAJ le plateau
	        	if(viewantcolony.getZoomedWindow() != null) // on MAJ le plateau zoom s'il existe
	        		viewantcolony.getZoomedWindow().updateZoomGrid();
    		}
		        
    	});
    	    
	    //Pour l'ajout des graines
    	viewantcolony.getPlateau().setOnScroll(event -> {
    		//on fait les changements seulement si la simulation n'est pas en mode play
    		if(!viewantcolony.getplaypause().getisPlaying()) {
    			  int x = (int) (event.getX() / viewantcolony.getPlateau().getCellSize());
    		        int y = (int) (event.getY() / viewantcolony.getPlateau().getCellSize());
    		        
    		        if(!antcolony.getMur(x, y)) {
    		        	if (event.getDeltaY() > 0) {
    			            antcolony.setQteGraines(x, y, antcolony.getQteGraines(x, y) + 1);
    			          
    			        } else {
    			            antcolony.setQteGraines(x, y, antcolony.getQteGraines(x, y) - 1);
    			        }
    		        	viewantcolony.getPlateau().updateGrid(); // on MAJ le plateau
    		        	if(viewantcolony.getZoomedWindow() != null) // on MAJ le plateau zoom s'il existe
    		        		viewantcolony.getZoomedWindow().updateZoomGrid();
    		        }
	        }
	    });
    }
	
	/**
	 * Initialise le jeu avec des murs, des fourmis et des graines plac�s al�atoirement.
	 * @param nbMurs le nombre de murs � placer
	 * @param nbFourmis le nombre maximal de fourmis � placer
	 * @param nbGraines le nombre de graines � placer
	*/
	private void initAleatoire( int nbMurs,int nbFourmis, int nbGraines) {
	    // Place nbMurs murs al�atoirement
	    Random rand = new Random();
	   // int nbMurs = rand.nextInt(antcolony.getHauteur()+antcolony.getLargeur()* rand.nextInt(randomMur)) / 2;
	    for (int i = 0; i < nbMurs; i++) {
	        int x = rand.nextInt(antcolony.getHauteur()) + 1;
	        int y = rand.nextInt(antcolony.getLargeur()) + 1;
	        antcolony.setMur(y, x, true);
	    }
	    
	    // Place jusqu'� nbFourmis fourmis al�atoirement
	    int nbFourmisPlaces = 0;
	    while (nbFourmisPlaces < nbFourmis) {
	    	int x = rand.nextInt(antcolony.getHauteur()) + 1;
		    int y = rand.nextInt(antcolony.getLargeur()) + 1;
	        if (!antcolony.contientFourmi(y, x) && !antcolony.getMur(y, x)) {
	            antcolony.ajouteFourmi(y, x);
	            nbFourmisPlaces++;
	        }
	    }
	    
	    // Place nbGraines graines al�atoirement
	    for (int i = 0; i < nbGraines; i++) {
	    	int x = rand.nextInt(antcolony.getHauteur()) + 1;
		    int y = rand.nextInt(antcolony.getLargeur()) + 1;

	        if (!antcolony.getMur(y, x)) {
	            int qte = rand.nextInt(antcolony.getQMax() + 1);
	            antcolony.setQteGraines(y, x, qte);
	        }
	    }
	}
	
	/**
	 * R�initialise le jeu en remettant les mod�les et la vue � leur �tat initial.
	*/
	public void resetGame() {
		antcolony.resetModel();
		viewantcolony.getPlateau().resestGrid();
    	if(viewantcolony.getZoomedWindow() != null) // on MAJ le plateau zoom s'il existe
    		viewantcolony.getZoomedWindow().updateZoomGrid();
	}

	/**
	 * Affiche une bo�te de dialogue personnalis�e de confirmation.
	 * @param title le titre de la bo�te de dialogue
	 * @param HeaderText le texte d'en-t�te de la bo�te de dialogue
	 * @param ContentText le texte de contenu de la bo�te de dialogue
	 * @return true si l'utilisateur a appuy� sur le bouton OK, false sinon
	*/
	private boolean myCustomeAlerteConfirm(String title,String HeaderText, String ContentText) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(HeaderText);
	    alert.setContentText(ContentText);

	    Optional<ButtonType> result = alert.showAndWait();
	    return result.isPresent() && result.get() == ButtonType.OK;
	}
	
	//////////////////////
	// SETTERS/GETTERS 
	//
	public Fourmiliere getAntcolony() {
		return antcolony;
	}

	public void setAntcolony(Fourmiliere antcolony) {
		this.antcolony = antcolony;
	}
	
	public DoubleProperty SizeProperty() {
	    return SizeProperty;
	}	
	
	public double getSize() {
	    return SizeProperty.get();
	}	
	
	public void setSize(double value) {
		SizeProperty.set(value);
	}
}
