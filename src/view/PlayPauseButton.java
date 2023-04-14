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

public class PlayPauseButton extends Button {

    private final Image imagePause;
    private final Image imagePlay;
    private final ImageView imageViewPause;
    private final ImageView imageViewPlay;
    
	private final Fourmiliere antcolony;
	private final Board plateau;
	private final ZoomWindow zoom;
	
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
                    while (getisPlaying()) {
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
    


    public PlayPauseButton(int size, Fourmiliere fm, Board board, ZoomWindow zoomepane) {
    	this.zoom = zoomepane;
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
                setGraphic(imageViewPlay);
                setisPlaying(false);
                service.cancel();
            } else {
                // Passer en mode play
                setGraphic(imageViewPause);
                setisPlaying(true);
                service.restart();
            }
        });
        
	
        setStyle("-fx-background-color: transparent;");
    }
    
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
}
