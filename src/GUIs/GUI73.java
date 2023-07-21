package GUIs;

import Algorithms.Countdown;
import DataObjects.Choice;
import DataObjects.Question;
import DataObjects.Quiz;
import com.formdev.flatlaf.FlatLightLaf;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GUI73 extends DefaultJFrame {
    private JPanel TopBar;
    private JPanel guiPanel;
    private JPanel questionPanelContainer;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JScrollPane questionsScrollPane;
    private JPanel navigationEntitiesPanel;
    private JButton finishAttemptButton;
    private JScrollPane navigationScrollPane;
    private JButton finishReviewButton;
    private JPanel timerPanel;

    private JLabel countdown;
    private JTable summaryTable;
    private JLabel labelClock;
    private JLabel directoryLabel;
    private QuestionPanelManager[] questionPanelManagers;

    private int timeConsume;

    public GUI73(int width, int height, Quiz quiz) throws IOException {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);
        directoryLabel.setText("Home / My courses / Thi cuối kỳ / General / " + quiz.getName() + " / Preview");


        // Set GUI component related to quiz

        // Setup navigationEntitiesPanel
        navigationScrollPane.getVerticalScrollBar().setUnitIncrement(4);
        int numberOfQuestion = quiz.getQuestions().size();
        int ePL = GUIConfig.navigationEntitiesPerLine;

        navigationEntitiesPanel.setLayout(new GridLayoutManager(numberOfQuestion/ePL + 1, ePL + 1, new Insets(0, 0, 0, 0), 5, 4));
        NavigationEntityManager[] navigationEntityManagers = new NavigationEntityManager[numberOfQuestion];
        for (int i = 0; i < numberOfQuestion; i++) {
            int finalI = i;
            MouseListener mouseListener = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JPanel source = (JPanel) e.getSource();
                    questionsScrollPane.getVerticalScrollBar().setValue(questionScrollBarValue(finalI));
                }
            };

            navigationEntityManagers[i] = new NavigationEntityManager(i + 1);
            navigationEntityManagers[i].getEntity().addMouseListener(mouseListener);

            navigationEntitiesPanel.add(navigationEntityManagers[i].getEntity(), new GridConstraints(i/ePL, i%ePL, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }
        final Spacer horizontalSpacer = new Spacer();
        navigationEntitiesPanel.add(horizontalSpacer, new GridConstraints(0, ePL, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));


        // Insert all questionPanel into questionPanelContainer.
        questionPanelContainer.setLayout(new GridLayoutManager(numberOfQuestion + 1, 1, new Insets(0, 0, 0, 0), -1, 20));

        questionPanelManagers = new QuestionPanelManager[numberOfQuestion];
        for (int i = 0; i < numberOfQuestion; i++) {
            questionPanelManagers[i] = new QuestionPanelManager(i + 1, quiz.getQuestions().get(i), navigationEntityManagers[i], quiz.isShuffle());
            questionPanelContainer.add(questionPanelManagers[i].getQuestionPanel(), new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }

        // Insert vertical spacer at the bottom of questionPaneContainer to push all questionPanel up
        final Spacer verticalSpacer = new Spacer();
        questionPanelContainer.add(verticalSpacer, new GridConstraints(numberOfQuestion, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));

        // Increase scrollPaneSpeed
        questionsScrollPane.getVerticalScrollBar().setUnitIncrement(8);

        // Creat count down clock
        Timer timeTaken = new Timer();
        timeConsume = 0;
        timeTaken.schedule(new TimerTask() {
            public void run() {
                timeConsume ++;
            }
        }, 0, 1000);

        class countdownAndFinish extends Countdown {
            @Override
            public void showCountDown(JLabel label, int seconds) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    int i = seconds;
                    public void run() {
                        int hour = i / 3600;
                        int minute = (i % 3600) / 60;
                        int second = i % 60;
                        label.setText("Time left: " + String.format("%02d:%02d:%02d", hour, minute, second));
                        if (i <= 0) {
                            timer.cancel();
                            finishAttemptButton.doClick();
                        }
                        i--;
                    }
                }, 0, 1000);
            }
        }

        int time;
        if(quiz.getTimeLimitType()==1)
            time = quiz.getTimeLimit()*3600;
        else
            time = quiz.getTimeLimit()*60;
        if(time > 0) {
            countdownAndFinish countdownClock = new countdownAndFinish();
            countdownClock.showCountDown(labelClock, time);
        }
        else timerPanel.setVisible(false);


        // Listener
        finishAttemptButton.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {

                int timeTotal = timeConsume;
                String timeTakenArea = new String();
                String hUnit = new String(" hrs ");
                String mUnit = new String(" mins ");
                String sUnit = new String(" secs ");
                int h = timeTotal / 3600;
                int m = (timeTotal % 3600) / 60;
                int s = timeTotal % 60;
                if(h==1) hUnit = " hr ";
                if(m==1) mUnit = " min ";
                if(s==1) sUnit = " sec ";

                if(h>0) {
                    timeTakenArea = String.format(h + hUnit + m + mUnit + s + sUnit);
                }
                else {
                    if(m>0)
                        timeTakenArea = String.format(m + mUnit + s + sUnit);
                    else
                        timeTakenArea = String.format(s + sUnit);

                }

                // Disable finishAttempt button
                finishAttemptButton.setEnabled(false);
                finishAttemptButton.setVisible(false);

                // Hide time:
                timerPanel.setVisible(false);

                // Disable all button, show answer, calculate mark and grade
                double mark = 0;
                for (QuestionPanelManager questionPanelManager : questionPanelManagers) {
                    questionPanelManager.disableButton();
                    questionPanelManager.showAnswer();
                    mark += questionPanelManager.formatFinishAndGetMark();
                }

                double grade = quiz.getMaxGrade() * mark / quiz.getTotalMark();

                // Display summaryTable
                DefaultTableCellRenderer firstColumnRenderer = new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        setFont(new Font("Inter", Font.BOLD, 12));
                        return this;
                    }
                };

                firstColumnRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                firstColumnRenderer.setForeground(new Color(0xBB3630));

                summaryTable.setVisible(true);
                summaryTable.setModel(new DefaultTableModel(
                        new Object[][]{
                                {"State", "Finished"},
                                {"Time taken", timeTakenArea},
                                {"Marks", "%.2f/%.2f".formatted(mark, quiz.getTotalMark())},
                                {"Grade", "%.2f out of %.2f (%.2f".formatted(grade, quiz.getMaxGrade(), grade/quiz.getMaxGrade()*100) + " %)"}
                        },
                        new String[]{"0", "1"}
                ));
                summaryTable.getColumn("0").setCellRenderer(firstColumnRenderer);
                summaryTable.getColumnModel().getColumn(0).setMinWidth(150);
                summaryTable.getColumnModel().getColumn(0).setMaxWidth(150);
                summaryTable.setRowHeight(20);
                summaryTable.setEnabled(false);

                // Enable finishReview button
                finishReviewButton.setVisible(true);

                // Set currentAttempt status to submitted
                quiz.getPreviousAttemptList().set(quiz.getPreviousAttemptList().size() - 1, "%.2f/%.2f".formatted(grade, quiz.getMaxGrade()));

            }
        });
        finishReviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUI11(getWidth(), getHeight());
                dispose();
            }
        });
    }

    public int questionScrollBarValue(int index) {
        int scrollBarValue = 0;

        for (int i = 0; i < index; i++) {
            scrollBarValue += questionPanelManagers[i].getQuestionPanel().getHeight();
            scrollBarValue += 20;
        }

        return Math.min(scrollBarValue, questionsScrollPane.getVerticalScrollBar().getMaximum());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        if (defaults.get("Table.alternateRowColor") == null)
            defaults.put("Table.alternateRowColor", new Color(240, 240, 240));

        // Test choice1
        Choice choice1 = new Choice();
        choice1.setText("Hello. I am choice1");
        Choice choice2 = new Choice();
        choice2.setText("Nice to meet you. My name is choice2");

        // Test question1
        Question question1 = new Question();
        question1.setText("Hello World");
        question1.getChoices().add(choice1);
        question1.getChoices().add(choice2);
        question1.addAnswer(choice1);

        // Test choice1
        Choice choice11 = new Choice();
        choice11.setText("Hello. I am choice1");
        Choice choice12 = new Choice();
        choice12.setText("Nice to meet you. My name is choice2 Nice to meet you. My name is choice2 Nice to meet you. My name is choice2");

        // Test question1
        Question question2 = new Question();
        question2.setText("Hello World Hello World Hello World Hello World Hello World Hello World Hello World Hello World v Hello World Hello World");
        question2.getChoices().add(choice11);
        question2.getChoices().add(choice12);
        question2.getChoices().add(choice12);
        question2.getChoices().add(choice12);
        question2.addAnswer(choice11);

        // Test question3
        Question question3 = new Question();
        question3.addChoice(choice11);
        question3.addAnswer(choice11);

        // Test quiz
        Quiz quiz = new Quiz();
        quiz.setName("Hello World");
        quiz.setTimeLimit(10);
        quiz.setTimeLimitType(Quiz.TIME_TYPE_MINUTE);
        quiz.addQuestion(question1);
        quiz.addQuestion(question2);
        quiz.addQuestion(question3);
        quiz.addQuestion(question1);
        quiz.addQuestion(question2);
        quiz.addQuestion(question3);
        quiz.addQuestion(question1);
        quiz.addQuestion(question2);
        quiz.addQuestion(question3);
        quiz.addQuestion(question1);
        quiz.addQuestion(question2);
        quiz.addQuestion(question3);
        quiz.addQuestion(question1);
        quiz.addQuestion(question2);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GUI73(1024, 768, quiz);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}