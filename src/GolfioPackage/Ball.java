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
    public double amountToBounceX;
    public double amountToBounceY;
    public Timeline launchBallTL;

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
            launchBall(e);
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

    public void createWallCollisionListener() {
        /* Timeline with no KeyValue, used to create infinite loop, wherein the
        onFinished (second parameter of KryFrame) event handler is called */
        Timeline wallCollisionTL = new Timeline(new KeyFrame(Duration.millis(10),
            new EventHandler<ActionEvent>() {
                    double deltaX  = -1; //Step on x or velocity
                    double deltaY  = -1; //Step on y

                    @Override
                    public void handle(final ActionEvent t) {

                        final Bounds bounds = Main.aPane.getBoundsInLocal();
                        final boolean atRightBorder = getCircle().getLayoutX() >= (bounds.getMaxX() - getCircle().getRadius());
                        final boolean atLeftBorder = getCircle().getLayoutX() <= (bounds.getMinX() + getCircle().getRadius());
                        final boolean atBottomBorder = getCircle().getLayoutY() >= (bounds.getMaxY() - getCircle().getRadius());
                        final boolean atTopBorder = getCircle().getLayoutY() <= (bounds.getMinY() + getCircle().getRadius());

                        if (atRightBorder || atLeftBorder) {
                            launchBallTL.stop();
                            relaunchBall(((getCircle().getLayoutX() + amountToBounceX)), ((getCircle().getLayoutY() - amountToBounceY)));
                        }
                        if (atBottomBorder || atTopBorder) {
                            launchBallTL.stop();
                            relaunchBall(((getCircle().getLayoutX() - amountToBounceX)), ((getCircle().getLayoutY() + amountToBounceY)));
                        }
                    }
                }));
        wallCollisionTL.setCycleCount(Timeline.INDEFINITE);
        wallCollisionTL.play();
    }

    public void relaunchBall(double reboundX, double reboundY) {
        Timeline relaunchBallTL = new Timeline();
        relaunchBallTL.setCycleCount(1);

        KeyValue ballNewX = new KeyValue(this.layoutXProperty(), reboundX, Interpolator.LINEAR);
        KeyValue ballNewY = new KeyValue(this.layoutYProperty(), reboundY, Interpolator.LINEAR);

        KeyFrame keyFrameEnd = new KeyFrame(Duration.seconds(3), ballNewX, ballNewY);

        relaunchBallTL.getKeyFrames().addAll(keyFrameEnd);
        relaunchBallTL.play();
    }

    public void launchBall(MouseEvent e) {
        launchBallTL = new Timeline();
        launchBallTL.setCycleCount(1);

        double newX = (this.getLayoutX() + (e.getX() *-1));
        double newY = (this.getLayoutY() + (e.getY() *-1));

        amountToBounceX = e.getX();
        amountToBounceY = e.getY();

        KeyValue ballNewX = new KeyValue(this.layoutXProperty(), newX, Interpolator.LINEAR);
        KeyValue ballNewY = new KeyValue(this.layoutYProperty(), newY, Interpolator.LINEAR);

        KeyFrame keyFrameEnd = new KeyFrame(Duration.seconds(3), ballNewX, ballNewY);

        launchBallTL.getKeyFrames().addAll(keyFrameEnd);
        launchBallTL.play();
    }
}
