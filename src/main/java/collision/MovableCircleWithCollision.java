package collision;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MovableCircleWithCollision extends Application {
    // Movement speed
    private final double SPEED = 5.0;

    // Circle properties
    private Circle player;
    private double radius = 20;

    // Window properties
    private double sceneWidth = 600;
    private double sceneHeight = 400;

    @Override
    public void start(Stage primaryStage) {
        // Create root pane
        Pane root = new Pane();

        // Create player circle
        player = new Circle(radius);
        player.setCenterX(sceneWidth / 2);
        player.setCenterY(sceneHeight / 2);
        player.setFill(Color.BLUE);

        // Create obstacle rectangle
        Rectangle obstacle = new Rectangle(100, 100, 150, 80);
        obstacle.setFill(Color.RED);

        // Add shapes to the pane
        root.getChildren().addAll(player, obstacle);

        // Create scene
        Scene scene = new Scene(root, sceneWidth, sceneHeight);

        // Handle key events
        scene.setOnKeyPressed(event -> {
            double newX = player.getCenterX();
            double newY = player.getCenterY();

            // Move based on arrow key
            if (event.getCode() == KeyCode.RIGHT) {
                newX += SPEED;
            } else if (event.getCode() == KeyCode.LEFT) {
                newX -= SPEED;
            } else if (event.getCode() == KeyCode.UP) {
                newY -= SPEED;
            } else if (event.getCode() == KeyCode.DOWN) {
                newY += SPEED;
            }

            // Check window boundary collision
            if (isValidMove(newX, newY, obstacle)) {
                player.setCenterX(newX);
                player.setCenterY(newY);
            }
        });

        // Set stage
        primaryStage.setTitle("Movable Circle with Collision");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Check if the new position is valid (no collisions)
    private boolean isValidMove(double newX, double newY, Rectangle obstacle) {
        // Check window boundaries
        if (newX - radius < 0 || newX + radius > sceneWidth ||
                newY - radius < 0 || newY + radius > sceneHeight) {
            return false;
        }

        // Check obstacle collision using simple distance-based collision
        // Get the closest point on rectangle to circle center
        double closestX = Math.max(obstacle.getX(), Math.min(newX, obstacle.getX() + obstacle.getWidth()));
        double closestY = Math.max(obstacle.getY(), Math.min(newY, obstacle.getY() + obstacle.getHeight()));

        // Calculate distance from the closest point to circle center
        double distanceX = newX - closestX;
        double distanceY = newY - closestY;
        double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

        // If distance is less than radius, we have collision
        return distanceSquared > (radius * radius);
    }

    public static void main(String[] args) {
        launch(args);
    }
}