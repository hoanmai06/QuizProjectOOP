package DataObjects;

import java.io.Serializable;

public class Category extends QuestionListContainer implements Serializable {
    private String categoryName;
    private String categoryInfo;
    private String idNumber;

    public String getCategoryName() {
        return categoryName;
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
