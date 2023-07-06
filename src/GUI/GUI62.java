package GUI;

import DataObject.Quiz;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
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
    private JButton aNewQuestionButton;
    private JButton fromQuestionBankButton;
    private JButton aRandomQuestionButton;
    private JPanel popupPanel;
    private JPanel addPanel;
    private JPanel ZeroVerticalGap;
    private JTable questionTable;

    public GUI62(int width, int height, Quiz quiz) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);

        // Set GUI component related to quiz
        quizNameLabel.setText("Editing quiz: " + quiz.getName());

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupPanel.setVisible(!popupPanel.isVisible());
            }
        });
        fromQuestionBankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUI63(getWidth(), getHeight());
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

        // Test quiz
        Quiz quiz = new Quiz();
        quiz.setName("Hello World");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI62(1024, 768, quiz);
            }
        });
    }
}
