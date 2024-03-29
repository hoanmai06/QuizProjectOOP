package GUIs;

import DataObjects.Quiz;
import DataObjects.QuizzesSingleton;
import com.formdev.flatlaf.FlatLightLaf;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;

public class GUI51 extends DefaultJFrame {
    private JPanel guiPanel;
    private JScrollPane mainScrollPane;
    private JTextField nameTextField;
    private JTextArea descriptionTextArea;
    private JCheckBox displayDesciptionOnCourseCheckBox;
    private JComboBox dateOpen;
    private JComboBox monthOpen;
    private JComboBox yearOpen;
    private JCheckBox openCheckBox;
    private JCheckBox closeCheckBix;
    private JComboBox timeLimitType;
    private JFormattedTextField timeLimitField;
    private JButton createButton;
    private JButton cancelButton;
    private JComboBox comboBox5;
    private JPanel TopBar;
    private JComboBox hourOpen;
    private JComboBox minuteOpen;
    private JComboBox dateClose;
    private JComboBox monthClose;
    private JComboBox yearClose;
    private JComboBox hourClose;
    private JComboBox minuteClose;
    private JCheckBox timeLimitCheckBox;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;

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

                boolean isValidCreat = true;
                if(timeLimitField.isEnabled() && !timeLimitField.getText().isEmpty()) {
                    double time = Double.parseDouble(timeLimitField.getText());
                    if(time <= 0) {
                        isValidCreat = false;
                        JFrame FBframe = new JFrame("Time_Limit_Problems");
                        JOptionPane.showMessageDialog(FBframe, "Time limit must be positive number!", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        if ((timeLimitType.getSelectedIndex() == 1)) {
                            if (time % 1 == 0) {
                                newQuiz.setTimeLimitType(1);
                                newQuiz.setTimeLimit((int) time);
                            } else {
                                newQuiz.setTimeLimitType(0);
                                newQuiz.setTimeLimit((int) (time * 60));
                            }
                        } else {
                            newQuiz.setTimeLimitType(0);
                            newQuiz.setTimeLimit((int) time);
                        }
                    }
                }
                else {
                    if(timeLimitField.isEnabled() && timeLimitField.getText().isEmpty()) {
                        isValidCreat = false;
                        JFrame FBframe = new JFrame("Time_Limit_Problems");
                        JOptionPane.showMessageDialog(FBframe, "Time limit has not be set!", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        newQuiz.setTimeLimitType(0);
                        newQuiz.setTimeLimit(-1);
                    }
                }

                if(isValidCreat) {

                    QuizzesSingleton.getInstance().getQuizzes().add(newQuiz);

                    new GUI11(getWidth(), getHeight());
                    dispose();
                }

            }
        });

        openCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean checkBoxStatus = e.getStateChange() == ItemEvent.SELECTED;
                dateOpen.setEnabled(checkBoxStatus);
                hourOpen.setEnabled(checkBoxStatus);
                minuteOpen.setEnabled(checkBoxStatus);
                monthOpen.setEnabled(checkBoxStatus);
                yearOpen.setEnabled(checkBoxStatus);
            }
        });
        closeCheckBix.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean checkBoxStatus = e.getStateChange() == ItemEvent.SELECTED;
                dateClose.setEnabled(checkBoxStatus);
                hourClose.setEnabled(checkBoxStatus);
                minuteClose.setEnabled(checkBoxStatus);
                monthClose.setEnabled(checkBoxStatus);
                yearClose.setEnabled(checkBoxStatus);
            }
        });
        timeLimitCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean checkBoxStatus = e.getStateChange() == ItemEvent.SELECTED;
                timeLimitField.setEnabled(checkBoxStatus);
                timeLimitType.setEnabled(checkBoxStatus);

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
