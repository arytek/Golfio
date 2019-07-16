package GolfioPackage;

import javafx.scene.shape.Line;
import java.util.ArrayList;

public class Level {

    private static ArrayList<Line> horizontalLines = new ArrayList<>();
    private static ArrayList<Line> verticalLines = new ArrayList<>();

    public Level() {
        Border border1 = new Border(64.0, 64.0, 895.0, 64.0);
        Border border2 = new Border(64.0, 64.0, 64.0, 894.0);
        Border border3 = new Border(64.0, 895.0, 895.0, 895.0);
        Border border4 = new Border(895.0, 895.0, 895.0, 608.0);
        Border border5 = new Border(895.0, 608.0, 350.0, 608.0);
        Border border6 = new Border(351.0, 608.0, 351.0, 319.0);
        Border border7 = new Border(351.0, 319.0, 640.0, 319.0);
        Border border8 = new Border(640.0, 319.0, 640.0, 480.0);
        Border border9 = new Border(640.0, 480.0, 895.0, 480.0);
        Border border10 = new Border(895.0, 480.0, 895.0, 64.0);
    }

    public void addBordersToPane() {
        Main.aPane.getChildren().addAll(horizontalLines);
        Main.aPane.getChildren().addAll(verticalLines);
    }

    /**  Getter and Setters **/
    public static ArrayList<Line> getHorizontalLines() {
        return horizontalLines;
    }

    public void setHorizontalLines(ArrayList<Line> horizontalLines) {
        this.horizontalLines = horizontalLines;
    }

    public static ArrayList<Line> getVerticalLines() {
        return verticalLines;
    }

    public void setVerticalLines(ArrayList<Line> verticalLines) {
        this.verticalLines = verticalLines;
    }
}
