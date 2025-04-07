package collision;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import java.util.List;

public class PixelGame extends Application {
    // Game constants
    private static final int TILE_SIZE = 16; // Size of each tile/pixel
    private static final int GRID_WIDTH = 20; // Number of tiles horizontally
    private static final int GRID_HEIGHT = 15; // Number of tiles vertically
    private static final int WIDTH = GRID_WIDTH * TILE_SIZE;
    private static final int HEIGHT = GRID_HEIGHT * TILE_SIZE;

    // Game objects
    private Player player;
    private List<GameObject> gameObjects = new ArrayList<>();

    // Key tracking
    private boolean[] keys = new boolean[256];

    @Override
    public void start(Stage primaryStage) {
        // Create canvas
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create game objects
        player = new Player(5, 5);
        gameObjects.add(player);

        // Add some walls/obstacles
        gameObjects.add(new Wall(8, 3, 4, 1));  // Horizontal wall
        gameObjects.add(new Wall(12, 4, 1, 6)); // Vertical wall
        gameObjects.add(new Wall(3, 10, 8, 1)); // Another horizontal wall

        // Set up scene and input handling
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        scene.setOnKeyPressed(e -> {
            keys[e.getCode().getCode()] = true;
        });

        scene.setOnKeyReleased(e -> {
            keys[e.getCode().getCode()] = false;
        });

        // Game loop
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render(gc);
            }
        }.start();

        // Set up stage
        primaryStage.setTitle("Pixel Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void update() {
        // Handle player movement based on keys
        int dx = 0, dy = 0;

        if (keys[KeyCode.RIGHT.getCode()]) dx = 1;
        else if (keys[KeyCode.LEFT.getCode()]) dx = -1;

        if (keys[KeyCode.DOWN.getCode()]) dy = 1;
        else if (keys[KeyCode.UP.getCode()]) dy = -1;

        // Try to move player
        if (dx != 0 || dy != 0) {
            int newX = player.x + dx;
            int newY = player.y + dy;

            // Check boundaries
            if (newX >= 0 && newX < GRID_WIDTH && newY >= 0 && newY < GRID_HEIGHT) {
                // Check collision with other objects
                boolean collision = false;
                for (GameObject obj : gameObjects) {
                    if (obj != player && obj.collidesWith(newX, newY)) {
                        collision = true;
                        break;
                    }
                }

                if (!collision) {
                    player.x = newX;
                    player.y = newY;
                }
            }
        }
    }

    private void render(GraphicsContext gc) {
        // Clear screen
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw grid (optional, for debugging)
        gc.setStroke(Color.DARKGRAY);
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                gc.strokeRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // Draw all game objects
        for (GameObject obj : gameObjects) {
            obj.render(gc);
        }
    }

    // Base class for all game objects
    private abstract class GameObject {
        protected int x, y; // Grid position
        protected int width, height; // Size in grid cells
        protected Color color;

        public GameObject(int x, int y, int width, int height, Color color) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
        }

        public boolean collidesWith(int cx, int cy) {
            return cx >= x && cx < x + width && cy >= y && cy < y + height;
        }

        public void render(GraphicsContext gc) {
            gc.setFill(color);
            gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, width * TILE_SIZE, height * TILE_SIZE);
        }
    }

    // Player class
    private class Player extends GameObject {
        public Player(int x, int y) {
            super(x, y, 1, 1, Color.BLUE);
        }
    }

    // Wall/obstacle class
    private class Wall extends GameObject {
        public Wall(int x, int y, int width, int height) {
            super(x, y, width, height, Color.RED);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}