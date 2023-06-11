package DataObject;

import java.util.ArrayList;

public class Singleton {
    private static Singleton instance;
    private ArrayList<Category> categories = new ArrayList<>();

    private Singleton() {
        Category default_category = new Category();
        default_category.setCategoryName("Default");
        default_category.setCategoryInfo("This is the default category.");
        categories.add(default_category);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public static Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();

        return instance;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public String[] getCategoryNameList() {
        String[] name_list = new String[categories.size()];

        for (int i = 0; i < categories.size(); i++) {
            name_list[i] = String.format(
                    "%s (%d)",
                    categories.get(i).getCategoryName(),
                    categories.get(i).getQuestions().size());
        }

        return name_list;
    }

    public Object[][] getQuestionTableData(Category category) {
        Object[][] questionTaleData = new Object[category.getQuestions().size()][3];
        Object[] questionRowData = {Boolean.FALSE, "", "Edit"};

        for (int i = 0; i < category.getQuestions().size(); i++) {
            questionRowData[2] = category.getQuestions().get(i).getQuestionText();
            questionTaleData[i] = questionRowData;
        }

        return questionTaleData;
    }
}
