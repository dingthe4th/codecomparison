package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DataEntry {
    float comparisonScore;
    Rectangle rect;
    Label label = new Label();
    StackPane stackPane;

    DataEntry(float entry) {
        comparisonScore = entry;
        rect = new Rectangle(50,30);
        label.setText(String.valueOf(entry));
        label.setTextFill(Color.WHITE);
        setRectColor(comparisonScore, rect);
        stackPane = new StackPane(rect,label);
    }

    public StackPane getStackPane() {
        return this.stackPane;
    }

    public void setRectColor(float score, Rectangle rect) {
        if(score < .20 || score == .20)
            rect.setFill(Color.INDIGO);
        else if(score < .40 || score == .4)
            rect.setFill(Color.BLUE);
        else if(score < .60 || score == .6)
            rect.setFill(Color.GREEN);
        else if(score < .80 || score == .8)
            rect.setFill(Color.ORANGE);
        else if(score > .80 || score == 1.0)
            rect.setFill(Color.RED);
    }
}
