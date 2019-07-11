package GolfioPackage;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Hole extends Circle {

    public Hole(double radius, Color color) {
        super(radius, color);
    }

    public void initialiseHole(String userData, double xPosition, double yPosition) {
        this.setUserData(userData);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }

    public Circle getCircle() {
        return this;
    }
}
