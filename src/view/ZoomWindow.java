package view;


import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Cette classe repr�sente une fen�tre de zoom pour afficher une zone plus d�taill�e du plateau.
 * Elle h�rite de la classe Stage.
 */
public class ZoomWindow extends Stage {

    private static final int cellSize = 30;
    private static final int zoomSize = 11;
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
        	double boardX = event.getX() / board.getCellSize();
        	double boardY = event.getY() / board.getCellSize();
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
    	    
    	    if (zoomX != startX || zoomY != startY) {
                startX = zoomX;
                startY = zoomY;
                updateZoomGrid();
            }
        });

        
        //Ajout d'un icon � la fentre 
        Image icon = new Image(getClass().getResourceAsStream("loupe-icon.png"));
        getIcons().add(icon);
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
    	int gridWidth = plateau.getGridwidth() + 2;
    	int gridHeight = plateau.getGridheight() + 2;
        for (int x = startX;  x < gridWidth; x++) {
            for (int y = startY; y < gridHeight; y++) {
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