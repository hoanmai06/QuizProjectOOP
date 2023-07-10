package GUIs;

import DataObjects.CategoriesSingleton;
import DataObjects.Choice;
import DataObjects.Question;
import com.formdev.flatlaf.FlatLightLaf;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI32Add extends DefaultJFrame {
    private JPanel TopBar;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JPanel guiPanel;
    private JComboBox categoryComboBox;
    private JTextField questionNameField;
    private JTextArea questionTextField;
    private JButton BLANKSFOR3MOREButton;
    private JScrollPane mainScrollPane;
    private JButton cancelButton;
    private JButton saveButton;
    private JPanel choicePanelContainer;
    private JTextField defaultMarkField;
    private JLabel titleLabel;
    private JButton saveAndContinueButton;
    private ArrayList<ChoicePanelManager> choicePanelManagers = new ArrayList<>();

    public GUI32Add(int width, int height, int categoryIndex) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);

        mainScrollPane.getVerticalScrollBar().setUnitIncrement(9);

        // Setup categoryComboBox
        categoryComboBox.setSelectedIndex(categoryIndex);

        // Setup choicePanelContainer
        choicePanelContainer.setLayout(new GridLayoutManager(32, 2, new Insets(0, 0, 0, 10), -1, -1));
        choicePanelContainer.setBackground(new Color(-1));

        // Restore addingQuestion (if needed)
        Question addingQuestion = CategoriesSingleton.getInstance().getAddingQuestion();
        if (addingQuestion != null) {
            questionNameField.setText(addingQuestion.getName());
            questionTextField.setText(addingQuestion.getText());

            // Fill choices with the choices of addingQuestion
            // Nếu choices.size() < 2 thì vẫn phải có 2 ô trống điền choice nên xử lý riêng
            ArrayList<Choice> choices = addingQuestion.getChoices();
            for (int i = 0; i < 2; i++) {
                if (i >= choices.size()) {
                    ChoicePanelManager newChoicePanelManager = new ChoicePanelManager(i + 1);
                    choicePanelManagers.add(newChoicePanelManager);

                    choicePanelContainer.add(newChoicePanelManager.getPanel(), new GridConstraints(i, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
                } else {
                    Choice choice = choices.get(i);

                    ChoicePanelManager newChoicePanelManager = new ChoicePanelManager(i + 1);
                    choicePanelManagers.add(newChoicePanelManager);

                    newChoicePanelManager.setChoiceText(choice.getText());
                    newChoicePanelManager.setGrade(choice.getGrade());

                    choicePanelContainer.add(newChoicePanelManager.getPanel(), new GridConstraints(i, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
                }
            }

            for (int i = 2; i < choices.size(); i++) {
                Choice choice = choices.get(i);

                ChoicePanelManager newChoicePanelManager = new ChoicePanelManager(i + 1);
                choicePanelManagers.add(newChoicePanelManager);

                newChoicePanelManager.setChoiceText(choice.getText());
                newChoicePanelManager.setGrade(choice.getGrade());

                choicePanelContainer.add(newChoicePanelManager.getPanel(), new GridConstraints(i, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
            }
        } else {
            choicePanelManagers.add(new ChoicePanelManager(1));
            choicePanelContainer.add(choicePanelManagers.get(0).getPanel(), new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
            choicePanelManagers.add(new ChoicePanelManager(2));
            choicePanelContainer.add(choicePanelManagers.get(1).getPanel(), new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
        }

        BLANKSFOR3MOREButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numberOfChoicePanel = choicePanelManagers.size();

                for (int i = numberOfChoicePanel; i < numberOfChoicePanel + 3; i++) {
                    choicePanelManagers.add(new ChoicePanelManager(i + 1));
                    choicePanelContainer.add(choicePanelManagers.get(i).getPanel(), new GridConstraints(i, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));

                    if (i == 31) {
                        BLANKSFOR3MOREButton.setEnabled(false);
                        break;
                    }
                }

                choicePanelContainer.revalidate();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Question newQuestion = new Question();
                newQuestion.setName(questionNameField.getText());
                newQuestion.setText(questionTextField.getText());

                for (ChoicePanelManager choicePanelManager : choicePanelManagers) {
                    if (choicePanelManager.getChoiceText().equals("")) continue;

                    Choice choice = new Choice();

                    choice.setText(choicePanelManager.getChoiceText());
                    choice.setGrade(choicePanelManager.getGrade());

                    newQuestion.addChoice(choice);
                }

                CategoriesSingleton.getInstance().getCategories().get(categoryComboBox.getSelectedIndex()).addQuestion(newQuestion);
                CategoriesSingleton.getInstance().setAddingQuestion(null);

                dispose();
                new GUI21(getWidth(), getHeight(), categoryComboBox.getSelectedIndex());
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GUI21(getWidth(), getHeight(), categoryComboBox.getSelectedIndex());
            }
        });
        saveAndContinueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Question editingQuestion = new Question();
                editingQuestion.setName(questionNameField.getText());
                editingQuestion.setText(questionTextField.getText());

                for (ChoicePanelManager choicePanelManager : choicePanelManagers) {
                    if (choicePanelManager.getChoiceText().equals("")) continue;

                    Choice choice = new Choice();

                    choice.setText(choicePanelManager.getChoiceText());
                    choice.setGrade(choicePanelManager.getGrade());

                    editingQuestion.addChoice(choice);
                }

                CategoriesSingleton.getInstance().setAddingQuestion(editingQuestion);
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
                new GUI32Add(1024, 768, 0);
            }
        });
    }

    private void createUIComponents() {
        categoryComboBox = new JComboBox(CategoriesSingleton.getInstance().getCategoryNameList());
    }

}
