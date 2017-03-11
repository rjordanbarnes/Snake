import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
    
    public boolean eatFood(Food food) {
        if (x == food.getX() && y == food.getY()) {
            return true;
        } else {
            return false;
        }
    }
    
    // Changes the snakes speed.
    public void setSpeed(int x, int y) {
        // Prevents snake from going backwards.
        if (ySpeed + y != 0 || xSpeed + x != 0) {
            ySpeed = y;
            xSpeed = x;
        }
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
