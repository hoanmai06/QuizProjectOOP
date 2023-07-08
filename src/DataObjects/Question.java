package DataObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private String questionName;
    private String questionText;
    private int defaultMark;
    private final ArrayList<Choice> choices = new ArrayList<Choice>();

    public void addChoice(Choice choice) {
        choices.add(choice);
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getQuestionName() {
        return questionName;
    }

    public int getDefaultMark() {
        return defaultMark;
    }

    public Object[] getQuestionTableRow() {
        Object[] questionTableRow = {Boolean.FALSE, questionText, "Edit"};
        return questionTableRow;
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public void setDefaultMark(int defaultMark) {
        this.defaultMark = defaultMark;
    }

}
