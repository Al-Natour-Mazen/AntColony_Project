package view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * MySpring est une classe representant un espace de ressort qui peut etre utilise pour etirer les regions dans les conteneurs tels que HBox et VBox.
 * Il permet d'etirer les regions pour remplir l'espace disponible dans la direction specifiee.
*/
public class MySpring extends Region{
    
	/**
	Construit un objet MySpring pour le type de conteneur specifie.
	@param type le type de conteneur pour lequel l'espace de ressort est cree. Doit etre soit "HBox" soit "VBox".
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
