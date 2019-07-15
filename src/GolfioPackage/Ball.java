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

    private Line currentLine;
    private double amountToBounceX;
    private double amountToBounceY;
    private double lastPosX;
    private double lastPosY;
    private WallCollisionEvent wallCollisionEvent;
    private boolean didReboundLeftRight = false;
    private boolean didReboundTopBottom = false;
    private boolean doingRebound = false;
    private double reboundDelta = 1.0;

    public Ball(double radius, Color color) {
        super(radius, color);
    }

    public void initialiseBall(String userData, double xPosition, double yPosition, Image image) {
        this.setUserData(userData);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
        this.setFill(new ImagePattern(image));
        wallCollisionEvent = new WallCollisionEvent(this);
        wallCollisionEvent.addEventHandler();
        createEventHandlers();
    }

    /**  Setters and Getters */
    public Ball getCircle() {
        return this;
    }

    public Line getCurrentLine() {
        return currentLine;
    }

    public void setCurrentLine(Line currentLine) {
        this.currentLine = currentLine;
    }

    public double getAmountToBounceX() {
        return amountToBounceX;
    }

    public void setAmountToBounceX(double amountToBounceX) {
        this.amountToBounceX = amountToBounceX;
    }

    public double getAmountToBounceY() {
        return amountToBounceY;
    }

    public void setAmountToBounceY(double amountToBounceY) {
        this.amountToBounceY = amountToBounceY;
    }

    public double getLastPosX() {
        return lastPosX;
    }

    public void setLastPosX(double lastPosX) {
        this.lastPosX = lastPosX;
    }

    public double getLastPosY() {
        return lastPosY;
    }

    public void setLastPosY(double lastPosY) {
        this.lastPosY = lastPosY;
    }

    public WallCollisionEvent getWallCollisionEvent() {
        return wallCollisionEvent;
    }

    public void setWallCollisionEvent(WallCollisionEvent wallCollisionEvent) {
        this.wallCollisionEvent = wallCollisionEvent;
    }

    public boolean getDidReboundLeftRight() {
        return didReboundLeftRight;
    }

    public void setDidReboundLeftRight(boolean didReboundLeftRight) {
        this.didReboundLeftRight = didReboundLeftRight;
    }

    public boolean getDidReboundTopBottom() {
        return didReboundTopBottom;
    }

    public void setDidReboundTopBottom(boolean didReboundTopBottom) {
        this.didReboundTopBottom = didReboundTopBottom;
    }

    public boolean isDoingRebound() {
        return doingRebound;
    }

    public void setDoingRebound(boolean doingRebound) {
        this.doingRebound = doingRebound;
    }

    public double getReboundDelta() {
        return reboundDelta;
    }

    public void setReboundDelta(double reboundDelta) {
        this.reboundDelta = reboundDelta;
    }

    public void incrementReboundDelta(double reboundDelta) {
        this.reboundDelta += reboundDelta;
    }

    private void createEventHandlers() {
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
            resetBallProperties();
            launchBall(e);
            Main.aPane.getChildren().remove(currentLine);
        });
    }

    private void createPowerIndicator(double x, double y) {
        this.currentLine = new Line(this.getLayoutX(), this.getLayoutY(), x, y);
        Main.aPane.getChildren().add(currentLine);
    }

    private void createPowerIndicatorAnimation() {
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

    public void relaunchBall(double reboundX, double reboundY) {
        Timeline relaunchBallTL = new Timeline();
        relaunchBallTL.setCycleCount(1);

        KeyValue ballNewX = new KeyValue(this.layoutXProperty(), reboundX, Interpolator.EASE_OUT);
        KeyValue ballNewY = new KeyValue(this.layoutYProperty(), reboundY, Interpolator.EASE_OUT);

        KeyFrame keyFrameEnd = new KeyFrame(Duration.seconds(3),  event -> onLaunchFinished(), ballNewX, ballNewY);

        relaunchBallTL.getKeyFrames().addAll(keyFrameEnd);
        relaunchBallTL.play();
    }

    public void launchBall(MouseEvent e) {
        Timeline launchBallTL = new Timeline();
        launchBallTL.setCycleCount(1);

        double newX = (this.getLayoutX() + (e.getX() *-1));
        double newY = (this.getLayoutY() + (e.getY() *-1));

        amountToBounceX = e.getX();
        amountToBounceY = e.getY();
        reboundDelta = 2;

        KeyValue ballNewX = new KeyValue(this.layoutXProperty(), newX, Interpolator.EASE_OUT);
        KeyValue ballNewY = new KeyValue(this.layoutYProperty(), newY, Interpolator.EASE_OUT);

        KeyFrame keyFrameEnd = new KeyFrame(Duration.seconds(3), event -> onLaunchFinished(), ballNewX, ballNewY);

        launchBallTL.getKeyFrames().addAll(keyFrameEnd);
        launchBallTL.play();
    }

    private void onLaunchFinished() {
        double X1 = this.getLayoutX();
        double Y1 = this.getLayoutY();
        double radius1 = this.getRadius();
        double X2 = Main.holeXPos;
        double Y2 = Main.holeYPos;
        double radius2 = 15;
        double distance = Math.pow((X1 - X2) * (X1 - X2) + (Y1 - Y2) * (Y1 - Y2), 0.5);
        if (radius2 >= radius1 && distance <= (radius2 - radius1)) {
            System.out.println("Circle 1 is inside the hole.");
        } else if (radius1 >= radius2 && distance <= (radius1 - radius2)) {
            System.out.println("Circle 2 is inside Circle 1.");
        } else if (distance > (radius1 + radius2)) {
            System.out.println("Circle 2 does not overlap Circle 1.");
        } else {
            System.out.println("Circle 2 overlaps Circle 1.");
        }
    }

    private void resetBallProperties() {
        didReboundLeftRight = false;
        didReboundTopBottom = false;
        doingRebound = false;
    }
}
