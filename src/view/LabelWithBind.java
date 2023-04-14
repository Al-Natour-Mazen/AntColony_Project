package view;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class LabelWithBind extends Label{

	private String labelText;
	
	public LabelWithBind(String text, DoubleProperty propToBind) {
		// TODO Auto-generated constructor stub
		super();
		labelText = text;
		MyStringBinding mybind = new MyStringBinding(propToBind);
		textProperty().bind(mybind);;
		this.setAlignment(Pos.CENTER);
		this.setMinWidth(100);
	}
	
    public class MyStringBinding extends StringBinding{

    	private DoubleProperty prop;

    	public MyStringBinding(final DoubleProperty theProp) {
    		this.prop = theProp;
    		bind(theProp);
    	}
    		
		@Override
		protected String computeValue() {
			return labelText + " " + String.format("%.1f", prop.get());
		}
    	
    }
    
}
