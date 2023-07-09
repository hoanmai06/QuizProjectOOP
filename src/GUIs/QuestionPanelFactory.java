package GUIs;

import DataObjects.Question;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JTextArea questionText;
    private JRadioButton radioButton1;

    public QuestionPanelFactory(int index, Question question) {
        // Customize JLabel
        questionIndex.setText(String.valueOf(index));
        questionText.setText(question.getQuestionText());

        // Setup choice panel
        int numberOfChoices = question.getChoices().size();

        choicePanel.setLayout(new GridLayoutManager(numberOfChoices, 2, new Insets(0, 0, 0, 0), 0, 8));

        JRadioButton[] choiceRadioButtonList = new JRadioButton[numberOfChoices];
        ButtonGroup choiceButtonGroup = new ButtonGroup();
        for (int i = 0; i < numberOfChoices; i++) {
            choiceRadioButtonList[i] = new JRadioButton();
            choiceButtonGroup.add(choiceRadioButtonList[i]);
            choicePanel.add(choiceRadioButtonList[i], new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

            // Choice text renderer
            final JTextArea choiceTextArea = new JTextArea();
            choiceTextArea.setBackground(new Color(-1575947));
            choiceTextArea.setDisabledTextColor(new Color(-16777216));
            choiceTextArea.setEditable(false);
            choiceTextArea.setEnabled(false);
            choiceTextArea.setLineWrap(true);
            choiceTextArea.setText(question.getChoices().get(i).getChoiceText());
            choiceTextArea.setWrapStyleWord(true);
            choicePanel.add(choiceTextArea, new GridConstraints(i, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

            int finalI = i;
            choiceTextArea.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    choiceRadioButtonList[finalI].setSelected(true);
                }
            });

            // Test code
            choiceTextArea.setBorder(new LineBorder(Color.BLACK));
        }
    }

    public JPanel getQuestionPanel() {
        return questionPanel;
    }

}
