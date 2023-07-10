package DataObjects;

import java.io.Serializable;

public class Choice implements Serializable {
    private String choiceText;
    private String choiceName;              // them thuoc tinh nay de setGrade khi doc tu file ben ngoai
    private int grade;

    public String getChoiceText() {
        return choiceText;
    }

    public int getGrade() {
        return grade;
    }
    public String getChoiceName() { return choiceName;}

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setChoiceName(String choiceName) {this.choiceName = choiceName;}
}
