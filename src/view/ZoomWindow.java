package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ZoomWindow extends Stage {

    private final int cellSize = 20;
    private final int zoomSize = 11;
    private Pane thepane ;
    private final Board plateau;
    private int startX , startY;

    public ZoomWindow(Board board) {
    	plateau = board;
    	thepane = new Pane();
        Scene scene = new Scene(thepane, cellSize * zoomSize, cellSize * zoomSize);

        // add zoom rectangle to pane
        Rectangle zoomRect = new Rectangle(0, 0, cellSize * zoomSize, cellSize * zoomSize);
        zoomRect.setFill(Color.TRANSPARENT);
        zoomRect.setStroke(Color.BLACK);
        thepane.getChildren().add(zoomRect);

        // handle mouse events
        board.setOnMouseMoved(event -> {
        	double mouseX = event.getX();
        	double mouseY = event.getY();
        	double boardX = mouseX / board.getCellSize();
        	double boardY = mouseY / board.getCellSize();
        	int zoomX = (int) (boardX - (zoomSize - 1) / 2);
        	int zoomY = (int) (boardY - (zoomSize - 1) / 2);
    	    if (zoomX < 0) {
    	        zoomX = 0;
    	    } else if (zoomX > board.getGridwidth() - zoomSize) {
    	        zoomX = board.getGridwidth() - zoomSize +2;
    	    }

    	    if (zoomY < 0) {
    	        zoomY = 0;
    	    } else if (zoomY > board.getGridheight() - zoomSize) {
    	        zoomY = board.getGridheight() - zoomSize+2;
    	    }
        	zoomRect.setX(zoomX * cellSize);
        	zoomRect.setY(zoomY * cellSize);
        	startX = zoomX;
        	startY = zoomY;
        	updateZoomGrid();
        });

        // show the window
        setScene(scene);
        setTitle("Zoom Window");
        setResizable(false);
        show();
    }


            
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