package GolfioPackage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

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

        // Import ball
        Image ballImage = new Image(new FileInputStream("Images/Ball.png"));
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
        aPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        createPaneEventHandlers(aPane, ball);
        ball.createWallCollisionListener();
        Scene scene = new Scene(aPane, 1000, 1000);
        primaryStage.setScene(scene);
        scene.setCursor(Cursor.HAND);
        primaryStage.show();
    }

//    private void test(Ball ball, Hole hole) {
//            double X1 = ball.getLayoutX();
//            double Y1 = ball.getLayoutY();
//            double radius1 = ball.getRadius();
//            double X2 = hole.getLayoutX();
//            double Y2 = hole.getLayoutY();
//            double radius2 = hole.getRadius();
//            double distance = Math.pow((X1 - X2) * (X1 - X2) + (Y1 - Y2) * (Y1 - Y2), 0.5);
//            if (radius2 >= radius1 && distance <= (radius2 - radius1)) {
//                System.out.println("Circle 1 is inside Circle 2.");
//            } else if (radius1 >= radius2 && distance <= (radius1 - radius2)) {
//                System.out.println("Circle 2 is inside Circle 1.");
//            } else if (distance > (radius1 + radius2)) {
//                System.out.println("Circle 2 does not overlap Circle 1.");
//            } else {
//                System.out.println("Circle 2 overlaps Circle 1.");
//            }
//    }
//        holeShape ts = Shape.
//            if (ts.getBoundsInParent().getWidth() > 0) {
//                System.out.println("ObjectA intersects ObjectB");
//            } else {
//                System.out.println("ObjectA does not intersect ObjectB");
//            }


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