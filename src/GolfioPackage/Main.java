package GolfioPackage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.Group;
import java.io.FileInputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
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
        // Create a Group of ImageViews called root.
        Group root = new Group(ball.getView(), hole.getView());
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
