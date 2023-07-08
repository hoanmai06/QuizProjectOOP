package GUIs;

import DataObjects.Question;
import DataObjects.Quiz;
import GUIs.GUI61;
import GUIs.GUI62;
import GUIs.GUI63;
import GUIs.GUI65;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI72 extends DefaultJFrame {
    private JPanel guiPanel;
    private JPanel TopBar;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JTextArea attemptTextArea;

    public GUI72(int width, int height, Quiz quiz) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);

        // Set GUI component related to quiz

        // Setup attemptTextArea
        attemptTextArea.getCaret().setVisible(false);
//        attemptTextArea.getCaret().setSelectionVisible(false);

        // Listener
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
                new GUI72(1024, 768, quiz);
            }
        });
    }
}
