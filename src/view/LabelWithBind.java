package view;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * Cette classe représente un Label avec un texte et une propriété double bindée pour afficher une valeur numérique.
*/
public class LabelWithBind extends Label{

	private String labelText;
	
	/**
	 * Constructeur qui prend en paramètre le texte du label et la propriété double bindée.
	 * @param text le texte à afficher sur le label.
	 * @param prop la propriété double à bindée avec le label.
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
    	 * Constructeur qui prend en paramètre la propriété double à bindée.
    	 * @param theProp la propriété double à bindée avec le label.
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
