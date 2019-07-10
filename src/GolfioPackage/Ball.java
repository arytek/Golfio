package GolfioPackage;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class Ball extends Circle {

    public Line currentLine;

    public Ball(double radius, Color color) {
        super(radius, color);
    }

    public void initialiseBall(String userData, double xPosition, double yPosition) {
        this.setUserData(userData);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
        createEventHandlers();
    }

    public Circle getCircle() {
        return this;
    }

    public void createEventHandlers() {
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                if(!(currentLine == null)){
                    createPowerIndicator(this.getLayoutX(), this.getLayoutY());
                    createPowerIndicatorAnimation();
                }
        });

        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (currentLine == null) {
                createPowerIndicator(this.getLayoutX(), this.getLayoutY());
            }  else {
                createPowerIndicatorAnimation();
                currentLine.setEndX(e.getSceneX());
                currentLine.setEndY(e.getSceneY());
            }
        });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            Main.aPane.getChildren().remove(currentLine);
        });
    }

    public void createPowerIndicator(double x, double y) {
        this.currentLine = new Line(this.getLayoutX(), this.getLayoutY(), x, y);
        Main.aPane.getChildren().add(currentLine);
    }

    public void createPowerIndicatorAnimation() {
        currentLine.getStrokeDashArray().addAll(10d, 10d, 10d, 10d);
        double maxOffset = currentLine.getStrokeDashArray().stream().reduce(0d, (a, b) -> a + b);
        Timeline powerIndicatorTL = new Timeline();
        powerIndicatorTL.setCycleCount(Timeline.INDEFINITE);
        powerIndicatorTL.setAutoReverse(true);

        KeyFrame keyFrameOne = new KeyFrame(Duration.ZERO, new KeyValue(currentLine.strokeDashOffsetProperty(), 0, Interpolator.LINEAR));
        KeyFrame keyFrameTwo = new KeyFrame(Duration.seconds(50), new KeyValue(currentLine.strokeDashOffsetProperty(), maxOffset, Interpolator.LINEAR));

        // Add the keyframe to the powerIndicator timeline.
        powerIndicatorTL.getKeyFrames().addAll(keyFrameOne, keyFrameTwo);
        powerIndicatorTL.play();
    }

    public void createBallMovementAnimation(){
        Timeline ballMovementTL = new Timeline(new KeyFrame(Duration.millis(20),
                new EventHandler<ActionEvent>() {

                    double deltaX  = 1; //Step on x or velocity
                    double deltaY  = 1; //Step on y

                    @Override
                    public void handle(final ActionEvent t) {

                        getCircle().setLayoutX(getCircle().getLayoutX() + deltaX);
                        getCircle().setLayoutY(getCircle().getLayoutY() + deltaY);

                        final Bounds bounds = Main.aPane.getBoundsInLocal();
                        final boolean atRightBorder = getCircle().getLayoutX() >= (bounds.getMaxX() - getCircle().getRadius());
                        final boolean atLeftBorder = getCircle().getLayoutX() <= (bounds.getMinX() + getCircle().getRadius());
                        final boolean atBottomBorder = getCircle().getLayoutY() >= (bounds.getMaxY() - getCircle().getRadius());
                        final boolean atTopBorder = getCircle().getLayoutY() <= (bounds.getMinY() + getCircle().getRadius());

                        if (atRightBorder || atLeftBorder) {
                            deltaX *= -1;
                        }
                        if (atBottomBorder || atTopBorder) {
                            deltaY *= -1;
                        }
                    }
                }));
        ballMovementTL.setCycleCount(Timeline.INDEFINITE);
        ballMovementTL.play();
    }
}
