package GolfioPackage;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.animation.Interpolator;
import java.util.ArrayList;

public class Ball {

    private double xPos;
    private double yPos;
    private double startX;
    private double startY;
    private int height;
    private int width;
    private ImageView ballView;
    private Timeline timeline;
    private double maxOffset;
    private ArrayList<Line> arrayLines = new ArrayList<Line>();
    public Line currentLine;

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
                   // currentLine.setDisable(false);
                  //  currentLine.setVisible(true);
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
            if(timeline == null){
                createPaneAnimations();
            }
        });

        ballView.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
                //   currentLine.setDisable(true);
                //  currentLine.setVisible(false);
        });
    }

    public void createPowerIndicator(double x, double y) {
        this.currentLine = new Line(startX, startY, x, y);
        arrayLines.add(currentLine);
        Main.aPane.getChildren().add(currentLine);
    }

    public void createPaneAnimations(){
        maxOffset = currentLine.getStrokeDashArray().stream().reduce(0d, (a, b) -> a + b);
        timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(currentLine.strokeDashOffsetProperty(), 0, Interpolator.LINEAR)),
        new KeyFrame(Duration.seconds(50), new KeyValue(currentLine.strokeDashOffsetProperty(), maxOffset, Interpolator.LINEAR)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}