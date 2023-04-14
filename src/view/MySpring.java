package view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MySpring extends Region{
    
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
