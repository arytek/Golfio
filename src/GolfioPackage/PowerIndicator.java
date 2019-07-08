package GolfioPackage;

import javafx.scene.shape.Line;

public class PowerIndicator extends Line {

        private double startX;
        private double startY;

    public PowerIndicator(double startX, double startY) {
            this.startX = startX;
            this.startY = startY;
    }

    public void initialisePowerIndicator(double startX, double startY) {
        this.startX = startX;
        this.startY = startY;
    }

    public void createPowerIndicator(double x, double y) {
        Line currentLine = new Line(startX, startY, x, y);
        Main.aPane.getChildren().add(currentLine);
    }
}
