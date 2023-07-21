package DataObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private String name;
    private String text;
    private byte[] q_imageData;
    private final double defaultMark = 1.0;
    private ArrayList<Choice> choices = new ArrayList<Choice>();            // loai bo final
    private ArrayList<Choice> answers = new ArrayList<Choice>();                                                 // them vao thuoc tinh Answer ls 1 trong so cac choices

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

    public ArrayList<Choice> getAnswers() {return answers;}                 // them getter setter cho Answer
    public byte[] getq_ImageData() {return q_imageData;}

    public void setAnswers(ArrayList<Choice> ans) {this.answers = ans;}

    public void addAnswer(Choice answer) {
        this.answers.add(answer);
    }

    public Object[] getGUI21QuestionTableRow() {
        return new Object[]{Boolean.FALSE, text, "Edit"};
    }

    public Object[] getGUI63QuestionTableRow() {
        return new Object[]{Boolean.FALSE, text};
    }

    public Object[] getGUI65QuestionTableRow() {
        return new Object[]{text};
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
    public void setImageData(byte[] imageData) {this.q_imageData = imageData;}

    public boolean isMultipleChoices() {
        return answers.size() != 1;
    }


//    public void setDefaultMark(int defaultMark) {
//        this.defaultMark = defaultMark;
//    }

}
