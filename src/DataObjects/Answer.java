package DataObjects;

import java.io.Serializable;

public class Answer implements Serializable {
    private char answer;
    public Answer(char ans) {
        this.answer = ans;
    }
    public char getAnswer() {
        return answer;
    }
    public void setAnswer(char ans) {
        this.answer = ans;
    }
}
