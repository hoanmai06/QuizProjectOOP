package DataObject;

import java.io.Serializable;

public class Choice implements Serializable {
    private String choiceText;
    private int grade;

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
