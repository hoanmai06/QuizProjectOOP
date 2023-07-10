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
    private JButton clearSelectionButton;
    JRadioButton[] choiceRadioButtonList;

    public QuestionPanelManager(int index, Question question, JCheckBox navigationCheckBox) {
        // Customize JLabel
        questionIndex.setText(String.valueOf(index));
        questionText.setText("<html>%s</html>".formatted(question.getText()));

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
            choiceRadioButtonList[i] = new JRadioButton("<html>" + question.getChoices().get(i).getText() + "</html>");

            choiceRadioButtonList[i].addActionListener(radioButtonListener);

            choiceButtonGroup.add(choiceRadioButtonList[i]);
            choicePanel.add(choiceRadioButtonList[i], new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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

    public JPanel getQuestionPanel() {
        return questionPanel;
    }

    public JRadioButton[] getChoiceRadioButtonList() {
        return choiceRadioButtonList;
    }
}
