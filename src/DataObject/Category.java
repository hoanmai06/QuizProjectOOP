package DataObject;

import java.util.ArrayList;

public class Category {
    private String categoryName;
    private String categoryInfo;
    private String idNumber;
    private ArrayList<Question> questions = new ArrayList<Question>();

    public String getCategoryName() {
        return categoryName;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryInfo(String categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public void setIdNumber(String id_number) {
        this.idNumber = id_number;
    }
}
