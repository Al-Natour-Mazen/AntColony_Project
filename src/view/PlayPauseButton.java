package view;

import java.io.InputStream;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Fourmiliere;
/*
public class PlayPauseButton extends Button{
	
	private Fourmiliere antcolony;
	private Board plateau;
	
    private Service<Void> myService = new Service<Void>(){
        @Override 
        protected Task<Void> createTask(){
            return new Task<Void>(){
                @Override    
                protected Void call() throws Exception {  
                	int x =0;
                	while(!isCancelled()) {
                		
                		Platform.runLater(()->{
                			antcolony.evolue();
                    		plateau.updateGrid();
  
                  		
                		});

          		      System.out.println(antcolony.stringMurs());
          		      System.out.println(antcolony.stringGraines());	
          		      System.out.println(antcolony.stringFourmis());
          	
              	      System.out.println(x);
                		x++;
                	}
                    return null ; 
                }
            };  
        }
    };
	
	public PlayPauseButton(int size, Fourmiliere fm, Board board) {
		this.antcolony =fm;
		this.plateau = board;
		InputStream imgPause =  this.getClass().getResourceAsStream("pause.png");
		InputStream imgPlay =  this.getClass().getResourceAsStream("play.png");
		InputStream imgStart =  this.getClass().getResourceAsStream("start.png");
		
				
		Image imagePause = new Image(imgPause,size,size, true, true);
		Image imageplay = new Image(imgPlay,size,size, true, true);
		Image imageStart = new Image(imgStart,size,size, true, true);
		
		ImageView imageViewPause = new ImageView(imagePause);
		ImageView imageViewPlay = new ImageView(imageplay);
		ImageView imageViewStart = new ImageView(imageStart);
		
		this.setGraphic(imageViewStart);
		
		this.setOnAction(e ->{
	       		if(this.getGraphic().equals(imageViewStart)) {
	       			this.setGraphic(imageViewPlay);
	       			
	       			myService.start();
					   myService.stateProperty().addListener( (ObservableValue, oldValue, newValue) -> {
					       switch (newValue) {
					           case FAILED:
					           case CANCELLED:
					           		myService.reset();
					           		break;
					           case SUCCEEDED:
					        	 	myService.reset();
					        	 	break;
					           default:
					        	   	break;            
					       }
					   });  
	       		}
	           if(this.getGraphic().equals(imageViewPause)) {
	           		this.setGraphic(imageViewPlay);
	           		myService.start();
					   myService.stateProperty().addListener( (ObservableValue, oldValue, newValue) -> {
					       switch (newValue) {
					           case FAILED:
					           case CANCELLED:
					           		myService.reset();
					           		break;
					           case SUCCEEDED:
					        	 	myService.reset();
					        	 	break;
					           default:
					        	   	break;            
					       }
					   });  
	           	   
	           }
	           else if (this.getGraphic().equals(imageViewPlay)) {
	        	   this.setGraphic(imageViewPause);
	        	   myService.cancel();
	        	   
	        	
	           }
	       
				           		
       });
			
	}
	

}
*/
public class PlayPauseButton extends Button {

    private final Image imagePause;
    private final Image imagePlay;
    private final ImageView imageViewPause;
    private final ImageView imageViewPlay;
 
    // Créer le service qui sera lancé en arrière-plan
    private final Service<Void> service = new Service<>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    while (isPlaying) {
                    	Platform.runLater(()->{
                			antcolony.evolue();
                    		plateau.updateGrid();
                    		if(zoom != null)
                    			zoom.updateZoomGrid();
  
                  		
                		});
                    	 System.out.println(antcolony.stringMurs());
             		     System.out.println(antcolony.stringGraines());	
             		     System.out.println(antcolony.stringFourmis());
                    
                        Thread.sleep((long) getValueSpeed()*10);
                    }
                    return null;
                }
            };
        }
    };
    
	private final Fourmiliere antcolony;
	private final Board plateau;
	private final ZoomWindow zoom;
	
	private final DoubleProperty valueSpeedProperty;
	private final static int DefaultSpeed = 10;
	
    private boolean isPlaying;   

    public PlayPauseButton(int size, Fourmiliere fm, Board board, ZoomWindow zoomepane) {
    	this.zoom = zoomepane;
		this.antcolony =fm;
		this.plateau = board;
        // Charger les images depuis les fichiers
        InputStream imgPause = this.getClass().getResourceAsStream("pause.png");
        InputStream imgPlay = this.getClass().getResourceAsStream("play.png");

        imagePause = new Image(imgPause, size, size, true, true);
        imagePlay = new Image(imgPlay, size, size, true, true);

        imageViewPause = new ImageView(imagePause);
        imageViewPlay = new ImageView(imagePlay);

        // Initialiser le bouton avec l'image de play
        setGraphic(imageViewPlay);
        isPlaying = false;

        
        // Ajouter un listener pour changer l'image et le status du service
        setOnAction(event -> {
            if (isPlaying) {
                // Passer en mode pause
                setGraphic(imageViewPlay);
                isPlaying = false;
                service.cancel();
            } else {
                // Passer en mode play
                setGraphic(imageViewPause);
                isPlaying = true;
                service.restart();
            }
        });
        
        valueSpeedProperty = new SimpleDoubleProperty(DefaultSpeed);
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
}
