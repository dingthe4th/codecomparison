package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MetricsData {
    double programVocabolary;
    double programLength;
    double programVolume;
    double programLevel;
    double programDifficulty;
    double programEffort;
    MetricsData() {
        programDifficulty = 0;
        programEffort = 0;
        programLength = 0;
        programLevel = 0;
        programVocabolary = 0;
        programVolume = 0;
    }
    public void setProgramDifficulty(double val) {
        this.programDifficulty = val;
    }
    public void setProgramVocabolary(double val) {
        this.programVocabolary = val;
    }
    public void setProgramLength(double val) {
        this.programLength = val;
    }
    public void setProgramVolume(double val) {
        this.programVolume = val;
    }
    public void setProgramLevel(double val) {
        this.programLevel = val;
    }
    public void setProgramEffort(double val) {
        this.programEffort = val;
    }

    public StackPane getDifficultyPane() {
        Rectangle rect = new Rectangle(50,30);
        Label label = new Label(String.valueOf(programDifficulty));
        label.setTextFill(Color.WHITE);
        return new StackPane(rect,label);
    }

    public StackPane getLevelPane() {
        Rectangle rect = new Rectangle(50,30);
        Label label = new Label(String.valueOf(programLevel));
        label.setTextFill(Color.WHITE);
        return new StackPane(rect,label);
    }

    public StackPane getEffortPane() {
        Rectangle rect = new Rectangle(50,30);
        Label label = new Label(String.valueOf(programEffort));
        label.setTextFill(Color.WHITE);
        return new StackPane(rect,label);
    }

    public StackPane getVolumePane() {
        Rectangle rect = new Rectangle(50,30);
        Label label = new Label(String.valueOf(programVolume));
        label.setTextFill(Color.WHITE);
        return new StackPane(rect,label);
    }

    public StackPane getLengthPane() {
        Rectangle rect = new Rectangle(50,30);
        Label label = new Label(String.valueOf(programLength));
        label.setTextFill(Color.WHITE);
        return new StackPane(rect,label);
    }

    public StackPane getVocabolaryPane() {
        Rectangle rect = new Rectangle(50,30);
        Label label = new Label(String.valueOf(programVocabolary));
        label.setTextFill(Color.WHITE);
        return new StackPane(rect,label);
    }
}
