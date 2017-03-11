import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jordan
 */
public class Snake {
    private int x, y;
    private int xSpeed, ySpeed;
    
    public Snake(int x, int y) {
        this.x = x;
        this.y = y;
        setSpeed(1, 0);
    }
    
    // Changes the snakes speed.
    public void setSpeed(int x, int y) {
        xSpeed = x;
        ySpeed = y;
    }
    
    // Moves the snake based on its speed.
    public void update() {
        x = x + xSpeed * SnakeApp.BLOCK_SIZE;
        y = y + ySpeed * SnakeApp.BLOCK_SIZE;
    }
    
    public void render(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(x, y, SnakeApp.BLOCK_SIZE, SnakeApp.BLOCK_SIZE);
    }
}
