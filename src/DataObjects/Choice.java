package DataObjects;

import java.io.Serializable;

public class Choice implements Serializable {
    private String text;
    private String name;              // them thuoc tinh nay de setGrade khi doc tu file ben ngoai
    private int gradeIndex;
    private byte[] c_imageData;

    public String getText() {
        return text;
    }

    public int getGradeIndex() {
        return gradeIndex;
    }
    public String getName() { return name;}
    public byte[] getc_ImageData() {return c_imageData;}
    public void setText(String text) {
        this.text = text;
    }

    public void setGradeIndex(int gradeIndex) {
        this.gradeIndex = gradeIndex;
    }

    public void setName(String name) {this.name = name;}
    public void setc_ImageData(byte[] imageData) {this.c_imageData = imageData;}
}

