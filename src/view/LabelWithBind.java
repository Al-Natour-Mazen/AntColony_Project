package view;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * Cette classe represente un Label avec un texte et une propriete double bindee pour afficher une valeur numerique.
*/
public class LabelWithBind extends Label{

	private String labelText;
	
	/**
	 * Constructeur qui prend en parametre le texte du label et la propriete double bindee.
	 * @param text le texte a afficher sur le label.
	 * @param prop la propriete double a bindee avec le label.
	*/
	public LabelWithBind(String text,DoubleProperty prop) {
		// TODO Auto-generated constructor stub
		super();
		labelText = text;
		MyStringBinding mybind = new MyStringBinding(prop);
		textProperty().bind(mybind);;
		this.setAlignment(Pos.CENTER);
		this.setMinWidth(100);
	}

    public class MyStringBinding extends StringBinding{

    	private DoubleProperty prop;

    	/**
    	 * Constructeur qui prend en parametre la propriete double a bindee.
    	 * @param theProp la propriete double a bindee avec le label.
    	*/
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
