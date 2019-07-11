package GolfioPackage;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.lang.Math;
import java.text.DecimalFormat;

public class Main extends Application {

    public static AnchorPane aPane;
    public static final double reboundFactor = 0.5;
    private Text mouseXText;
    private Text mouseYText;
    private Text distanceText;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private static DecimalFormat df1 = new DecimalFormat("#.#");


    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("stage1.fxml"));
        primaryStage.setTitle("Golfio");
        primaryStage.setResizable(false);

        // Load images.
        Image ballImage = new Image(new FileInputStream("Images/Ball.png"));
        Image map = new Image(new FileInputStream("Images/GolfLevel_01.png"));
        //Image holeImage = new Image(new FileInputStream("Images/Hole.png"));
        Ball ball = new Ball(10, Color.RED);
        Hole hole = new Hole(15, Color.BLACK);
        ball.initialiseBall("ball", 500, 500, ballImage);
        hole.initialiseHole("hole", 500, 800);

        // Create Text to display mouseX and  mouseY coordinates.
        mouseXText = new Text (20, 20, "X: 0");
        mouseXText.setUserData("mouseXText");
        mouseYText = new Text (20, 40, "Y: 0");
        mouseYText.setUserData("mouseYText");
        mouseXText.setFont(Font.font ("Verdana", 20));
        mouseYText.setFont(Font.font ("Verdana", 20));

        //Create Text to display distance from cursor to ball.
        distanceText = new Text (20, 60, "Distance: 0");
        distanceText.setUserData("distanceText");
        distanceText.setFont(Font.font ("Verdana", 20));

        // Create a new AnchorPane.
        aPane = new AnchorPane(hole.getCircle(), ball.getCircle(), mouseXText, mouseYText, distanceText);
        //aPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        BackgroundSize backgroundSize = new BackgroundSize(960, 960, false, false, false, false);
        aPane.setBackground(new Background(new BackgroundImage(map, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, null, backgroundSize)));
        createPaneEventHandlers(aPane, ball);
        ball.createWallCollisionListener();
        Scene scene = new Scene(aPane, 960, 960); //983, 983
        System.out.println(aPane.getWidth());
        System.out.println(aPane.getHeight());
        primaryStage.setScene(scene);
        scene.setCursor(Cursor.HAND);
        primaryStage.show();
    }

    private void createPaneEventHandlers(AnchorPane aPane, Ball ball) {
        aPane.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
            mouseXText.setText("X: " + df1.format(e.getX()));
            mouseYText.setText("Y: " + df1.format(e.getY()));
        });

        aPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            mouseXText.setText("X: " + e.getX());
            mouseYText.setText("Y: " + e.getY());
            double[] distNum = {ball.getLayoutX(), e.getX(), ball.getLayoutY(), e.getY()};
            double distance = Math.hypot(distNum[0]-distNum[1], distNum[2]-distNum[3]);
            distanceText.setText("Distance: " + df2.format(distance));
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}