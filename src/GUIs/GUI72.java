package GUIs;

import Algorithms.QuizExporter;
import DataObjects.Question;
import DataObjects.Quiz;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

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
    private JButton exportButton;
    private JPanel exportPanel;
    private JCheckBox encryptPDFCheckBox;
    private JButton CHOOSEADIRECTORYButton;
    private JPanel passwordPanel;
    private JPasswordField passwordField;

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
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportPanel.setVisible(!exportPanel.isVisible());
            }
        });
        encryptPDFCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordPanel.setVisible(encryptPDFCheckBox.isSelected());
            }
        });
        CHOOSEADIRECTORYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog(GUI72.this, "Export" , FileDialog.SAVE);
                fileDialog.setFile("Untitled.pdf");
                fileDialog.setVisible(true);

                if (fileDialog.getFile() != null) {
                    try {
                        char[] password = passwordField.getPassword();

                        QuizExporter quizExporter = new QuizExporter();
                        quizExporter.exportQuizToPDF(quiz, fileDialog.getDirectory() + fileDialog.getFile(), encryptPDFCheckBox.isSelected(), new String(password));

                        Arrays.fill(password, '0');
                        JOptionPane.showMessageDialog(GUI72.this, "Export successfully", "Export", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(GUI72.this, "Export unsuccessfully", "Export", JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                }
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
