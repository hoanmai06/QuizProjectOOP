package GUI;

import DataObject.Choice;
import DataObject.Question;
import DataObject.Singleton;
import com.formdev.flatlaf.FlatLightLaf;
import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI32 extends DefaultJFrame {
    private JPanel TopBar;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JPanel guiPanel;
    private JComboBox categoryComboBox;
    private JTextField questionNameField;
    private JTextArea questionTextField;
    private JPanel choicePanel1;
    private JComboBox grade1ComboBox;
    private JPanel choicePanel2;
    private JButton BLANKSFOR3MOREButton;
    private JScrollPane mainScrollPane;
    private JButton cancelButton;
    private JButton saveButton;
    private JPanel choicePanel;
    private JTextField defaultMarkField;
    private JTextArea choice1TextArea;
    private JTextArea choice2TextArea;
    private JComboBox grade2ComboBox;

    public GUI32(int width, int height) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);

        mainScrollPane.getVerticalScrollBar().setUnitIncrement(9);

        BLANKSFOR3MOREButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choicePanel.add(new ChoicePanelFactory().getJPanel(3), new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
                choicePanel.add(new ChoicePanelFactory().getJPanel(4), new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
                choicePanel.add(new ChoicePanelFactory().getJPanel(5), new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
                SwingUtilities.updateComponentTreeUI(choicePanel);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GUI21(getWidth(), getHeight());
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Question new_question = new Question();
                new_question.setQuestionName(questionNameField.getText());
                new_question.setQuestionText(questionTextField.getText());
                new_question.setDefaultMark(Integer.parseInt(defaultMarkField.getText()));

                Choice choice1 = new Choice();
                choice1.setChoiceText(choice1TextArea.getText());
                choice1.setGrade(grade1ComboBox.getSelectedIndex());
                new_question.addChoice(choice1);

                Choice choice2 = new Choice();
                choice2.setChoiceText(choice2TextArea.getText());
                choice2.setGrade(grade2ComboBox.getSelectedIndex());
                new_question.addChoice(choice2);

                Singleton.getInstance().getCategories().get(categoryComboBox.getSelectedIndex()).addQuestion(new_question);

                dispose();
                new GUI21(getWidth(), getHeight());
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

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI32(1024, 768);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        //categoryComboBox
        categoryComboBox = new JComboBox(Singleton.getInstance().getCategoryNameList());
    }
}
