package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ant extends Circle{
	
	private Boolean HasSeed ;
	private int x,y;
	
	/**
	   * Construction  d'une fourmi au point (x,y) du terrain
	   * @param taille  le rayon de la fourmi
	   * @param x		coordonnee
	   * @param y		abcisse
	   * @param hasseed  vrai si la fourmi porte une graine
	   */
	public Ant(int taille,int x , int y, boolean hasseed) {
		// TODO Auto-generated constructor stub
		super(taille);
		setLayoutX(x);
		setLayoutY(y);
		setHasSeed(hasseed);
		setX(x);
		setY(y);
		changeColorAnt();
	}
	
	/**
	* Change la couleur de la fourmi selon si elle porte une graine ou non
	*/
	public void changeColorAnt() {
		if(HasSeed) {
			this.setFill(Color.BLUE);
		}
		else
			this.setFill(Color.GREEN);
	}
	
	//////////////////////
	// SETTERS/GETTERS 
	//

	public Boolean getHasSeed() {
		return HasSeed;
	}

	public void setHasSeed(Boolean hasSeed) {
		HasSeed = hasSeed;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
