package GolfioPackage;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.awt.*;
import java.lang.Math;
import java.text.DecimalFormat;

public class Main extends Application {

    public static AnchorPane aPane;
    public static double mouseX;
    public static double mouseY;
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

        // Import ball
        //Image ballImage = new Image(new FileInputStream("Images/Ball.png"));
        //Image holeImage = new Image(new FileInputStream("Images/Hole.png"));
        Ball ball = new Ball(10, Color.RED);
        Hole hole = new Hole(10, Color.BLACK);
        ball.initialiseBall("ball", 500, 500);
        hole.initialiseHole("hole", 300, 800);

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
        aPane = new AnchorPane(ball.getCircle(), hole.getCircle(), mouseXText, mouseYText, distanceText);
        createPaneEventHandlers(aPane, ball);
        ball.createWallCollisionListener();
        Scene scene = new Scene(aPane, 1000, 1000);
        primaryStage.setScene(scene);
        scene.setCursor(Cursor.HAND);
        primaryStage.show();
    }

    private void createPaneEventHandlers(AnchorPane aPane, Ball ball) {
        aPane.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
            mouseX = e.getX();
            mouseY = e.getY();
            mouseXText.setText("X: " + df1.format(e.getX()));
            mouseYText.setText("Y: " + df1.format(e.getY()));
            if((e.getX() < 1) || (e.getX() > 1000) || (e.getY() < 1) || (e.getY() > 1000)){
                try {
                    Robot bot = new Robot();
                    bot.mouseMove(960, 540);
                } catch (AWTException ex) {
                }
            }
        });

        aPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            mouseXText.setText("X: " + e.getX());
            mouseYText.setText("Y: " + e.getY());
            double[] distNum = {ball.getLayoutX(), e.getX(), ball.getLayoutY(), e.getY()};
            double distance = Math.hypot(distNum[0]-distNum[1], distNum[2]-distNum[3]);
            distanceText.setText("Distance: " + df2.format(distance));
            if((e.getX() < 1) || (e.getX() > 1000) || (e.getY() < 1) || (e.getY() > 1000)){
                try {
                    Robot bot = new Robot();
                    bot.mouseMove(960, 540);
                } catch (AWTException ex) {
                }
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}