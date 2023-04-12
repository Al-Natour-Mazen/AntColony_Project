package view;



import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ZoomWindow extends Stage {

    private final int cellSize = 20;
    private final int zoomSize = 11;

    public ZoomWindow(Board board) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, cellSize * zoomSize, cellSize * zoomSize);

        // add zoom rectangle to pane
        Rectangle zoomRect = new Rectangle(0, 0, cellSize * zoomSize, cellSize * zoomSize);
        zoomRect.setFill(Color.TRANSPARENT);
        zoomRect.setStroke(Color.BLACK);
        pane.getChildren().add(zoomRect);

        // handle mouse events
        board.setOnMouseMoved(event -> {
            int x = (int) event.getX() / cellSize;
            int y = (int) event.getY() / cellSize;
            int zoomX = Math.max(0, Math.min(board.getGridwidth() - zoomSize, x - zoomSize / 2));
            int zoomY = Math.max(0, Math.min(board.getGridheight() - zoomSize, y - zoomSize / 2));
            zoomRect.setX(zoomX * cellSize);
            zoomRect.setY(zoomY * cellSize);
            updateZoomGrid(board, pane, zoomX, zoomY);
        });

        // show the window
        setScene(scene);
        setTitle("Zoom Window");
        show();
    }

    private void updateZoomGrid(Board board, Pane pane, int startX, int startY) {
        pane.getChildren().clear();
        for (int x = startX; x < startX + zoomSize; x++) {
            for (int y = startY; y < startY + zoomSize; y++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.setFill(board.getcell(x, y).getFill());
                cell.setStroke(board.getcell(x, y).getStroke());
                cell.setX((x - startX) * cellSize);
                cell.setY((y - startY) * cellSize);
                pane.getChildren().add(cell);
            }
        }
    }
}

