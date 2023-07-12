package GUIs;

import DataObjects.Question;
import DataObjects.Quiz;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUI72 extends DefaultJFrame {
    private JPanel guiPanel;
    private JPanel TopBar;
    private JTextArea attemptTextArea;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JButton cancelButton;
    private JButton startButton;

    public GUI72(int width, int height, Quiz quiz) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);

        // Set GUI component related to quiz

        // Setup attemptTextArea
        attemptTextArea.getCaret().setVisible(false);
//        attemptTextArea.getCaret().setSelectionVisible(false);

        // Listener
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUI61(getWidth(), getHeight(), quiz);
                dispose();
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quiz.getPreviousAttemptList().add("Never submitted");
                try {
                    new GUI73(getWidth(), getHeight(), quiz);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });
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
        question.setText("Hello World");
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
