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
        String newstr="";
        for(int i=0;i<choiceText.length();i++)
            if(choiceText.charAt(i)=='\'')
                newstr = newstr + "&#39;";
            else if (choiceText.charAt(i)=='\"')
                newstr = newstr + "&#34;";
            else if(choiceText.charAt(i)=='&')
                newstr = newstr + "&#38;";
            else if(choiceText.charAt(i)=='<')
                newstr = newstr + "&#60;";
            else if(choiceText.charAt(i)=='>')
                newstr = newstr + "&#39;";
            else
                newstr= newstr + String.copyValueOf(choiceText.toCharArray(),i,1);

        this.choiceText = newstr;

    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setChoiceName(String choiceName) {this.choiceName = choiceName;}
}
