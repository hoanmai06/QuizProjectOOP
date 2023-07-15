package GUIs;

import Algorithms.FormatHTMLSafe;
import DataObjects.Choice;
import DataObjects.Question;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class QuestionPanelManager {
    private JPanel questionIndexPanel;
    private JLabel questionIndex;
    private JLabel answerStatus;
    private JLabel questionMarkLabel;
    private JPanel questionContent;
    private JPanel choicePanel;
    private JLabel choiceImage;                                     // neu co anh trong choice
    private JPanel questionPanel;
    private JLabel questionText;
    private JButton clearSelectionButton;
    private JPanel answerPanel;
    private JLabel answerLabel;
    JRadioButton[] choiceRadioButtonList;
    JRadioButton answerRadioButton;
    Question question;
    NavigationEntityManager navigationEntity;
    ButtonGroup choiceButtonGroup;

    public QuestionPanelManager(int index, Question question, NavigationEntityManager navigationEntity) throws IOException {
        this.question = question;
        this.navigationEntity = navigationEntity;
        // Customize JLabel
        questionIndex.setText(String.valueOf(index));
        questionText.setText("<html>" + FormatHTMLSafe.format(question.getText()) + "</html>");

        // Check if the questionText contains image and show the image in a Label
        if(question.getq_ImageData()!=null) {
            ImageIcon image = toImageIcon(question.getq_ImageData());
            JLabel questionImage = new JLabel(image);
            questionContent.add(questionImage, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }

        answerLabel.setText("<html>The correct answer is: " + FormatHTMLSafe.format(question.getAnswer().getText()) + "</html>");

        // Check if the answer contains image and add a Label of it to answerPanel
        if(question.getAnswer().getc_ImageData()!= null) {
            ImageIcon image = toImageIcon(question.getAnswer().getc_ImageData());
            JLabel answerImage = new JLabel(image);
            answerPanel.add(answerImage, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }

        // Setup choice panel
        int numberOfChoices = question.getChoices().size();

        choicePanel.setLayout(new GridLayoutManager(numberOfChoices, 1, new Insets(0, 0, 0, 0), -1, 8));

        choiceRadioButtonList = new JRadioButton[numberOfChoices];
        choiceButtonGroup = new ButtonGroup();
        ActionListener radioButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigationEntity.setColor(GUIConfig.NAVIGATION_SELECTED);
                answerStatus.setText("<html>Answered<br>&nbsp;</html>");
            }
        };

        for (int i = 0; i < numberOfChoices; i++) {

            // Create one JPanel for each choice
            JPanel thisChoice = new JPanel();
            thisChoice.setBackground(new Color(0xE7F3F5));
            BoxLayout box = new BoxLayout(thisChoice, BoxLayout.Y_AXIS); // xep cac components theo chieu doc
            thisChoice.setLayout(box);

            Choice currentChoice = question.getChoices().get(i);
            JRadioButton currentChoiceRadioButton = new JRadioButton("<html>" + FormatHTMLSafe.format(question.getChoices().get(i).getText()) + "</html>");
            thisChoice.add(currentChoiceRadioButton);

            // Check if the choice contains image and show the image in a Label
            choiceImage = new JLabel();
            if(currentChoice.getc_ImageData()!=null) {
                ImageIcon image = toImageIcon(currentChoice.getc_ImageData());
                choiceImage.setIcon(image);
                thisChoice.add(choiceImage);
            }
            choiceRadioButtonList[i] = currentChoiceRadioButton;

            currentChoiceRadioButton.addActionListener(radioButtonListener);

            if (question.getAnswer() == currentChoice) answerRadioButton = currentChoiceRadioButton;

            choiceButtonGroup.add(currentChoiceRadioButton);
            choicePanel.add(thisChoice, new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }
        // Listener
        clearSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choiceButtonGroup.clearSelection();
                navigationEntity.setColor(GUIConfig.NAVIGATION_NOT_SELECTED);
                answerStatus.setText("<html>Not yet answered</html>");
            }
        });
    }

    //# funtion to re_write an image from its data
    public ImageIcon toImageIcon(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedImage img = ImageIO.read(bais);

        if (img.getWidth() <= GUIConfig.imageWidth) return new ImageIcon(img);
        else {
            Image newIMG = img.getScaledInstance(GUIConfig.imageWidth, GUIConfig.imageWidth*img.getHeight()/img.getWidth(), Image.SCALE_SMOOTH);               // set default size for the output image
            return new ImageIcon(newIMG);
        }
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
            navigationEntity.setColor(GUIConfig.NAVIGATION_CORRECT);
            return question.getDefaultMark();
        }

        if (choiceButtonGroup.getSelection() != null)
            navigationEntity.setColor(GUIConfig.NAVIGATION_INCORRECT);

        return 0;
    }
    public JRadioButton[] getChoiceRadioButtonList() {
        return choiceRadioButtonList;
    }
}
