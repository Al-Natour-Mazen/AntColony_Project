package view;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * Cette classe repr�sente un Label avec un texte et une propri�t� double bind�e pour afficher une valeur num�rique.
*/
public class LabelWithBind extends Label{

	private String labelText;
	
	/**
	 * Constructeur qui prend en param�tre le texte du label et la propri�t� double bind�e.
	 * @param text le texte � afficher sur le label.
	 * @param prop la propri�t� double � bind�e avec le label.
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
    	 * Constructeur qui prend en param�tre la propri�t� double � bind�e.
    	 * @param theProp la propri�t� double � bind�e avec le label.
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
