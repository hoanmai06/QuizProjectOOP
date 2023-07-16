package DataObjects;

import javax.lang.model.type.NullType;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CategoriesSingleton {

    Category NULL;
    public int count;
    ArrayList<String> Namelist=new ArrayList<>();
    ArrayList<Category> categories=new ArrayList<>();
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
    private Category headcategories=new Category() ;
    private Question addingQuestion;


    private CategoriesSingleton() {
        if (new File(filePath).exists())
            readCategoriesFromFile();
        else {
            Category default_category = new Category();
            default_category.setCategoryName("Default");
            default_category.setCategoryInfo("This is the default category.");
            default_category.setrank(0);
            default_category.Subcategories=new ArrayList<>();
            headcategories = default_category;
        }
    }

    public void writeCategoriesToFile() {
        try {
            FileOutputStream f = new FileOutputStream(filePath);
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(headcategories);

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

            headcategories = (Category) oi.readObject();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public Category findcategory (Category node,int index){
        categories.clear();
        pregetcategories(node);


    return categories.get(index);
    }
    public  void pregetcategories(Category node){
       categories.add(node);
        if(node.Subcategories!=null)
        for(Category subnode :node.Subcategories) {
            pregetcategories(subnode);
        }
    }
    public  void pregetnamelist(Category node){

        String tmp="";
        int n= node.getrank();
        for(int i=1;i<=n;i++){
            tmp=tmp + "   ";
        }
        Namelist.add(String.format("%s (%d)",tmp+node.getCategoryName(),node.getQuestions().size()));
        if(node.Subcategories!=null)
        for(Category subnode :node.Subcategories) {
            pregetnamelist(subnode);
        }
    }
    public void addCategory(Category category, int index) {
        category.setrank(findcategory(this.headcategories,index).getrank()+1);
        count=count+1;
//if (findcategory(this.headcategories,index)!=null)
  //  System.out.println(findcategory(this.headcategories,index).getCategoryName());
        findcategory(this.headcategories,index).Subcategories.add(category);
        writeCategoriesToFile();
    }

    public static CategoriesSingleton getInstance() {
        if (instance == null)
            instance = new CategoriesSingleton();

        return instance;
    }

    public Category getCategory() {
        return headcategories;
    }
    public ArrayList<Category> getCategories() {
        categories.clear();
        pregetcategories(headcategories);
        return categories;
    }

    public String[] getCategoryNameList() {
        if(Namelist!=null)
        Namelist.clear();
        pregetnamelist(headcategories);
        String[] list= new String[Namelist.size()];
        for(int i=0;i<=(Namelist.size()-1);i++)
            list[i]=Namelist.get(i);



        return list;
    }

    public Question getAddingQuestion() {
        return addingQuestion;
    }

    public void setAddingQuestion(Question addingQuestion) {
        this.addingQuestion = addingQuestion;
    }
}
