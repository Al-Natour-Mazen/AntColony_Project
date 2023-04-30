package view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * MySpring est une classe représentant un espace de ressort qui peut être utilisé pour étirer les régions dans les conteneurs tels que HBox et VBox.
 * Il permet d'étirer les régions pour remplir l'espace disponible dans la direction spécifiée.
*/
public class MySpring extends Region{
    
	/**
	Construit un objet MySpring pour le type de conteneur spécifié.
	@param type le type de conteneur pour lequel l'espace de ressort est créé. Doit être soit "HBox" soit "VBox".
	@throws IllegalArgumentException si le type de conteneur n'est pas reconnu.
	*/
    public MySpring(String type){
    	if(type.equals("HBox")) {
    		HBox.setHgrow(this,Priority.ALWAYS);
    	}
       	else if (type.equals("VBox")) {
    		VBox.setVgrow(this,Priority.ALWAYS);
    	}
    	else {
    		throw new IllegalArgumentException("Error MySpring Class : "+ type + "is not recognized for a Region");	
    	}
        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);
    }   
}
