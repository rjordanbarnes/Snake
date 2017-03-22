package snake;

import javafx.scene.control.Label;

class ScoreEntry {
    private String name;
    private int score;
    Label label;
    
    public ScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
        this.buildLabel();
    }
    
    // Creates a label to be displayed on the High Scores screen.
    public void buildLabel() {
        label = new Label(name + "....." + score);
        label.setStyle("-fx-background-color: none;-fx-text-fill: rgb(0, 32, 0);");
        label.setFont(SnakeApp.generalFont);
    }
    
    // Returns the player name who achieved the score.
    public String getName() {
        return name;
    }
    
    // Returns the score.
    public int getScore() {
        return score;
    }
    
    // Returns the label built by buildLabel
    public Label getLabel() {
        return label;
    }
}
