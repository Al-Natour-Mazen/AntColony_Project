package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Cette classe repr�sente une fen�tre de zoom pour afficher une zone plus d�taill�e d'un plateau de jeu.
 * Elle h�rite de la classe Stage de JavaFX.
 */
public class ZoomWindow extends Stage {

    private final int cellSize = 20;
    private final int zoomSize = 11;
    private Pane thepane ;
    private final Board plateau;
    private int startX , startY;

    /**
     * Constructeur de la classe ZoomWindow.
     * @param board le plateau de jeu � zoomer.
     */
    public ZoomWindow(Board board) {
    	plateau = board;
    	thepane = new Pane();
        Scene scene = new Scene(thepane, cellSize * zoomSize, cellSize * zoomSize);

        // Event si on zoom sur le plateau
        /*
         * on calcule les coordonn�es du coin sup�rieur gauche de la fen�tre de zoom en soustrayant les coordonn�es x et y obtenues pr�c�demment par la moiti� de la taille de la fen�tre de zoom. Ensuite, 
         * on v�rifie si les coordonn�es calcul�es ne sortent pas du plateau en comparant avec la largeur et la hauteur du plateau. Si elles sortent, on les ajuste pour qu'elles restent dans les limites 
         * du plateau.Enfin, on appelle la m�thode updateZoomGrid() pour mettre � jour la fen�tre de zoom avec les nouvelles cases � afficher. 
         */
        plateau.setOnMouseMoved(event -> {
        	double mouseX = event.getX();
        	double mouseY = event.getY();
        	double boardX = mouseX / board.getCellSize();
        	double boardY = mouseY / board.getCellSize();
        	int zoomX = (int) (boardX - (zoomSize - 1) / 2);
        	int zoomY = (int) (boardY - (zoomSize - 1) / 2);
    	    if (zoomX < 0) {
    	        zoomX = 0;
    	    } else if (zoomX > plateau.getGridwidth() - zoomSize) {
    	        zoomX = plateau.getGridwidth() - zoomSize +2;
    	    }

    	    if (zoomY < 0) {
    	        zoomY = 0;
    	    } else if (zoomY > plateau.getGridheight() - zoomSize) {
    	        zoomY = plateau.getGridheight() - zoomSize+2;
    	    }

        	startX = zoomX;
        	startY = zoomY;
        	updateZoomGrid();
        });

        // show la fenetre
        setScene(scene);
        setTitle("Zoom Window");
        setResizable(false);
        show();
    }


    /**
     * Met � jour la grille de zoom pour afficher la zone correspondante du plateau de jeu.
     */
    //   On supprime tous les enfants de thepane, puis on parcourt toutes les cases de la grille correspondant � la 
    // fen�tre de zoom et ajoute un rectangle pour chaque case avec la couleur de fond et la couleur de contour 
    // correspondantes. Si une fourmi est pr�sente dans une case, elle est �galement ajout�e.
    public void updateZoomGrid() {
    	thepane.getChildren().clear();
        for (int x = startX;  x < plateau.getGridwidth() + 2; x++) {
            for (int y = startY; y < plateau.getGridheight() + 2; y++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.setFill(plateau.getcell(y, x).getFill());
                cell.setStroke(plateau.getcell(y, x).getStroke());
                cell.setX((x - startX) * cellSize);
                cell.setY((y - startY) * cellSize);
                thepane.getChildren().add(cell);
                Ant antplateau = plateau.getAntCell(x, y);
                if(antplateau != null){
                    Ant antView = new Ant(cellSize/2, (x - startX) * cellSize + cellSize/2, (y - startY) * cellSize + cellSize/2, antplateau.getHasSeed());
                    antView.changeColorAnt();
                    thepane.getChildren().add(antView);
                }                
            }
        }
    }

}