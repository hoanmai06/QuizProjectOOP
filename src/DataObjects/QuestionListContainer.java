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

    public Object[][] getQuestionTableData() {
        Object[][] questionTaleData = new Object[questions.size()][3];

        for (int i = 0; i < questions.size(); i++) {
            questionTaleData[i] = questions.get(i).getQuestionTableRow();
        }

        return questionTaleData;
    }
}
