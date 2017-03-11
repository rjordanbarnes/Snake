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
public class Food {
    private int x, y;
    
    // Creates Food with specific location.
    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // Creates Food with random location.
    public Food() {
        this.x = getRandomX();
        this.y = getRandomY();
    }
    
    // Returns the food's x coordinate.
    public int getX() {
        return x;
    }
    
    // Returns the food's y coordinate.
    public int getY() {
        return y;
    }
    
    // Returns a random X coordinate based on the grid size.
    public int getRandomX() {
        return (int)(Math.random() * (SnakeApp.APP_WIDTH / SnakeApp.BLOCK_SIZE)) * SnakeApp.BLOCK_SIZE;
    }
    
    // Returns a random Y coordinate based on the grid size.
    public int getRandomY() {
        return (int)(Math.random() * (SnakeApp.APP_HEIGHT / SnakeApp.BLOCK_SIZE)) * SnakeApp.BLOCK_SIZE;
    }
    
    // Places the food at a random location on the grid without placing it directly
    // on the snake.
    public void randomLocation(Snake snake) {
        boolean validLocation = true;
        do {
            this.x = getRandomX();
            this.y = getRandomY();
            
            for (int i = 0; i < snake.getTail().size(); i++) {
                if ((x == snake.getTail().get(i).x && y == snake.getTail().get(i).y) ||
                        (x == snake.getX() && y == snake.getY())) {
                    validLocation = false;
                    break;
                } else {
                    validLocation = true;
                }
            }
        } while (!validLocation);
    }
    
    // Renders the food.
    public void render(GraphicsContext gc) {
        gc.setFill(Color.PINK);
        gc.fillRect(x, y, SnakeApp.BLOCK_SIZE, SnakeApp.BLOCK_SIZE);
    }
}
