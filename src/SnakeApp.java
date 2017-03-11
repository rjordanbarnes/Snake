/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;


public class SnakeApp extends Application {
    public final static int APP_WIDTH = 800;
    public final static int APP_HEIGHT = 600;
    public final static int BLOCK_SIZE = 20;
    public final static double APP_FPS = 10;

    @Override
    public void start(Stage primaryStage) {
        //// INITIALIZATION ////
        
        // Sets the title of the window
        primaryStage.setTitle("Snake");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        
        // Adds a group to a scene which is then added to the window.
        Group rootGroup = new Group();
        Scene scene = new Scene(rootGroup);
        primaryStage.setScene(scene);
        
        // Places a canvas on the group and gets its graphic context.
        Canvas canvas = new Canvas(APP_WIDTH, APP_HEIGHT);
        rootGroup.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Create snake
        Snake snake = new Snake(0, 0);
        
        //// KEY HANDLING ////
        
        // Sets the snake's directional speed based on key presses.
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    snake.setSpeed(0, -1); break;
                    case DOWN:  snake.setSpeed(0, 1); break;
                    case LEFT:  snake.setSpeed(-1, 0); break;
                    case RIGHT: snake.setSpeed(1, 0); break;
                }
            }
        });
        
        //// GAME LOOP ////

        new AnimationTimer() {
            final long startNanoTime = System.nanoTime();
            private long timeSinceLastUpdate = 0;
            Food food = new Food();
            
            @Override
            public void handle(long currentNanoTime) {
                double secondsElapsed = (currentNanoTime - startNanoTime) / 1_000_000_000.0;
                
                // Limits canvas refresh rate to APP_FPS
                if (currentNanoTime - timeSinceLastUpdate >= 1 / APP_FPS * 1_000_000_000) {
                    // Clear canvas
                    gc.clearRect(0, 0, APP_WIDTH, APP_HEIGHT);
                    
                     // Update and draw snake
                    snake.update();
                    snake.render(gc);
                    
                    // Spawns new food is snake eats food.
                    if (snake.eatFood(food)) {
                        food.randomLocation();
                    }
                    
                    // Draw food
                    food.render(gc);
                    
                    timeSinceLastUpdate = currentNanoTime;
                }
            }
        }.start();
        
        // Display Window
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
