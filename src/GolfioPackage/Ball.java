package GolfioPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class Ball {

    private Double xPos;
    private Double yPos;
    private int height;
    private int width;
    private ImageView ballView;
    private PowerIndicator currentLine;

    public Ball(Double xPos, Double yPos, int height, int width) {
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

    public void createEventHandlers(ImageView ballView) {
        ballView.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            currentLine.initialisePowerIndicator(getX(), getY());
            if(!currentLine.isVisible()) {
                currentLine.setVisible(true);
                currentLine.setEndX(e.getX());
                currentLine.setEndY(e.getY());
            }
        });

        ballView.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (currentLine == null) {
                currentLine.createPowerIndicator(e.getX(), e.getY());
                Main.aPane.getChildren().add(currentLine);
            }  else {
                currentLine.setEndX(e.getX());
                currentLine.setEndY(e.getY());
            }
        });

        ballView.addEventHandler(MouseEvent.MOUSE_RELEASED, ev -> {
            currentLine.setVisible(false);
        });
    }
}