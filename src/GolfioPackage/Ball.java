package GolfioPackage;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class Ball {

    private double xPos;
    private double yPos;
    private double startX;
    private double startY;
    private int height;
    private int width;
    private ImageView ballView;
    private Timeline ballMovement;
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
        //createBallMovementAnimation();
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
                    createPowerIndicator(this.getX(), this.getY());
                    currentLine.setEndX(e.getX());
                    currentLine.setEndY(e.getY());
                }
        });

        ballView.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (currentLine == null) {
                createPowerIndicator(this.getX(), this.getY());
            }  else {
                createPowerIndicatorAnimation();
                currentLine.setEndX(e.getX());
                currentLine.setEndY(e.getY());

            }
        });

        ballView.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            Main.aPane.getChildren().remove(currentLine);
        });
    }

    public void createPowerIndicator(double x, double y) {
        this.currentLine = new Line(startX, startY, x, y);
        Main.aPane.getChildren().add(currentLine);
    }

    public void createPowerIndicatorAnimation() {
        currentLine.getStrokeDashArray().addAll(10d, 10d, 10d, 10d);
        double maxOffset = currentLine.getStrokeDashArray().stream().reduce(0d, (a, b) -> a + b);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        KeyFrame keyFrameOne = new KeyFrame(Duration.ZERO, new KeyValue(currentLine.strokeDashOffsetProperty(), 0, Interpolator.LINEAR));
        KeyFrame keyFrameTwo = new KeyFrame(Duration.seconds(50), new KeyValue(currentLine.strokeDashOffsetProperty(), maxOffset, Interpolator.LINEAR));

        //add the keyframe to the timeline
        timeline.getKeyFrames().addAll(keyFrameOne, keyFrameTwo);
        timeline.play();
    }

    public void createBallMovementAnimation(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20),
                new EventHandler<ActionEvent>() {

                    double dx = 7; //Step on x or velocity
                    double dy = 3; //Step on y

                    @Override
                    public void handle(ActionEvent t) {
                        //move the ball
                        ballView.setLayoutX(ballView.getLayoutX() + dx);
                        ballView.setLayoutY(ballView.getLayoutY() + dy);

                        Bounds bounds = Main.aPane.getBoundsInLocal();

                        final boolean atRightBorder = ballView.getLayoutX() >= (bounds.getMaxX() - 100.00);
                        final boolean atLeftBorder = ballView.getLayoutX() <= (bounds.getMinX() + 100.00);
                        final boolean atBottomBorder = ballView.getLayoutY() >= (bounds.getMaxY() - 100.00);
                        final boolean atTopBorder = ballView.getLayoutY() <= (bounds.getMinY() + 100.00);

                        if (atRightBorder || atLeftBorder) {
                            dx *= -1;
                        }
                        if (atBottomBorder || atTopBorder) {
                            dy *= -1;
                        }
                    }
                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    }
