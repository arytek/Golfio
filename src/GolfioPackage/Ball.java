package GolfioPackage;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class Ball extends Circle {

    public Line currentLine;
    public double amountToBounceX;
    public double amountToBounceY;
    public Timeline launchBallTL;
    public Timeline relaunchBallTL;
    public boolean didReboundLeftRight = false;
    public boolean didReboundTopBottom = false;
    public double reboundDelta = 1;

    public Ball(double radius, Color color) {
        super(radius, color);
    }

    public void initialiseBall(String userData, double xPosition, double yPosition, Image image) {
        this.setUserData(userData);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
        this.setFill(new ImagePattern(image));
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
                (ActionEvent t) -> {

                    final Bounds bounds = Main.aPane.getBoundsInLocal();
                    final boolean atRightBorder = getCircle().getLayoutX() >= (bounds.getMaxX() - getCircle().getRadius());
                    final boolean atLeftBorder = getCircle().getLayoutX() <= (bounds.getMinX() + getCircle().getRadius());
                    final boolean atBottomBorder = getCircle().getLayoutY() >= (bounds.getMaxY() - getCircle().getRadius());
                    final boolean atTopBorder = getCircle().getLayoutY() <= (bounds.getMinY() + getCircle().getRadius());

                    if (atRightBorder || atLeftBorder) {
                        relaunchBall((getCircle().getLayoutX() + (amountToBounceX * Main.reboundFactor)),
                                (getCircle().getLayoutY() - (amountToBounceY * Main.reboundFactor)));
                        didReboundLeftRight = true;
                        reboundDelta += 0.1;

                        if (didReboundTopBottom) {
                            relaunchBall((getCircle().getLayoutX() + (amountToBounceX / reboundDelta) * Main.reboundFactor),
                                    (getCircle().getLayoutY() - (amountToBounceY / reboundDelta) * Main.reboundFactor));
                            didReboundLeftRight = false;
                        }
                    }

                    if (atBottomBorder || atTopBorder) {
                        relaunchBall((getCircle().getLayoutX() - (amountToBounceX * Main.reboundFactor)),
                                (getCircle().getLayoutY() + (amountToBounceY * Main.reboundFactor)));
                        didReboundTopBottom = true;
                        reboundDelta += 0.1;

                        if (didReboundLeftRight) {
                            relaunchBall((getCircle().getLayoutX() - (amountToBounceX / reboundDelta) * Main.reboundFactor),
                                    (getCircle().getLayoutY() + (amountToBounceY / reboundDelta) * Main.reboundFactor));
                            didReboundTopBottom = false;
                        }
                    }
                }));
        wallCollisionTL.setCycleCount(Timeline.INDEFINITE);
        wallCollisionTL.play();
    }

    public void relaunchBall(double reboundX, double reboundY) {
        relaunchBallTL = new Timeline();
        relaunchBallTL.setCycleCount(1);

        KeyValue ballNewX = new KeyValue(this.layoutXProperty(), reboundX, Interpolator.EASE_OUT);
        KeyValue ballNewY = new KeyValue(this.layoutYProperty(), reboundY, Interpolator.EASE_OUT);

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
        reboundDelta = 2;

        KeyValue ballNewX = new KeyValue(this.layoutXProperty(), newX, Interpolator.EASE_OUT);
        KeyValue ballNewY = new KeyValue(this.layoutYProperty(), newY, Interpolator.EASE_OUT);

        KeyFrame keyFrameEnd = new KeyFrame(Duration.seconds(3), ballNewX, ballNewY);

        launchBallTL.getKeyFrames().addAll(keyFrameEnd);
        launchBallTL.play();

        double X1 = this.getLayoutX();
        double Y1 = this.getLayoutY();
        double radius1 = this.getRadius();
        double X2 = 500;
        double Y2 = 800;
        double radius2 = 15;
        double distance = Math.pow((X1 - X2) * (X1 - X2) + (Y1 - Y2) * (Y1 - Y2), 0.5);
        if(launchBallTL.getStatus() == Animation.Status.STOPPED)  {
            if (radius2 >= radius1 && distance <= (radius2 - radius1)) {
                System.out.println("Circle 1 is inside Circle 2.");
            } else if (radius1 >= radius2 && distance <= (radius1 - radius2)) {
                System.out.println("Circle 2 is inside Circle 1.");
            } else if (distance > (radius1 + radius2)) {
                System.out.println("Circle 2 does not overlap Circle 1.");
            } else {
                System.out.println("Circle 2 overlaps Circle 1.");
            }
        }
    }
}
