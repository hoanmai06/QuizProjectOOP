package DataObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionListContainer implements Serializable {
    private ArrayList<Question> questions = new ArrayList<>();

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Object[][] getGUI21QuestionTableData() {
        Object[][] questionTaleData = new Object[questions.size()][3];

        for (int i = 0; i < questions.size(); i++) {
            questionTaleData[i] = questions.get(i).getGUI21QuestionTableRow();
        }

        return questionTaleData;
    }

    public Object[][] getGUI63QuestionTableData() {
        Object[][] questionTaleData = new Object[questions.size()][2];

        for (int i = 0; i < questions.size(); i++) {
            questionTaleData[i] = questions.get(i).getGUI63QuestionTableRow();
        }

        return questionTaleData;
    }

    public Object[][] getGUI65QuestionTableData() {
        Object[][] questionTaleData = new Object[questions.size()][1];

        for (int i = 0; i < questions.size(); i++) {
            questionTaleData[i] = questions.get(i).getGUI65QuestionTableRow();
        }

        return questionTaleData;
    }
}
