package GUIs;

import DataObjects.CategoriesSingleton;
import DataObjects.Category;
import DataObjects.Choice;
import DataObjects.Question;
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
import java.util.Locale;

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
    private JLabel titleLabel;

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
                new GUI21(getWidth(), getHeight(), categoryComboBox.getSelectedIndex());
            }
        });
    }

    /**
     * This is the constructor used when Adding a question, it adds a different ActionListener to the saveButton
     */
    public GUI32(int width, int height, int categoryIndex) {
        this(width, height);
        categoryComboBox.setSelectedIndex(categoryIndex);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Question new_question = new Question();
                new_question.setQuestionName(questionNameField.getText());
                new_question.setQuestionText(questionTextField.getText());
//                new_question.setDefaultMark(Integer.parseInt(defaultMarkField.getText()));

                Choice choice1 = new Choice();
                choice1.setChoiceText(choice1TextArea.getText());
                choice1.setGrade(grade1ComboBox.getSelectedIndex());
                new_question.addChoice(choice1);

                Choice choice2 = new Choice();
                choice2.setChoiceText(choice2TextArea.getText());
                choice2.setGrade(grade2ComboBox.getSelectedIndex());
                new_question.addChoice(choice2);

                CategoriesSingleton.getInstance().getCategories().get(categoryComboBox.getSelectedIndex()).addQuestion(new_question);

                dispose();
                new GUI21(getWidth(), getHeight(), categoryComboBox.getSelectedIndex());
            }
        });
    }

    /**
     * This is the constructor used when Editing a question, it adds a different ActionListener to the saveButton
     */
    public GUI32(int width, int height, int categoryIndex, int questionIndex) {
        this(width, height);
        categoryComboBox.setSelectedIndex(categoryIndex);

        titleLabel.setText("Editing a Multiple choice question");

        Category category = CategoriesSingleton.getInstance().getCategories().get(categoryIndex);
        Question editingQuestion = category.getQuestions().get(questionIndex);

        questionNameField.setText(editingQuestion.getQuestionName());
        questionTextField.setText(editingQuestion.getQuestionText());
        defaultMarkField.setText(String.valueOf(editingQuestion.getDefaultMark()));

        choice1TextArea.setText(editingQuestion.getChoices().get(0).getChoiceText());
        grade1ComboBox.setSelectedIndex(editingQuestion.getChoices().get(0).getGrade());

        choice2TextArea.setText(editingQuestion.getChoices().get(1).getChoiceText());
        grade2ComboBox.setSelectedIndex(editingQuestion.getChoices().get(1).getGrade());

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editingQuestion.getChoices().clear();

                editingQuestion.setQuestionName(questionNameField.getText());
                editingQuestion.setQuestionText(questionTextField.getText());
//                editingQuestion.setDefaultMark(Integer.parseInt(defaultMarkField.getText()));

                Choice choice1 = new Choice();
                choice1.setChoiceText(choice1TextArea.getText());
                choice1.setGrade(grade1ComboBox.getSelectedIndex());
                editingQuestion.addChoice(choice1);

                Choice choice2 = new Choice();
                choice2.setChoiceText(choice2TextArea.getText());
                choice2.setGrade(grade2ComboBox.getSelectedIndex());
                editingQuestion.addChoice(choice2);

                dispose();
                new GUI21(getWidth(), getHeight(), categoryComboBox.getSelectedIndex());
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
                new GUI32(1024, 768, 0);
            }
        });
    }

    private void createUIComponents() {
        categoryComboBox = new JComboBox(CategoriesSingleton.getInstance().getCategoryNameList());
    }

}
