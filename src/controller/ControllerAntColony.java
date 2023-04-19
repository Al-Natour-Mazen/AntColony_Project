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
					MyCustomAlert alert = new MyCustomAlert(AlertType.INFORMATION,"Succ�s",null,"La nouvelle capacit� a �t� prise en compte !");
			    } else {
				    @SuppressWarnings("unused")
				    MyCustomAlert alert = new MyCustomAlert(AlertType.INFORMATION,"Erreur",null,"La nouvelle capacit� doit �tre superieur � 0 !");
			    }
			} catch (NumberFormatException expt) {
			    // Afficher un message d'erreur si les entr�es ne sont pas des nombres valides
				 @SuppressWarnings("unused")
				MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"L'entr�e doit �tre un nombre valide.");
			}
		});
	}
	
	/**
	 * Met en place l'�v�nement de changement de taille.
	 */
	private void doChangeTailleEvent() {
		viewantcolony.getConfirmerParamTaille().setOnAction(e -> {		
			 if(myCustomeAlerteConfirm("Change Taille Simulation",
			    		"Voulez-vous vraiment Changer la taille du plateau  ?",
			    		"Tout progr�s sera perdu !")) { 
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
					        //Techniquement ici �a ne sert � rien la varibale taille, c'est seulement pour update la prop et notifier dans le main pour adapter la taille de la fentre
					        setSize(taille);
					        
					        @SuppressWarnings("unused")
							MyCustomAlert alert = new MyCustomAlert(AlertType.INFORMATION,"Succ�s",null,"La nouvelle taille a �t� prise en compte !"); 
					        
					    } else {
					        // Afficher un message d'erreur si les nombres ne sont pas positifs
					    	 @SuppressWarnings("unused")
							MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"L'entr�e doit �tre un nombre superieur ou �gale � 20 (taille minimale d'une fourmiliere).");
					    }
					} catch (NumberFormatException expt) {
					    // Afficher un message d'erreur si l'entr�e n est pas un nombre valide
						 @SuppressWarnings("unused")
						MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"L'entr�e doit �tre un nombre valide.");
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
		    		"Voulez-vous vraiment r�initialiser la simulation ?",
		    		"Tout progr�s sera perdu !")) {
		    	resetGame();
		    }
		    
		});
	}
	

	/**
	 * Met en place l'�v�nement d'initialisation.
	 */
	private void doInitEvents() {
		viewantcolony.getInit().setOnAction(e ->{
		    if(myCustomeAlerteConfirm("Init Simulation",
		    		"Voulez-vous vraiment initialiser la simulation ?",
		    		"Tout progr�s sera perdu !")) {
		    	if(nbfourmi == 0 || nbgraines == 0 || nbmurs == 0) {
		    		@SuppressWarnings("unused")
					MyCustomAlert alert = new MyCustomAlert(AlertType.WARNING,"Attention",null,"Vous avez oubli� de confirmer les valeurs d'initialisations !");
		    	}else {
		    	  	resetGame();
			       	initAleatoire(nbmurs,nbfourmi,nbgraines);
					viewantcolony.getPlateau().updateGrid();
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
			  
				if (Knbmurs >= 0 && Knbfourmi >= 0 && Knbgraines >=0) {
					nbmurs = (int) Knbmurs;
					nbfourmi = (int) Knbfourmi;
					nbgraines = (int) Knbgraines;
			        @SuppressWarnings("unused")
					MyCustomAlert alert = new MyCustomAlert(AlertType.INFORMATION,"Confirmation",null,"Les valeurs d'initialisations ont �t� prise en compte !");
			    } else {
			        // Afficher un message d'erreur si les nombres ne sont pas positifs
			        @SuppressWarnings("unused")
					MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"Les entr�es doivent �tre des nombres positifs.");
			    }	    
			} catch (NumberFormatException expt) {
			    // Afficher un message d'erreur si les entr�es ne sont pas des nombres valides
			    @SuppressWarnings("unused")
				MyCustomAlert alert = new MyCustomAlert(AlertType.ERROR,"Erreur",null,"Les entr�es doivent �tre des nombres valides.");
			}
		});
	}
	
	   /**
     * Configure les �v�nements de la souris pour changer l'�tat de la grille (ajout de fourmi ou changement d'�tat du mur).
     */
    private void doEventsChangeTerrainBoard() {
    	//pour l'ajout des murs/fourmis
    	viewantcolony.getPlateau().setOnMouseClicked(event -> {
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
	        	//on regarde si la simualtion n'est pas encore
	        	// si elle est encore alors �a ne sert � rien d'update le plateau 2 fois, si on le fait on risque d'avoir des fourmis fantomes pendant quelques tours
	        	// sinon on va l'update 
	        	// car si on met pas de condtion nous aurons des fourmis fantomes durant 1 tour chose qui n'est pas normal
	        	if(!viewantcolony.getplaypause().getisPlaying())
	        		viewantcolony.getPlateau().updateGrid();
    	    });
    	    
	    //Pour l'ajout des graines
    	viewantcolony.getPlateau().setOnScroll(event -> {
	        int x = (int) (event.getX() / viewantcolony.getPlateau().getCellSize());
	        int y = (int) (event.getY() / viewantcolony.getPlateau().getCellSize());
	        
	        if(!antcolony.getMur(x, y)) {
	        	if (event.getDeltaY() > 0) {
		            antcolony.setQteGraines(x, y, antcolony.getQteGraines(x, y) + 1);
		          
		        } else {
		            antcolony.setQteGraines(x, y, antcolony.getQteGraines(x, y) - 1);
		        }
	        	//on regarde si la simualtion n'est pas encore
	        	// si elle est encore alors �a ne sert � rien d'update le plateau 2 fois
	        	// sinon on va l'update 
	        	// car si on met pas de condtion nous aurons des fourmis fantomes durant 1 tour chose qui n'est pas normal
	        	if(!viewantcolony.getplaypause().getisPlaying())
	        		viewantcolony.getPlateau().updateGrid();
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
