package GUIs;

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
    private JLabel questionMark;
    private JPanel questionContent;
    private JPanel choicePanel;
    private JPanel questionPanel;
    private JLabel questionText;
    JRadioButton[] choiceRadioButtonList;

    public QuestionPanelManager(int index, Question question, ActionListener actionWhenAnswered) {
        // Customize JLabel
        questionIndex.setText(String.valueOf(index));
        questionText.setText("<html>%s</html>".formatted(question.getQuestionText()));

        // Setup choice panel
        int numberOfChoices = question.getChoices().size();

        choicePanel.setLayout(new GridLayoutManager(numberOfChoices, 1, new Insets(0, 0, 0, 0), -1, 8));

        choiceRadioButtonList = new JRadioButton[numberOfChoices];
        ButtonGroup choiceButtonGroup = new ButtonGroup();
        for (int i = 0; i < numberOfChoices; i++) {
            choiceRadioButtonList[i] = new JRadioButton("<html>" + question.getChoices().get(i).getChoiceText() + "</html>");
            choiceRadioButtonList[i].addActionListener(actionWhenAnswered);
            choiceButtonGroup.add(choiceRadioButtonList[i]);
            choicePanel.add(choiceRadioButtonList[i], new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }
        // Listener
    }

    public JPanel getQuestionPanel() {
        return questionPanel;
    }

    public JRadioButton[] getChoiceRadioButtonList() {
        return choiceRadioButtonList;
    }
}
