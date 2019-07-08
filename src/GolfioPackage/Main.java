package GolfioPackage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import javafx.scene.shape.Line;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {

    private Line currentLine;
    private double startLineX;
    private double startLineY;
    private AnchorPane aPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("stage1.fxml"));
        primaryStage.setTitle("Golfio");
        primaryStage.setResizable(false);
        // Import ball
        Image ballImage = new Image(new FileInputStream("Images/Ball.png"));
        Image holeImage = new Image(new FileInputStream("Images/Hole.png"));
        Ball ball = new Ball(700.0, 250.0, 20, 20);
        Hole hole = new Hole(400.0, 600.0, 20, 20);
        ball.initialiseBall(ballImage);
        hole.initialiseBall(holeImage);
        // Create a new AnchorPane
        aPane = new AnchorPane(ball.getView(), hole.getView());

        ball.getView().addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            startLineX = ball.getxPos() + 10;
            startLineY = ball.getyPos() + 10;
            if(!currentLine.isVisible())
            {
                currentLine.setVisible(true);
                currentLine.setEndX(e.getX());
                currentLine.setEndY(e.getY());
            }
        });

        ball.getView().addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (currentLine == null) {
                addLine(e.getX(), e.getY());
            }  else {
                currentLine.setEndX(e.getX());
                currentLine.setEndY(e.getY());
        }
        });

        ball.getView().addEventHandler(MouseEvent.MOUSE_RELEASED, ev -> {
            currentLine.setVisible(false);
        });
        Scene scene = new Scene(aPane, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addLine(double x, double y) {
        currentLine = new Line(startLineX, startLineY, x, y);
        aPane.getChildren().add(currentLine);
    }

    public static void main(String[] args) {
        launch(args);
    }
}