package DataObject;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class QuizzesSingleton {
    private static final String filePath;
    static {
        try {
            filePath = Paths.get(new File(CategoriesSingleton.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getPath()).getParent().toString() + File.separator + "quizzes";
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    private static QuizzesSingleton instance;
    private ArrayList<Quiz> quizzes = new ArrayList<>();

    private QuizzesSingleton() {
        if (new File(filePath).exists())
            readQuizzesFromFile();
        else {
            Quiz defaultQuiz = new Quiz();
            defaultQuiz.setName("Default");
            defaultQuiz.setDescription("This is the default quiz");
            quizzes.add(defaultQuiz);
        }
    }

    public static QuizzesSingleton getInstance() {
        if (instance == null)
            instance = new QuizzesSingleton();

        return instance;
    }

    public void writeQuizzesToFile() {
        try {
            FileOutputStream f = new FileOutputStream(filePath);
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(quizzes);

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readQuizzesFromFile() {
        try {
            FileInputStream fi = new FileInputStream(filePath);
            ObjectInputStream oi = new ObjectInputStream(fi);

            quizzes = (ArrayList<Quiz>) oi.readObject();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Object[][] getQuizTableData() {
        Object[][] quizTableData = new Object[quizzes.size()][1];

        for (int i = 0; i < quizzes.size(); i++) {
            quizTableData[i][0] = quizzes.get(i).getName();
        }

        return quizTableData;
    }

    public ArrayList<Quiz> getQuizzes() {
        return quizzes;
    }
}
