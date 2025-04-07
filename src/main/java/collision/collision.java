package collision;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class collision extends Application {

    private static final double MOVE_SPEED = 5.0;

    @Override
    public void start(Stage primaryStage) {
        // Create a pane to hold the nodes
        Pane pane = new Pane();

        // Create two rectangles
        Rectangle rect1 = new Rectangle(50, 50, 50, 50); // Moving rectangle
        rect1.setFill(Color.BLUE);

        Rectangle rect2 = new Rectangle(200, 200, 50, 50); // Stationary rectangle
        rect2.setFill(Color.GREEN);

        // Add rectangles to the pane
        pane.getChildren().addAll(rect1, rect2);

        // Set up the scene
        Scene scene = new Scene(pane, 400, 400);

        // Handle key presses for movement
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    rect1.setY(rect1.getY() - MOVE_SPEED);
                    break;
                case DOWN:
                    rect1.setY(rect1.getY() + MOVE_SPEED);
                    break;
                case LEFT:
                    rect1.setX(rect1.getX() - MOVE_SPEED);
                    break;
                case RIGHT:
                    rect1.setX(rect1.getX() + MOVE_SPEED);
                    break;
            }
        });

        // AnimationTimer to continuously check for collision
        AnimationTimer collisionChecker = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Check if rect1 collides with rect2
                if (rect1.getBoundsInParent().intersects(rect2.getBoundsInParent())) {
                    rect1.setFill(Color.RED); // Change color on collision
                } else {
                    rect1.setFill(Color.BLUE); // Revert to original color
                }
            }
        };
        collisionChecker.start();

        // Set up the stage
        primaryStage.setTitle("Collision Detection Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}