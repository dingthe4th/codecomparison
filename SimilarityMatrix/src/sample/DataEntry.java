package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.File;
import java.util.ArrayList;

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
        rect.setFill(Color.rgb( 0,255-Math.round(255*score),0));
    }


}
