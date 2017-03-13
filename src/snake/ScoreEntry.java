/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import javafx.scene.control.Label;

/**
 *
 * @author Jordan
 */
class ScoreEntry {
    private String name;
    private int score;
    Label label;
    
    public ScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
        this.buildLabel();
    }
    
    public void buildLabel() {
        label = new Label(name + "....." + score);
        label.setStyle("-fx-background-color: none;-fx-text-fill: rgb(0, 32, 0);");
        label.setFont(SnakeApp.generalFont);
    }
    
    public String getName() {
        return name;
    }
    
    public int getScore() {
        return score;
    }
    
    public Label getLabel() {
        return label;
    }
}
