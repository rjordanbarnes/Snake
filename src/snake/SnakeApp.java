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
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;


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
        // Sets the title of the window
        primaryStage.setTitle("Snake");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        // Creates the first blank pane to display
        Pane intro = new Pane();
        intro.setStyle("-fx-background-color: rgb(190, 220, 145);");
        Scene primaryScene = new Scene(intro, APP_WIDTH, APP_HEIGHT, backgroundColor);
        
        // Sets the scene and displays the main menu.
        primaryStage.setScene(primaryScene);
        primaryStage.show();
        displayMainMenu(primaryScene);
    }
    
    public void displayMainMenu(Scene primaryScene) {
        //// MAIN MENU INITIALIZATION ////
        // Main title label
        Label mainTitle = new Label("SNAKE");
        mainTitle.setTextFill(Color.rgb(0, 32, 0));
        mainTitle.setStyle("-fx-padding: 0 0 100px 0;");
        
        // Create and style the vertical box (background).
        VBox buttonBox = new VBox(5);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-background-color: rgb(190, 220, 145);");
        
        // Fade the Main Menu in
        FadeTransition ft = new FadeTransition(Duration.millis(5000), buttonBox);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        
        // Create and style the buttons.
        Button playGameButton = new Button("Play Game");
        playGameButton.setStyle("-fx-background-color: none;-fx-text-fill: rgb(0, 32, 0);");
        Button highScoresButton = new Button("High Scores");
        highScoresButton.setStyle("-fx-background-color: none;-fx-text-fill: rgb(0, 32, 0);");
        
        // Load External fonts and apply them to the menu.
        try { 
            final Font generalFont = Font.loadFont(new FileInputStream(new File("./Retro Computer.TTF")), 30);
            playGameButton.setFont(generalFont);
            highScoresButton.setFont(generalFont);
            
            final Font titleFont = Font.loadFont(new FileInputStream(new File("./Advanced Pixel LCD.TTF")),65);
            mainTitle.setFont(titleFont);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        // Add the buttons to the vbox
        buttonBox.getChildren().addAll(mainTitle, playGameButton, highScoresButton);

        // Applies the button box to the scene.
        primaryScene.setRoot(buttonBox);
        
        // Button functionality
        playGameButton.setOnAction(e -> startGame(primaryScene));
    }
    
    public void startGame(Scene primaryScene) {
        //// GAME INITIALIZATION ////
        // Adds a group to a scene which is then added to the window.
        Group rootGroup = new Group();
        //Scene gameScene = new Scene(rootGroup, backgroundColor);
        primaryScene.setRoot(rootGroup);
        primaryScene.setFill(backgroundColor);
        
        // Places a canvas on the group and gets its graphic context.
        Canvas canvas = new Canvas(APP_WIDTH, APP_HEIGHT);
        rootGroup.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Create snake
        Snake snake = new Snake(0, 0);
        
        //// KEY HANDLING ////
        
        // Sets the snake's directional speed based on key presses.
       primaryScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
