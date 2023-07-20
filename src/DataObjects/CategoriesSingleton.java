package DataObjects;

import GUIs.QuestionTableModel;

import javax.lang.model.type.NullType;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CategoriesSingleton {

    Category NULL;
    public int count;
    ArrayList<String> Namelist=new ArrayList<>();
    ArrayList<Category> categories=new ArrayList<>();
    ArrayList<Question> allQuestion=new ArrayList<>();
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
    public ArrayList<Question> getAllQuestion(Category node){
        allQuestion.clear();
        preGetallquestion(node);

        return allQuestion;
    }
    public void preGetallquestion(Category node){
        for(Question question:node.getQuestions()){
            allQuestion.add(question);
        }
        if(node.Subcategories!=null)
            for(Category subnode :node.Subcategories) {
               preGetallquestion(subnode);
            }
    }
    public Category findcategory (Category node,int index){
        categories.clear();
        pregetcategories(node);


    return categories.get(index);
    }

    public  void addSubcategiesgui65(Category node, DefaultTableModel qtm){

        for (Question question : node.getQuestions()) {
            qtm.addRow(question.getGUI65QuestionTableRow());
        }
        if(node.Subcategories!=null)
            for(Category subnode :node.Subcategories) {
                addSubcategiesgui65(subnode, qtm);
            }
    }
    public  void addSubcategiesgui21(Category node, QuestionTableModel qtm){

        for (Question question : node.getQuestions()) {
            qtm.addRow(question.getGUI21QuestionTableRow());
        }
        if(node.Subcategories!=null)
            for(Category subnode :node.Subcategories) {
                addSubcategiesgui21(subnode, qtm);
            }
    }
    public  void addSubcategiesgui63(Category node, DefaultTableModel qtm){

        for (Question question : node.getQuestions()) {
            qtm.addRow(question.getGUI63QuestionTableRow());
        }
        if(node.Subcategories!=null)
            for(Category subnode :node.Subcategories) {
                addSubcategiesgui63(subnode, qtm);
            }
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
            tmp=tmp + "     ";
        }
        if(node.getQuestions().size() > 0)
            Namelist.add(String.format("%s (%d)",tmp+node.getCategoryName(),node.getQuestions().size()));
        else
            Namelist.add(String.format("%s",tmp+node.getCategoryName()));
        if(node.Subcategories!=null)
        for(Category subnode :node.Subcategories) {
            pregetnamelist(subnode);
        }
    }
    public void addCategory(Category category, int index) {
        category.setrank(findcategory(this.headcategories,index).getrank()+1);
        count=count+1;

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
