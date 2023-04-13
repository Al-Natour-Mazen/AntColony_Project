package view;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Fourmi;
import model.Fourmiliere;

public class Board extends Pane {

    private final Fourmiliere antColony;
 
	private int gridheight;
	private int gridwidth;
    private final static int cellSize = 10; // taille par défaut d'une cellule
	private Rectangle[][] cells;
	private Ant[][] Ants;

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
        updateGrid();
    }

    
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
                } else if (antColony.getQteGraines(j, i) > 0) {
                	int seedLevel = antColony.getQteGraines(j, i);
                	double ratio = (double) seedLevel / antColony.getQMax();
                	double saturation = 0.5 + (ratio * 0.5); // réduire la saturation pour les niveaux de graines faibles
                	double brightness = 1.0 - (ratio * 0.5); // augmenter la luminosité pour les niveaux de graines élevés
                	double hue =  460 / (ratio * 180.0); // gradation du vert au rouge
                	cells[j][i].setFill(Color.hsb(hue, saturation, brightness));
                	removeAnt(j, i); 

                } else {
                    cells[j][i].setFill(Color.WHITE);
              	    removeAnt(j, i); 
                }
            }
        }
    }
    
    public void resestGrid() {
    	 for (int i = 0; i < gridheight+2; i++){
             for (int j = 0; j < gridwidth+2; j++){
                     cells[j][i].setFill(Color.WHITE);   
             }
    	 }
    }
    
    private void addAnt(int x , int y , boolean Hasseed) {
    	Ant ant = new Ant(cellSize/2,x*cellSize+(cellSize/2),y*cellSize+(cellSize/2), Hasseed);
    	ant.changeColorAnt();
    	Ants[y][x] = ant;
    	this.getChildren().add(ant);
    }
    
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
		return Ants[x][y];
	}
	
    public int getCellSize() {
		return cellSize;
	}
}