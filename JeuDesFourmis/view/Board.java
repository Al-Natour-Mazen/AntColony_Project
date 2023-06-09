package view;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Fourmi;
import model.Fourmiliere;

public class Board extends Pane {

    private final Fourmiliere antColony;
    private final static int cellSize = 10; // taille par d�faut d'une cellule
	
    private int gridheight;
	private int gridwidth;
	private Rectangle[][] cells;
	private Ant[][] Ants;
	
	 
   /* On d�finie comme final la Property afin de garantir qu'une fois initialis�e dans le constructeur, elle ne peut 
    * plus �tre remplac�e par une autre instance de BooleanProperty. Cela permet d'assurer l'int�grit� 
    * des donn�es de la propri�t� et d'�viter toute modification accidentelle ou malveillante de la r�f�rence de la propri�t�.
    */
	private final BooleanProperty displayGridsProperty;
	private final static boolean DefaultDisplay = true;

	/**
	 * Constructeur de la classe Board.
	 * @param antColony    Une Fourmiliere pour laquelle le plateau sera cr��.
	 */
	public Board(Fourmiliere antColony) {
        this.antColony = antColony;
        this.gridheight = antColony.getHauteur();
        this.gridwidth = antColony.getLargeur();       
        cells = new Rectangle[gridwidth+2][gridheight+2];
        for (int i = 0; i < gridheight+2; i++) {
            for (int j = 0; j < gridwidth+2; j++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.setFill(Color.WHITE);
                cell.setStroke(Color.BLACK);
                cell.setX(j * cellSize);
                cell.setY(i * cellSize);
                cells[j][i] = cell;
                getChildren().add(cell);
            }
        }
        Ants = new Ant[gridheight+2][gridwidth+2];
        displayGridsProperty = new SimpleBooleanProperty(DefaultDisplay);
        updateGrid();
    }

	/**
	 * Met � jour la grille � partir de l'�tat actuel de la Fourmiliere.
	 */
    public void updateGrid() {
        for (int i = 0; i < gridheight+2; i++) {
            for (int j = 0; j < gridwidth+2; j++) {
            	if (antColony.contientFourmi(j, i)) {
            		boolean foundAnt = false;
        		    for (Fourmi f : antColony.getLesFourmis()) {
        		        if (f.getX() == j && f.getY() == i) {
        		            addAnt(j, i, f.porte());
        		            foundAnt = true;
        		            break;
        		        }
        		    }
        		    if (!foundAnt) {
        		        addAnt(j, i, false);  
        		    }
                }else if (antColony.getMur(j, i)) {
                    cells[j][i].setFill(Color.BLACK);
                    removeAnt(j, i); 
                } else if (antColony.getQteGraines(j, i) > 0) {
                	int seedLevel = antColony.getQteGraines(j, i);
                	double ratio = (double) seedLevel / antColony.getQMax();
                	double saturation = 0.5 + (ratio * 0.5); // r�duire la saturation pour les niveaux de graines faibles
                	double brightness = 1.0 - (ratio * 0.5); // augmenter la luminosit� pour les niveaux de graines �lev�s
                	double hue =  460 / (ratio * 180.0); // gradation du vert au rouge
                	cells[j][i].setFill(Color.hsb(hue, saturation, brightness));
                	removeAnt(j, i); 

                } else {
                    cells[j][i].setFill(Color.WHITE);
              	    removeAnt(j, i); 
                }
            	
            	//Pour changer automatiquement les grilles (les afficher ou pas)
            	final int ibis = i;
            	final int jbis = j;
            	displayGridsProperty.addListener((obs, oldVal, newVal) -> {
            		if(newVal) // on les affiche
            			cells[jbis][ibis].setStroke(Color.BLACK);
            		else // sinon on les affiche pas
            			cells[jbis][ibis].setStroke(null);
            	});
            }
        }
    }
    
    /**
     * Remet � z�ro la grille (toutes les cases sont blanches).
     */
    public void resestGrid() {
    	 for (int i = 0; i < gridheight+2; i++){
             for (int j = 0; j < gridwidth+2; j++){
            	    removeAnt(j, i);
                    cells[j][i].setFill(Color.WHITE);   
             }
    	 }
    }
    
    /**
     * Ajoute une fourmi � la case (x, y) et cr�e une instance de la classe Ant � cette position.
     * @param x         Coordonn�e x de la case.
     * @param y         Coordonn�e y de la case.
     * @param Hasseed   Si la fourmi a une graine.
     */
    private void addAnt(int x , int y , boolean Hasseed) {
    	Ant ant = new Ant(cellSize/2,x*cellSize+(cellSize/2),y*cellSize+(cellSize/2), Hasseed);
    	ant.changeColorAnt();
    	Ants[y][x] = ant;
    	this.getChildren().add(ant);
    }
    
    /**
     * Supprime la fourmi de la case (x, y) en parcourant les enfants du Pane et en supprimant la fourmi correspondante.
     * @param x     Coordonn�e x de la case.
     * @param y     Coordonn�e y de la case.
     */
    private void removeAnt(int x, int y) {
        for (Node node : this.getChildren()) {
            if (node instanceof Ant) {
                Ant ant = (Ant) node;
                if (ant.getX() == x * cellSize + (cellSize/2) && ant.getY() == y * cellSize + (cellSize/2)) {
                	Ants[y][x] = null;
                    this.getChildren().remove(ant);
                    break;
                }
            }
        }
    }
    
	//////////////////////
	// SETTERS/GETTERS 
	//
    public int getGridheight() {
		return gridheight;
	}

	public int getGridwidth() {
		return gridwidth;
	}
	
	public Rectangle getcell(int x,int y) {
		return cells[y][x];
	}
	public Ant getAntCell(int x,int y) {
		return Ants[y][x];
	}
	
    public int getCellSize() {
		return cellSize;
	}
    
    public BooleanProperty displayGridsProperty() {
        return displayGridsProperty;
    }

    public Boolean getdisplayGrids() {
        return displayGridsProperty.get();
    }

    public void setdisplayGrids(Boolean value) {
    	displayGridsProperty.set(value);
    }
    
}