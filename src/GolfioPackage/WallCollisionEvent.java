package GolfioPackage;

import javafx.animation.KeyFrame;
import javafx.scene.shape.Line;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.util.Duration;

public class WallCollisionEvent{

    private Ball ball;

    public WallCollisionEvent(Ball ball) {
    this.ball = ball;
    }

    public void addEventHandler() {
        /* Timeline with no KeyValue, used to create infinite loop, wherein the
        onFinished (second parameter of KeyFrame) event handler is called */
        Timeline wallCollisionTL = new Timeline(new KeyFrame(Duration.millis(10), (ActionEvent t) -> {
            double currPosX = ball.getCircle().getLayoutX();
            double lastPosX = ball.getCircle().getLastPosX();
            double currPosY = ball.getCircle().getLayoutY();
            double lastPosY = ball.getCircle().getLastPosY();
            double ballRadius = ball.getCircle().getRadius();

            testHorizontal(currPosX, lastPosX, currPosY, lastPosY, ballRadius);
            testVertical(currPosX, lastPosX, currPosY, lastPosY, ballRadius);
        }));
        wallCollisionTL.setCycleCount(Timeline.INDEFINITE);
        wallCollisionTL.play();
    }

    private void testHorizontal(double currPosX, double lastPosX, double currPosY, double lastPosY, double ballRadius) {
        for (Line border : Level.getHorizontalLines()) {
            final Bounds bounds = border.getBoundsInLocal();
            final boolean atBorder = currPosY >= (bounds.getMinY() - ballRadius) && currPosY <= (bounds.getMaxY() + ballRadius);
            final boolean withinBorder = currPosX >= (bounds.getMinX() + ballRadius) && currPosX <= (bounds.getMaxX() - ballRadius);

            if (((atBorder) && withinBorder) && !ball.getDidReboundTopBottom()) {
                if (ball.getDidReboundLeftRight()) {
                    if (lastPosX < currPosX && lastPosY > currPosY) { // Ball inbound from left, upwards.
                        ball.relaunchBall((currPosX + (Math.abs(currPosX - lastPosX)) * Main.reboundFactor),
                                (currPosY + Math.abs(currPosY - lastPosY) * Main.reboundFactor));

                    } else if (lastPosX < currPosX && lastPosY < currPosY) { // Ball inbound from left, downwards.
                        ball.relaunchBall((currPosX + (Math.abs(currPosX - lastPosX)) * Main.reboundFactor),
                                (currPosY - Math.abs(currPosY - lastPosY) * Main.reboundFactor));

                    } else if (lastPosX > currPosX && lastPosY < currPosY) { // Ball inbound from right, downwards.
                        ball.relaunchBall((currPosX - (Math.abs(currPosX - lastPosX)) * Main.reboundFactor),
                                (currPosY - Math.abs(currPosY - lastPosY) * Main.reboundFactor));

                    } else {
                        ball.relaunchBall((currPosX - (Math.abs(currPosX - lastPosX)) * Main.reboundFactor),
                                (currPosY + Math.abs(currPosY - lastPosY) * Main.reboundFactor));
                    }
                    ball.setDoingRebound(true);
                    ball.setDidReboundLeftRight(false);
                } else if (!ball.isDoingRebound()) {
                    ball.relaunchBall((currPosX - (ball.getAmountToBounceX() * Main.reboundFactor)),
                            (currPosY + (ball.getAmountToBounceY() * Main.reboundFactor)));
                    ball.setDidReboundTopBottom(true);
                }
                ball.incrementReboundDelta(0.1);
                ball.setLastPosX(currPosX);
                ball.setLastPosY(currPosY);
            }
        }
    }

    private void testVertical(double currPosX, double lastPosX, double currPosY, double lastPosY, double ballRadius) {
        for (Line border : Level.getVerticalLines()) {
            final Bounds bounds = border.getBoundsInLocal();
            final boolean atBorder = currPosX >= (bounds.getMinX() - ballRadius) && currPosX <= (bounds.getMaxX() + ballRadius);
            final boolean withinBorder = currPosY >= (bounds.getMinY() + ballRadius) && currPosY <= (bounds.getMaxY() - ballRadius);

            if (((atBorder) && withinBorder) && !ball.getDidReboundLeftRight()) {
                if (ball.getDidReboundTopBottom()) {
                    if (lastPosX < currPosX && lastPosY > currPosY) { // Ball inbound from left, upwards.
                        ball.relaunchBall((currPosX - (Math.abs(currPosX - lastPosX)) * Main.reboundFactor),
                                (currPosY - Math.abs(currPosY - lastPosY) * Main.reboundFactor));

                    } else if (lastPosX < currPosX && lastPosY < currPosY) { // Ball inbound from left, downwards.
                        ball.relaunchBall((currPosX - (Math.abs(currPosX - lastPosX)) * Main.reboundFactor),
                                (currPosY + Math.abs(currPosY - lastPosY) * Main.reboundFactor));

                    } else if (lastPosX > currPosX && lastPosY < currPosY) { // Ball inbound from right, downwards.
                        ball.relaunchBall((currPosX + (Math.abs(currPosX - lastPosX)) * Main.reboundFactor),
                                (currPosY + Math.abs(currPosY - lastPosY) * Main.reboundFactor));

                    } else {
                        ball.relaunchBall((currPosX + (Math.abs(currPosX - lastPosX)) * Main.reboundFactor),
                                (currPosY - Math.abs(currPosY - lastPosY) * Main.reboundFactor));
                    }
                    ball.setDoingRebound(true);
                    ball.setDidReboundTopBottom(false);
                } else if (!ball.isDoingRebound()) {
                    ball.relaunchBall((currPosX + (ball.getAmountToBounceX() * Main.reboundFactor)),
                            (currPosY - (ball.getAmountToBounceY() * Main.reboundFactor)));
                    ball.setDidReboundLeftRight(true);
                }
                ball.incrementReboundDelta(0.1);
                ball.setLastPosX(currPosX);
                ball.setLastPosY(currPosY);
            }
        }
    }
}
