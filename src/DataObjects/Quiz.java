package DataObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz extends QuestionListContainer implements Serializable {
    public static final int TIME_TYPE_HOUR = 1;
    public static final int TIME_TYPE_MINUTE = 0;
    private String name;
    private String description;
    private double maxGrade = 10;
    private int timeLimit = -1;
    private int timeLimitType = Quiz.TIME_TYPE_HOUR;
    private ArrayList<String> previousAttemptList = new ArrayList<>();
    private boolean isShuffle;

    public Object[][] getAttemptTableData() {
        if (previousAttemptList.size() == 0)
            return new Object[][]{{"None", ""}};

        Object[][] attemptTableData = new Object[previousAttemptList.size()][2];
        for (int i = 0; i < previousAttemptList.size(); i++) {
            attemptTableData[i][0] = "Preview";
            attemptTableData[i][1] = previousAttemptList.get(i);
        }

        return attemptTableData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getTimeLimitType() {
        return timeLimitType;
    }

    public void setTimeLimitType(int timeLimitType) {
        this.timeLimitType = timeLimitType;
    }

    public ArrayList<String> getPreviousAttemptList() {
        return previousAttemptList;
    }

    public double getTotalMark() {
        double totalMark = 0;
        for (Question question : getQuestions()) {
            totalMark += question.getDefaultMark();
        }
        return totalMark;
    }

    public double getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(double maxGrade) {
        this.maxGrade = maxGrade;
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public void setShuffle(boolean shuffle) {
        isShuffle = shuffle;
    }
}
