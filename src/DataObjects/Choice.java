package DataObjects;

import java.io.Serializable;

public class Choice implements Serializable {
    private String text;
    private String name;              // them thuoc tinh nay de setGrade khi doc tu file ben ngoai
    private int grade;

    public String getText() {
        return text;
    }

    public int getGrade() {
        return grade;
    }
    public String getName() { return name;}

    public void setText(String text) {
        String newstr="";
        for(int i=0;i<text.length();i++)
            if(text.charAt(i)=='\'')
                newstr = newstr + "&#39;";
            else if (text.charAt(i)=='\"')
                newstr = newstr + "&#34;";
            else if(text.charAt(i)=='&')
                newstr = newstr + "&#38;";
            else if(text.charAt(i)=='<')
                newstr = newstr + "&#60;";
            else if(text.charAt(i)=='>')
                newstr = newstr + "&#62;";
            else
                newstr= newstr + String.copyValueOf(text.toCharArray(),i,1);
        this.text = text;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setName(String name) {this.name = name;}
}
