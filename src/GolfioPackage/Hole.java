package GolfioPackage;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Hole {
    private Double xPos;
    private Double yPos;
    private int height;
    private int width;
    ImageView holeView;

    public Hole(Double xPos, Double yPos, int height, int width) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.height = height;
        this.width = width;
    }

    public void initialiseBall(Image hole) {
        holeView = new ImageView(hole);
        holeView.setX(xPos);
        holeView.setY(yPos);
        holeView.setFitHeight(height);
        holeView.setFitWidth(width);
        holeView.setPreserveRatio(true);
    }

    public ImageView getView() {
        return holeView;
    }
}
