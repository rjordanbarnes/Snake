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
    
    @Override
    public void start(Stage primaryStage) {
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
        
        // Sets the 
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

        final long startNanoTime = System.nanoTime();
        
        Food food = new Food(gc);

        new AnimationTimer() {
            private long lastUpdate = 0;
            
            @Override
            public void handle(long currentNanoTime) {
                double secondsElapsed = (currentNanoTime - startNanoTime) / 1000000000.0;
               
                if (currentNanoTime - lastUpdate >= 60000000) {
                    // Clear canvas
                    gc.clearRect(0, 0, APP_WIDTH, APP_HEIGHT);
                     // Draw snake
                    snake.update();
                    snake.render(gc);
                    food.render(gc);
                    lastUpdate = currentNanoTime;
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