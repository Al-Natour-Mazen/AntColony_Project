package view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Fourmi;

public class ZoomWindow extends Stage {

    private final int cellSize = 20;
    private final int zoomSize = 11;
    private  Pane pane ;

    public ZoomWindow(Board board) {
    	pane = new Pane();
        Scene scene = new Scene(pane, cellSize * zoomSize, cellSize * zoomSize);

        // add zoom rectangle to pane
        Rectangle zoomRect = new Rectangle(0, 0, cellSize * zoomSize, cellSize * zoomSize);
        zoomRect.setFill(Color.TRANSPARENT);
        zoomRect.setStroke(Color.BLACK);
        pane.getChildren().add(zoomRect);

        // handle mouse events
        /*board.setOnMouseMoved(event -> {
            int x = (int) event.getX() / board.getCellSize();
            int y = (int) event.getY() / board.getCellSize() ;
            int zoomX = Math.max(0, Math.min(board.getGridwidth() - zoomSize, x - zoomSize / 2));
            int zoomY = Math.max(0, Math.min(board.getGridheight() - zoomSize, y - zoomSize / 2));
            zoomRect.setX(zoomX * cellSize);
            zoomRect.setY(zoomY * cellSize);
            updateZoomGrid(board, pane, zoomX, zoomY);
        });*/
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
        	updateZoomGrid(board, pane, zoomX, zoomY);
        	});

        // show the window
        setScene(scene);
        setTitle("Zoom Window");
        setResizable(false);
        show();
    }

  /*  private void updateZoomGrid(Board board, Pane pane, int startX, int startY) {
        pane.getChildren().clear();
        for (int x = startX; x < startX + zoomSize + 2 ; x++) {
            for (int y = startY; y < startY + zoomSize + 2; y++) {
            
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.setFill(board.getcell(y, x).getFill());
                cell.setStroke(board.getcell(y, x).getStroke());
                cell.setX((x - startX) * cellSize);
                cell.setY((y - startY) * cellSize);
               /* for (Fourmi n : board.getAntsList()) {
                     if (n.getX() == y && n.getY() == x) {
                    	 
                }*/
               /* for (Node node : board.getChildren()) {
                    if (node instanceof Ant) {
                        Ant ant = (Ant) node;
                        if (ant.getX() == x  && ant.getY() == y ) {
                        	Circle antCircle = new Circle(cellSize / 2);
                            antCircle.setFill(Color.BLACK);
                            antCircle.setStroke(Color.WHITE);
                            antCircle.setStrokeWidth(2);
                            antCircle.setTranslateX(cell.getX() + cellSize / 2);
                            antCircle.setTranslateY(cell.getY() + cellSize / 2);
                            pane.getChildren().add(antCircle);
                            break;
                        }
                    }
                }*/
               /* 
                pane.getChildren().add(cell);
               }
              }
            }
        */
            
    private void updateZoomGrid(Board board, Pane pane, int startX, int startY) {
        pane.getChildren().clear();
        for (int x = startX;  x < board.getGridwidth() + 2; x++) {
            for (int y = startY; y < board.getGridheight() + 2; y++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.setFill(board.getcell(y, x).getFill());
                cell.setStroke(board.getcell(y, x).getStroke());
                cell.setX((x - startX) * cellSize);
                cell.setY((y - startY) * cellSize);
                pane.getChildren().add(cell);
            }
        }
    }


}