package GUIs;

import DataObjects.Choice;
import DataObjects.Question;
import DataObjects.Quiz;
import com.formdev.flatlaf.FlatLightLaf;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI73 extends DefaultJFrame {
    private JPanel TopBar;
    private JPanel guiPanel;
    private JPanel questionPanelContainer;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JScrollPane questionsScrollPane;
    private JPanel navigationCheckBoxPanel;
    private JButton finishAttemptButton;
    private JScrollPane navigationScrollPane;
    private QuestionPanelManager[] questionPanelManager;

    public GUI73(int width, int height, Quiz quiz) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);

        // Set GUI component related to quiz

        // Setup navigationCheckBoxPanel
        navigationScrollPane.getVerticalScrollBar().setUnitIncrement(4);
        int numberOfQuestion = quiz.getQuestions().size();

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JCheckBox source = (JCheckBox) e.getSource();
                int sourceIndex = Integer.parseInt(source.getActionCommand());

                questionsScrollPane.getVerticalScrollBar().setValue(questionScrollBarValue(sourceIndex));
            }
        };

        navigationCheckBoxPanel.setLayout(new GridLayoutManager(numberOfQuestion/5 + 1, 6, new Insets(0, 0, 0, 0), 0, 4));
        JCheckBox[] navigationCheckBoxes = new JCheckBox[numberOfQuestion];
        for (int i = 0; i < numberOfQuestion; i++) {
            navigationCheckBoxes[i] = new JCheckBox(String.valueOf(i+1));
            navigationCheckBoxes[i].setEnabled(false);
            navigationCheckBoxes[i].setActionCommand(String.valueOf(i));
            navigationCheckBoxes[i].addMouseListener(mouseListener);

            navigationCheckBoxPanel.add(navigationCheckBoxes[i], new GridConstraints(i/5, i%5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }

        // Insert all questionPanel into questionPanelContainer, which is the viewport.
        questionPanelContainer.setLayout(new GridLayoutManager(numberOfQuestion + 1, 1, new Insets(0, 0, 0, 0), -1, 20));

        questionPanelManager = new QuestionPanelManager[numberOfQuestion];
        for (int i = 0; i < numberOfQuestion; i++) {
            questionPanelManager[i] = new QuestionPanelManager(i + 1, quiz.getQuestions().get(i), navigationCheckBoxes[i]);
            questionPanelContainer.add(questionPanelManager[i].getQuestionPanel(), new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }

        // Insert vertical spacer at the bottom of questionPaneContainer to push all questionPanel up
        final Spacer spacer = new Spacer();
        questionPanelContainer.add(spacer, new GridConstraints(numberOfQuestion, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));

        // Increase scrollPaneSpeed
        questionsScrollPane.getVerticalScrollBar().setUnitIncrement(6);

        // Listener
    }

    public int questionScrollBarValue(int index) {
        int scrollBarValue = 0;

        for (int i = 0; i < index; i++) {
            scrollBarValue += questionPanelManager[i].getQuestionPanel().getHeight();
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

        // Test question3
        Question question3 = new Question();
        question3.addChoice(choice11);

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
                new GUI73(1024, 768, quiz);
            }
        });
    }
}