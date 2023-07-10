package DataObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private String name;
    private String text;
    private final double defaultMark = 1.0;
    private ArrayList<Choice> choices = new ArrayList<Choice>();            // loai bo final
    private Choice answer;                                                 // them vao thuoc tinh Answer ls 1 trong so cac choices

    public void addChoice(Choice choice) {
        choices.add(choice);
    }

    public void setChoices(ArrayList<Choice> choices) {
        this.choices = choices;
    }           // them phuong thuc nhap vao ca list choices

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public double getDefaultMark() {
        return defaultMark;
    }

    public Choice getAnswer() {return answer;}                 // them getter setter cho Answer
    public void setAnswer(Choice ans) {this.answer = ans;}

    public Object[] getQuestionTableRow() {
        Object[] questionTableRow = {Boolean.FALSE, text, "Edit"};
        return questionTableRow;
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setName(String name) {
        this.name = name;
    }



//    public void setDefaultMark(int defaultMark) {
//        this.defaultMark = defaultMark;
//    }

}
