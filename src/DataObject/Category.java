package DataObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
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

    public Object[][] getQuestionTableData() {
        Object[][] questionTaleData = new Object[questions.size()][3];
        Object[] questionRowData = {Boolean.FALSE, "", "Edit"};

        for (int i = 0; i < questions.size(); i++) {
            questionRowData[2] = questions.get(i).getQuestionText();
            questionTaleData[i] = questionRowData;
        }

        return questionTaleData;
    }
}
