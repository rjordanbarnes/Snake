import java.util.ArrayList;
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
    private int extraLength = 0;
    private ArrayList<SnakeTailSegment> tail = new ArrayList<SnakeTailSegment>();
    
    public Snake(int x, int y) {
        this.x = x;
        this.y = y;
        setSpeed(1, 0);
    }
    
    public boolean eatFood(Food food) {
        if (x == food.getX() && y == food.getY()) {
            extraLength++;
            tail.add(new SnakeTailSegment(x, y));
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
    
    // Updates the snake's position and tail.
    public void update() {
        // Updates the snake's tail using the tail ArrayList.
        if (extraLength > 0) {
            for (int i = 0; i < extraLength - 1; i++) {
                tail.get(i).x = tail.get(i + 1).x;
                tail.get(i).y = tail.get(i + 1).y;
            }

            tail.get(extraLength - 1).x = x;
            tail.get(extraLength - 1).y = y;
        }
        
        // Updates the snake's position based on its speed.
        x = x + xSpeed * SnakeApp.BLOCK_SIZE;
        y = y + ySpeed * SnakeApp.BLOCK_SIZE;
    }
    
    // Renders the snake.
    public void render(GraphicsContext gc) {
        // Renders snake's head.
        gc.setFill(Color.GREEN);
        gc.fillRect(x, y, SnakeApp.BLOCK_SIZE, SnakeApp.BLOCK_SIZE);
        
        // Renders snake's tail.
        for (int i = 0; i < extraLength; i++) {
            gc.fillRect(tail.get(i).x, tail.get(i).y, SnakeApp.BLOCK_SIZE, SnakeApp.BLOCK_SIZE);
        }
    }
}
