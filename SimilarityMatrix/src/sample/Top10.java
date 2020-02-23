package sample;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

public class Top10 {
    SimpleStringProperty file_1;
    SimpleStringProperty file_2;
    SimpleFloatProperty score;

    public String getFile_1() {
        return file_1.get();
    }

    public SimpleStringProperty file_1Property() {
        return file_1;
    }

    public void setFile_1(String file_1) {
        this.file_1.set(file_1);
    }

    public String getFile_2() {
        return file_2.get();
    }

    public SimpleStringProperty file_2Property() {
        return file_2;
    }

    public void setFile_2(String file_2) {
        this.file_2.set(file_2);
    }

    public SimpleFloatProperty scoreProperty() {
        return score;
    }

    public void setScore(float score) {
        this.score.set(score);
    }


    Top10(String a, String b, float score) {
        file_1 = new SimpleStringProperty(a);
        file_2 = new SimpleStringProperty(b);
        this.score = new SimpleFloatProperty(score);
    }

    float getScore() {
        return this.score.get();
    }
}
