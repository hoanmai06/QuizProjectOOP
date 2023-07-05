package GUI;

import DataObject.Quiz;
import DataObject.QuizzesSingleton;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI51 extends DefaultJFrame {
    private JPanel guiPanel;
    private JScrollPane mainScrollPane;
    private JTextField nameTextField;
    private JTextArea descriptionTextArea;
    private JCheckBox displayDesciptionOnCourseCheckBox;
    private JComboBox comboBox1;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JCheckBox enableCheckBox;
    private JCheckBox enableCheckBox1;
    private JComboBox comboBox2;
    private JFormattedTextField formattedTextField1;
    private JButton createButton;
    private JButton cancelButton;
    private JComboBox comboBox5;
    private JPanel TopBar;

    public GUI51(int width, int height) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUI11(getWidth(), getHeight());
                dispose();
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Quiz newQuiz = new Quiz();
                newQuiz.setName(nameTextField.getText());
                newQuiz.setDescription(descriptionTextArea.getText());

                QuizzesSingleton.getInstance().getQuizzes().add(newQuiz);

                new GUI11(getWidth(), getHeight());
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

        GUI51 gui51 = new GUI51(1024, 768);
    }
}
