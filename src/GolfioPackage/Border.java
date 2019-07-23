package GolfioPackage;

import javafx.scene.shape.Line;

public class Border {

    public Border(double startX, double startY, double endX, double endY) {
        determineOrientation(startX, startY, endX, endY);
    }

    private void determineOrientation(double startX, double startY, double endX, double endY) {
        Line border = new Line(startX, startY, endX, endY);
        if (startX == endX) {
            addVertical(border);
        } else if (startY == endY) {
            addHorizontal(border);
        }
    }

    private void addHorizontal(Line border) {
        Level.getHorizontalLines().add(border);
    }

    private void addVertical(Line border) {
        Level.getVerticalLines().add(border);
    }
}
