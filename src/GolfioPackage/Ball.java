package GolfioPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import java.awt.BasicStroke;

public class Ball {

    private double xPos;
    private double yPos;
    private double startX;
    private double startY;
    private int height;
    private int width;
    private ImageView ballView;
    private Line currentLine;

    public Ball(double xPos, double yPos, int height, int width) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.height = height;
        this.width = width;
    }

    public void initialiseBall(Image ball) {
        ballView = new ImageView(ball);
        ballView.setX(xPos);
        ballView.setY(yPos);
        ballView.setFitHeight(height);
        ballView.setFitWidth(width);
        ballView.setPreserveRatio(true);
        createEventHandlers();
    }

    public ImageView getView() {
        return ballView;
    }

    public Double getX() {
        return xPos + 10;
    }

    public Double getY() {
        return yPos + 10;
    }

    public void createEventHandlers() {
        ballView.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                startX = ballView.getX() + 10;
                startY = ballView.getY() + 10;
                if(!(currentLine == null)){
                    currentLine.setVisible(true);
                    currentLine.getStrokeDashArray().addAll(10d, 10d, 10d, 10d);
                    currentLine.setEndX(e.getX());
                    currentLine.setEndY(e.getY());
                }
        });

        ballView.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (currentLine == null) {
                createPowerIndicator(this.getX(), this.getY());
            }  else {
                currentLine.getStrokeDashArray().addAll(10d, 10d, 10d, 10d);
                currentLine.setEndX(e.getX());
                currentLine.setEndY(e.getY());
            }
        });

        ballView.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            currentLine.setVisible(false);
        });
    }

    public void createPowerIndicator(double x, double y) {
        this.currentLine = new Line(startX, startY, x, y);
        Main.aPane.getChildren().add(currentLine);
    }

}