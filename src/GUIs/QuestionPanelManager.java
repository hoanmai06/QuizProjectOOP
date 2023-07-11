package GUIs;

import Algorithms.FormatHTMLSafe;
import DataObjects.Choice;
import DataObjects.Question;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionPanelManager {
    private JPanel questionIndexPanel;
    private JLabel questionIndex;
    private JLabel answerStatus;
    private JLabel questionMarkLabel;
    private JPanel questionContent;
    private JPanel choicePanel;
    private JPanel questionPanel;
    private JLabel questionText;
    private JButton clearSelectionButton;
    private JPanel answerPanel;
    private JLabel answerLabel;
    JRadioButton[] choiceRadioButtonList;
    JRadioButton answerRadioButton;
    Question question;
    JCheckBox navigationCheckBox;

    public QuestionPanelManager(int index, Question question, JCheckBox navigationCheckBox) {
        this.question = question;
        this.navigationCheckBox = navigationCheckBox;
        // Customize JLabel
        questionIndex.setText(String.valueOf(index));
        questionText.setText("<html>" + FormatHTMLSafe.format(question.getText()) + "</html>");

        answerLabel.setText("<html>The correct answer is: " + FormatHTMLSafe.format(question.getAnswer().getText()) + "</html>");

        // Setup choice panel
        int numberOfChoices = question.getChoices().size();

        choicePanel.setLayout(new GridLayoutManager(numberOfChoices, 1, new Insets(0, 0, 0, 0), -1, 8));

        choiceRadioButtonList = new JRadioButton[numberOfChoices];
        ButtonGroup choiceButtonGroup = new ButtonGroup();
        ActionListener radioButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigationCheckBox.setSelected(true);
            }
        };

        for (int i = 0; i < numberOfChoices; i++) {
            Choice currentChoice = question.getChoices().get(i);
            JRadioButton currentChoiceRadioButton = new JRadioButton("<html>" + FormatHTMLSafe.format(question.getChoices().get(i).getText()) + "</html>");
            choiceRadioButtonList[i] = currentChoiceRadioButton;

            currentChoiceRadioButton.addActionListener(radioButtonListener);

            if (question.getAnswer() == currentChoice) answerRadioButton = currentChoiceRadioButton;

            choiceButtonGroup.add(currentChoiceRadioButton);
            choicePanel.add(currentChoiceRadioButton, new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }
        // Listener
        clearSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choiceButtonGroup.clearSelection();
                navigationCheckBox.setSelected(false);
            }
        });
    }

    public void disableButton() {
        for (JRadioButton radioButton : choiceRadioButtonList) {
            radioButton.setEnabled(false);
        }

        clearSelectionButton.setEnabled(false);
        clearSelectionButton.setVisible(false);
    }

    public void showAnswer() {
        answerPanel.setVisible(true);
    }

    public JPanel getQuestionPanel() {
        return questionPanel;
    }

    public double formatFinishAndGetMark() {
        if (answerRadioButton.isSelected()) {
            answerPanel.setBackground(new Color(0xDEFFDE));
            navigationCheckBox.setForeground(new Color(0xDEFFDE));
            return question.getDefaultMark();
        }
        navigationCheckBox.setForeground(new Color(0xE7F3F5));
        return 0;
    }

    public JRadioButton[] getChoiceRadioButtonList() {
        return choiceRadioButtonList;
    }
}
