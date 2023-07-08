package DataObjects;

import java.io.Serializable;

public class Choice implements Serializable {
    private String choiceText;
    private int grade;

    public String getChoiceText() {
        return choiceText;
    }

    public int getGrade() {
        return grade;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
