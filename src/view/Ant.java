package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ant extends Circle{
	
	private Boolean HasSeed = false ;
	
	/**
	   * Construction  d'une fourmi au point (x,y) du terrain
	   * @param taille  le rayon de la fourmi
	   * @param x		coordonnee
	   * @param y		abcisse
	   * @param hasseed  vrai si la fourmi porte une graine
	   * @return		 une fourmi
	   */
	public Ant(int taille,int x , int y, boolean hasseed) {
		// TODO Auto-generated constructor stub
		super(taille);
		setLayoutX(x);
		setLayoutY(y);
		setHasSeed(hasseed);
		changeColorAnt();
	}

	public Boolean getHasSeed() {
		return HasSeed;
	}

	public void setHasSeed(Boolean hasSeed) {
		HasSeed = hasSeed;
	}
	
	public void changeColorAnt() {
		if(HasSeed) {
			this.setFill(Color.BLUE);
		}
		else
			this.setFill(Color.GREEN);
	}

}
