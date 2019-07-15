package GolfioPackage;

import javafx.scene.shape.Line;
import java.util.ArrayList;

public class Border extends Level {

    public Border(double startX, double startY, double endX, double endY) {
        determineOrientation(startX, startY, endX, endY);
    }

    private void determineOrientation(double startX, double startY, double endX, double endY) {
        Line border = new Line(startX, startY, endX, endY);
        if (startX == endX) {
            addHorizontal(border);
        } else if (startY == endY) {
            addVertical(border);
        }
    }

    private void addHorizontal(Line border) {
        super.getHorizontalLines().add(border);
    }

    private void addVertical(Line border) {
        super.getVerticalLines().add(border);
    }
}
