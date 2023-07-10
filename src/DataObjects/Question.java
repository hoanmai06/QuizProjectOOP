package DataObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private String questionName;
    private String questionText;
    private final double defaultMark = 1.0;
    private ArrayList<Choice> choices = new ArrayList<Choice>();            // loai bo final
    private Choice answer;                                                 // them vao thuoc tinh Answer ls 1 trong so cac choices

    public void addChoice(Choice choice) {
        choices.add(choice);
    }

    public void setChoices(ArrayList<Choice> choices) {
        this.choices = choices;
    }           // them phuong thuc nhap vao ca list choices

    public String getQuestionText() {
        return questionText;
    }

    public String getQuestionName() {
        return questionName;
    }

    public double getDefaultMark() {
        return defaultMark;
    }

    public Choice getAnswer() {return answer;}                 // them getter setter cho Answer
    public void setAnswer(Choice ans) {this.answer = ans;}

    public Object[] getQuestionTableRow() {
        Object[] questionTableRow = {Boolean.FALSE, questionText, "Edit"};
        return questionTableRow;
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }

    public void setQuestionText(String questionText) {
        String newstr="";
        for(int i=0;i<questionText.length();i++)
            if(questionText.charAt(i)=='\'')
                newstr = newstr + "&#39;";
            else if (questionText.charAt(i)=='\"')
                newstr = newstr + "&#34;";
            else if(questionText.charAt(i)=='&')
                newstr = newstr + "&#38;";
            else if(questionText.charAt(i)=='<')
                newstr = newstr + "&#60;";
            else if(questionText.charAt(i)=='>')
                newstr = newstr + "&#39;";
            else
                newstr= newstr + String.copyValueOf(questionText.toCharArray(),i,1);
        this.questionText = questionText;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

//    public void setDefaultMark(int defaultMark) {
//        this.defaultMark = defaultMark;
//    }

}
