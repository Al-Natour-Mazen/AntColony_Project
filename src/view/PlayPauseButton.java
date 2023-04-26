package view;

import java.io.InputStream;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Fourmiliere;

/**
 * Cette classe représente un bouton personnalisé permettant de jouer et mettre en pause
 * l'évolution d'une fourmilière.
 */
public class PlayPauseButton extends Button {

    private final Image imagePause;
    private final Image imagePlay;
    private final ImageView imageViewPause;
    private final ImageView imageViewPlay;
    
	private final Fourmiliere antcolony;
	private final Board plateau;
	private ZoomWindow zoom;

	/* On définie comme final la Property afin de garantir qu'une fois initialisée dans le constructeur, elle ne peut 
	 * plus être remplacée par une autre instance de chaque Property. Cela permet d'assurer l'intégrité 
	 * des données de la propriété et d'éviter toute modification accidentelle ou malveillante de la référence de la propriété.
	 */
	private final DoubleProperty valueSpeedProperty;
	private final BooleanProperty isPlayingProperty;  
	private final static int DefaultSpeed = 1;
	private final static boolean DefaultisPlaying = false;   
	
	private int size;
	
 
    // Créer le service qui sera lancé en arrière-plan
    private final Service<Void> service = new Service<>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    while (getisPlaying() && !isCancelled()) {
                    	Platform.runLater(()->{
                			antcolony.evolue();
                    		plateau.updateGrid();
                    		if(zoom != null)
                    			zoom.updateZoomGrid();
                		});
                    	
                    	// on affiche l'affichage console pour verifier que tout va bien
                    	System.out.println(antcolony.stringMurs());
             		    System.out.println(antcolony.stringGraines());	
             		    System.out.println(antcolony.stringFourmis());
                    
             		   //On Récupére la valeur de valuespeed
             		    double valuespeed = getValueSpeed();

             		    // On Calcule le temps de sommeil en fonction de valuespeed
             		    long sleepTime = (long) (1050 - (valuespeed * 100)) /2  ;

             		    // Vérifier que le temps de sommeil est supérieur à zéro pour éviter une exception
             		    if (sleepTime > 0) {
             		        Thread.sleep(sleepTime);
             		    }
                    }
                    return null;
                }
            };
        }
    };
    

    /**
     * Constructeur de la classe PlayPauseButton.
     * @param size La taille du bouton.
     * @param fm La fourmilière à faire évoluer.
     * @param board Le plateau de jeu.
     */
    public PlayPauseButton(int size, Fourmiliere fm, Board board) {
		this.antcolony =fm;
		this.plateau = board;
		this.size = size;
		// Charger les images depuis les fichiers
        InputStream imgPause = this.getClass().getResourceAsStream("pause.png");
        InputStream imgPlay = this.getClass().getResourceAsStream("play.png");

        imagePause = new Image(imgPause, size, size, true, true);
        imagePlay = new Image(imgPlay, size, size, true, true);

        imageViewPause = new ImageView(imagePause);
        imageViewPlay = new ImageView(imagePlay);

        // Initialiser le bouton avec l'image de play
        setGraphic(imageViewPlay);
    	isPlayingProperty = new SimpleBooleanProperty(DefaultisPlaying);
        valueSpeedProperty = new SimpleDoubleProperty(DefaultSpeed);

        
        // Ajouter un listener pour changer l'image et le status du service
        setOnAction(event -> {
            if (getisPlaying()) {
                // Passer en mode pause
            	plateau.setdisplayGrids(true); // on remet les grilles du plateau 
            	if(zoom != null)
        			zoom.updateZoomGrid();
                setGraphic(imageViewPlay); // on change l'image du btn
                // on arrete le service et on MAJ la property isPlaying
                setisPlaying(false); 
                service.cancel();
            } else {
                // Passer en mode play
            	plateau.setdisplayGrids(false); // on enleve les grilles du plateau 
                setGraphic(imageViewPause); // on change l'image du btn
                // on relance le service et on MAJ la property isPlaying
                setisPlaying(true);
                service.restart();
            }
        });
        
        setStyle("-fx-background-color: transparent;");
    }
    
    
	//////////////////////
	// SETTERS/GETTERS 
	//
    
    public DoubleProperty valueSpeedProperty() {
        return valueSpeedProperty;
    }

    public double getValueSpeed() {
        return valueSpeedProperty.get();
    }

    public void setValueSpeed(double value) {
    	valueSpeedProperty.set(value);
    }
    public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
 
    public BooleanProperty isPlayingProperty() {
      return isPlayingProperty;
    }
    
    public boolean getisPlaying() {
      return isPlayingProperty.get();
    }
    
    public void setisPlaying(boolean val) {
	  isPlayingProperty.set(val);
    }	
    
	public ZoomWindow getZoom() {
		return zoom;
	}
	public void setZoom(ZoomWindow zoom) {
		this.zoom = zoom;
	}

    
}
