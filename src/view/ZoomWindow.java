package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Cette classe represente une fenetre de zoom pour afficher une zone plus detaillee du plateau.
 * Elle herite de la classe Stage.
 */
public class ZoomWindow extends Stage {

    private static final int cellSize = 20;
    private static final int zoomSize = 11;
    private Pane thepane ;
    private final Board plateau;
    private int startX , startY;

    /**
     * Constructeur de la classe ZoomWindow.
     * @param board le plateau de jeu a zoomer.
     */
    public ZoomWindow(Board board) {
    	plateau = board;
    	thepane = new Pane();
        Scene scene = new Scene(thepane, cellSize * zoomSize, cellSize * zoomSize);

        // Event si on zoom sur le plateau
        /*
         * on calcule les coordonnees du coin superieur gauche de la fenetre de zoom en soustrayant les coordonnees x et y obtenues precedemment par la moitie de la taille de la fenetre de zoom. Ensuite, 
         * on verifie si les coordonnees calculees ne sortent pas du plateau en comparant avec la largeur et la hauteur du plateau. Si elles sortent, on les ajuste pour qu'elles restent dans les limites 
         * du plateau.Enfin, on appelle la methode updateZoomGrid() pour mettre a jour la fenetre de zoom avec les nouvelles cases a afficher. 
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

        
        //Ajout d'un icon a la fentre 
        Image icon = new Image(getClass().getResourceAsStream("loupe-icon.png"));
        getIcons().add(icon);
        // show la fenetre
        setScene(scene);
        setTitle("Zoom Window");
        setResizable(false);
        show();
    }


    /**
     * Met a jour la grille de zoom pour afficher la zone correspondante du plateau de jeu.
     */
    //   On supprime tous les enfants de thepane, puis on parcourt toutes les cases de la grille correspondant a la 
    // fenetre de zoom et ajoute un rectangle pour chaque case avec la couleur de fond et la couleur de contour 
    // correspondantes. Si une fourmi est presente dans une case, elle est egalement ajoutee.
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