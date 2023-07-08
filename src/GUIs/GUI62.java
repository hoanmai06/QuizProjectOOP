package GUIs;

import DataObjects.Question;
import DataObjects.Quiz;
import com.formdev.flatlaf.FlatLightLaf;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI62 extends DefaultJFrame {

    private JPanel guiPanel;
    private JPanel TopBar;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JLabel directoryLabel;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JLabel quizNameLabel;
    private JButton SAVEButton;
    private JTextField textField1;
    private JCheckBox shuffleCheckBox;
    private JButton addButton;
    private JButton fromQuestionBankButton;
    private JButton aNewQuestionButton;
    private JButton aRandomQuestionButton;
    private JPanel popupPanel;
    private JPanel addPanel;
    private JPanel ZeroVerticalGap;
    private JTable questionTable;
    private JScrollPane tableScrollPane;
    private JPanel layeredPaneContainer;

    public GUI62(int width, int height, Quiz quiz) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);

        // Set GUI component related to quiz
        quizNameLabel.setText("Editing quiz: " + quiz.getName());

        // Setup questionTable
        String[] columnNames = {"Index", "Question name", "Delete button", "Mark"};
        DefaultTableModel tableModel = new DefaultTableModel(getQuestionTableData(quiz), columnNames);

        questionTable.setModel(tableModel);

        // Listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupPanel.setVisible(!popupPanel.isVisible());
            }
        });
        fromQuestionBankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUI63(getWidth(), getHeight(), quiz);
                dispose();
            }
        });
        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUI61(getWidth(), getHeight(), quiz);
                dispose();
            }
        });
        aRandomQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUI65(getWidth(), getHeight(), quiz);
                dispose();
            }
        });
    }

    private Object[][] getQuestionTableData(Quiz quiz) {
        int numberOfQuestions = quiz.getQuestions().size();

        Object[][] questionTableData = new Object[numberOfQuestions][4];
        for (int i = 0; i < numberOfQuestions; i++) {
            questionTableData[i] = new Object[]{i, quiz.getQuestions().get(i).getQuestionText(), "", "1"};
        }
        return questionTableData;
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

        // Test question
        Question question = new Question();
        question.setQuestionText("Hello World");
        // Test quiz
        Quiz quiz = new Quiz();
        quiz.setName("Hello World");
        quiz.setTimeLimit(10);
        quiz.setTimeLimitType(Quiz.TIME_TYPE_MINUTE);
        quiz.addQuestion(question);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI62(1024, 768, quiz);
            }
        });
    }
}
