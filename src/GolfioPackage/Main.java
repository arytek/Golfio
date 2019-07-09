package GolfioPackage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import java.io.FileInputStream;
import javafx.scene.layout.AnchorPane;
import java.lang.Math;


public class Main extends Application {

    public static AnchorPane aPane;
    private Text mouseXText;
    private Text mouseYText;
    private Text DistanceText;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("stage1.fxml"));
        primaryStage.setTitle("Golfio");
        primaryStage.setResizable(false);
        // Import ball
        Image ballImage = new Image(new FileInputStream("Images/Ball.png"));
        Image holeImage = new Image(new FileInputStream("Images/Hole.png"));
        Ball ball = new Ball(700.0, 700.0, 20, 20);
        Hole hole = new Hole(563.0, 500.0, 20, 20);
        ball.initialiseBall(ballImage);
        hole.initialiseBall(holeImage);

        mouseXText = new Text (20, 20, "0");
        mouseYText = new Text (150, 20, "0");
        mouseXText.setFont(Font.font ("Verdana", 20));
        mouseYText.setFont(Font.font ("Verdana", 20));
        DistanceText = new Text (250, 20, "No Distance");
        DistanceText.setFont(Font.font ("Verdana", 20));

        // Create a new AnchorPane
        aPane = new AnchorPane(ball.getView(), hole.getView(), mouseXText, mouseYText, DistanceText);
        createPaneEventHandlers(aPane, ball);
        Scene scene = new Scene(aPane, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void createPaneEventHandlers(AnchorPane aPane, Ball ball) {
        aPane.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
            mouseXText.setText("X: " + e.getX());
            mouseYText.setText("Y: " + e.getY());
        });

        ball.getView().addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            mouseXText.setText("X: " + e.getX());
            mouseYText.setText("Y: " + e.getY());
            double[] distNum = {710.0, e.getX(), 710.0, e.getY()};
            double distance = Math.hypot(distNum[0]-distNum[1], distNum[2]-distNum[3]);
            DistanceText.setText("Distance: " + distance);
        });
    }
}