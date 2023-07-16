package DataObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class Category extends QuestionListContainer implements Serializable {
    private String categoryName;
    private String categoryInfo;
    private String idNumber;
    public ArrayList<Category> Subcategories=new ArrayList<>();
    private int rank;





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
    public void setrank(int rank){
        this.rank=rank;
    }
    public int getrank() {
        return rank;
    }
}
