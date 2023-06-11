package DataObject;

import java.util.ArrayList;

public class Question {
    private String questionName;
    private String questionText;
    private int defaultMark;
    private final ArrayList<Choice> choices = new ArrayList<Choice>();

    public String getQuestionText() {
        return questionText;
    }

    public String getQuestionName() {
        return questionName;
    }

    public int getDefaultMark() {
        return defaultMark;
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
