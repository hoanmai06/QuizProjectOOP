package DataObjects;

import java.io.Serializable;

public class Choice implements Serializable {
    private String text;
    private String name;              // them thuoc tinh nay de setGrade khi doc tu file ben ngoai
    private int grade;
    private byte[] imageData;

    public String getText() {
        return text;
    }

    public int getGrade() {
        return grade;
    }
    public String getName() { return name;}
    public byte[] getImageData() {return imageData;}

    public void setText(String text) {
        this.text = text;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setName(String name) {this.name = name;}
    public void setImageData(byte[] imageData) {this.imageData = imageData;}
}
