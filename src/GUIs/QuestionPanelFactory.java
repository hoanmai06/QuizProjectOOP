package GUIs;

import DataObjects.Question;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class QuestionPanelFactory {
    private JPanel questionIndexPanel;
    private JLabel questionIndex;
    private JLabel answerStatus;
    private JLabel questionMark;
    private JPanel questionContent;
    private JPanel choicePanel;
    private JPanel questionPanel;
    private JLabel questionText;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;

    public QuestionPanelFactory(int index, Question question) {
        // Customize JLabel
        questionIndex.setText(String.valueOf(index));
        questionText.setText(question.getQuestionText());

        // Setup choice panel
        int numberOfChoices = question.getChoices().size();

        choicePanel.setLayout(new GridLayoutManager(numberOfChoices, 1, new Insets(0, 0, 0, 0), -1, -1));

        JRadioButton[] choiceRadioButtonList = new JRadioButton[numberOfChoices];
        ButtonGroup choiceButtonGroup = new ButtonGroup();
        for (int i = 0; i < numberOfChoices; i++) {
            choiceRadioButtonList[i] = new JRadioButton(question.getChoices().get(i).getChoiceText());
            choiceButtonGroup.add(choiceRadioButtonList[i]);
            choicePanel.add(choiceRadioButtonList[i], new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }
    }

    public JPanel getQuestionPanel() {
        return questionPanel;
    }

}
