package snake;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class SnakeApp extends Application {
    // Window size and FPS constants
    public final static int APP_WIDTH = 800;
    public final static int APP_HEIGHT = 600;
    public final static int BLOCK_SIZE = 20;
    public final static double APP_FPS = 5;
    
    // Colors
    public final Color backgroundColor = Color.rgb(190, 220, 145);
    
    // Difficulty
    // Very easy = 1 5
    // Easy = 2    10
    // Normal = 3  15
    // Hard = 4    20
    // Very Hard = 5   25
    public static int difficulty = 3;

    @Override
    public void start(Stage primaryStage) {
        displayMainMenu(primaryStage);
    }
    
    public void displayMainMenu(Stage primaryStage) {
        //// MAIN MENU INITIALIZATION ////
        // Sets the title of the window
        primaryStage.setTitle("Snake");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        
        // Vertical box
        VBox buttonBox = new VBox(5);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-background-color: rgb(190, 220, 145);");
        
        // Buttons
        Button playGameButton = new Button("Play Game");
        playGameButton.setStyle("-fx-background-color: none;");
        //playGameButton.setFont(retroComputerFont);
        
        
        
        Button highScoresButton = new Button("High Scores");
        
        // Add buttons to the vbox
        buttonBox.getChildren().addAll(playGameButton, highScoresButton);

        // Adds the vertical box to the scene which is then added to the window.
        Scene scene = new Scene(buttonBox, APP_WIDTH, APP_HEIGHT);
        primaryStage.setScene(scene);
        
        //startGame(primaryStage);
        
        // Display Window
        primaryStage.show();
    }
    
    public void startGame(Stage primaryStage) {
        //// GAME INITIALIZATION ////

        // Adds a group to a scene which is then added to the window.
        Group rootGroup = new Group();
        Scene scene = new Scene(rootGroup, backgroundColor);
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
                    case UP:    case W: snake.setSpeed(0, -1); break;
                    case DOWN:  case S: snake.setSpeed(0, 1); break;
                    case LEFT:  case A: snake.setSpeed(-1, 0); break;
                    case RIGHT: case D: snake.setSpeed(1, 0); break;
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
                if (currentNanoTime - timeSinceLastUpdate >= 1 / (APP_FPS * difficulty) * 1_000_000_000) {
                    // Clear canvas
                    gc.clearRect(0, 0, APP_WIDTH, APP_HEIGHT);
                    
                     // Update and draw snake
                    snake.update();
                    snake.render(gc);
                    
                    // Checks if the snake is dead.
                    if (snake.isDead()) {
                        System.out.println("Dead");
                        snake.render(gc);
                        super.stop();
                    }
                    
                    // Spawns new food if the snake eats food.
                    if (snake.eatFood(food)) {
                        food.randomLocation(snake);
                    }
                    
                    // Draw food
                    food.render(gc);
                    
                    timeSinceLastUpdate = currentNanoTime;
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
