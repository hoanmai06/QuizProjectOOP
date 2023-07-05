package DataObject;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CategoriesSingleton {
    private static final String filePath;
    static {
        try {
            filePath = Paths.get(new File(CategoriesSingleton.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getPath()).getParent().toString() + File.separator + "categories";
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static CategoriesSingleton instance;
    private ArrayList<Category> categories = new ArrayList<>();

    private CategoriesSingleton() {
        if (new File(filePath).exists())
            readCategoriesFromFile();
        else {
            Category default_category = new Category();
            default_category.setCategoryName("Default");
            default_category.setCategoryInfo("This is the default category.");
            categories.add(default_category);
        }
    }

    public void writeCategoriesToFile() {
        try {
            FileOutputStream f = new FileOutputStream(filePath);
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(categories);

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readCategoriesFromFile() {
        try {
            FileInputStream fi = new FileInputStream(filePath);
            ObjectInputStream oi = new ObjectInputStream(fi);

            categories = (ArrayList<Category>) oi.readObject();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCategory(Category category) {
        categories.add(category);
        writeCategoriesToFile();
    }

    public static CategoriesSingleton getInstance() {
        if (instance == null)
            instance = new CategoriesSingleton();

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
}
