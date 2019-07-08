package GolfioPackage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;


public class Main extends Application {

    public static AnchorPane aPane;

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
        // Create a new AnchorPane
        Text t = new Text (20, 20, Double.toString(ball.getX()));

        ball.createEventHandlers();
        aPane = new AnchorPane(ball.getView(), hole.getView(), t);
        Scene scene = new Scene(aPane, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}