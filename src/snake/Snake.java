package snake;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Snake {
    // Positioning
    private int x, y;
    private int xSpeed, ySpeed;
    private boolean isDead = false;
    
    // Colors
    private Color headColor = Color.rgb(0, 32, 0);
    private final Color TAIL_COLOR = Color.rgb(0, 32, 0);
    private final Color DEAD_COLOR = Color.rgb(70, 10, 10);
    
    // Tail array that tracks the snake's tail segments. The end of the tail
    // is at the end of the array.
    private ArrayList<SnakeTailSegment> tail = new ArrayList<SnakeTailSegment>();
    private int extraLength = 0;
    
    // Creates a snake with a specific x and y coordinate and moving to the right.
    public Snake(int x, int y) {
        this.x = x;
        this.y = y;
        setSpeed(1, 0);
    }
    
    // Checks if the snake eats a food. Extends the snake's tail if so.
    public boolean eatFood(Food food) {
        if (x == food.getX() && y == food.getY()) {
            extraLength++;
            tail.add(new SnakeTailSegment(x, y));
            SnakeApp.currentScore += SnakeApp.APP_FPS * SnakeApp.difficulty;
            return true;
        } else {
            return false;
        }
    }
    
    // Returns the snake's X position.
    public int getX() {
        return x;
    }
    
    // Returns the snake's Y position.
    public int getY() {
        return y;
    }
    
    // Returns the snake's tail.
    public ArrayList<SnakeTailSegment> getTail() {
        return tail;
    }
    
    // Checks if the snake has ran into a wall or itself.
    public boolean isDead() {
        // Checks if the snake's head hits any part of the tail.
        for (int i = 0; i < extraLength; i++) {
            if (x == tail.get(i).x && y == tail.get(i).y) {
                isDead = true;
            }
        }
        
        // Checks if the snake's head goes beyond the window.
        if (x >= SnakeApp.APP_WIDTH || x < 0 ||
            y >= SnakeApp.APP_HEIGHT || y < 0) {
            isDead = true;
        }
        
        if (isDead) {
            headColor = DEAD_COLOR;
            return true;
        } else {
            return false;
        }
    }
    
    // Changes the snake's speed.
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
            for (int i = extraLength - 1; i > 0; i--) {
                tail.get(i).x = tail.get(i - 1).x;
                tail.get(i).y = tail.get(i - 1).y;
            }

            tail.get(0).x = x;
            tail.get(0).y = y;
        }
        
        // Updates the snake's position based on its speed.
        x = x + xSpeed * SnakeApp.BLOCK_SIZE;
        y = y + ySpeed * SnakeApp.BLOCK_SIZE;
    }
    
    // Renders the snake.
    public void render(GraphicsContext gc) {
        // Renders snake's tail.
        gc.setFill(TAIL_COLOR);
        for (int i = 0; i < extraLength; i++) {
            gc.fillRoundRect(tail.get(i).x, tail.get(i).y, SnakeApp.BLOCK_SIZE, SnakeApp.BLOCK_SIZE, 10, 10);
        }
        
        // Renders snake's head.
        gc.setFill(headColor);
        gc.fillRoundRect(x, y, SnakeApp.BLOCK_SIZE, SnakeApp.BLOCK_SIZE, 10, 10);
    }
}
