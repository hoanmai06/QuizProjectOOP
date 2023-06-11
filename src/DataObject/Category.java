package DataObject;

import java.util.ArrayList;

public class Category {
    private String category_name;
    private String category_info;
    private String id_number;
    private ArrayList<Question> questions = new ArrayList<Question>();

    public String getCategory_name() {
        return category_name;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setCategory_info(String category_info) {
        this.category_info = category_info;
    }

    public void setID_number(String id_number) {
        this.id_number = id_number;
    }
}
