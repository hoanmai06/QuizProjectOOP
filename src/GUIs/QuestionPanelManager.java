package GUIs;

import Algorithms.FormatHTMLSafe;
import Algorithms.Randomfeature;
import DataObjects.Choice;
import DataObjects.GradeConstants;
import DataObjects.Question;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

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
    ArrayList<JComponent> choiceComponentList = new ArrayList<>();
    ArrayList<String> answerNames = new ArrayList<>();
    ArrayList<JComponent> answerComponents = new ArrayList<>();
    Question question;
    NavigationEntityManager navigationEntity;
    ButtonGroup choiceButtonGroup;
    ArrayList<Choice> shuffledChoices = new ArrayList<>();

    public QuestionPanelManager(int index, Question question, NavigationEntityManager navigationEntity, boolean isShuffle) throws IOException {
        ArrayList<Choice> defaultChoices = question.getChoices();

        if (isShuffle) {
            ArrayList<Integer> shuffledIndex = Randomfeature.shuffle(defaultChoices.size());
            for (Integer i : shuffledIndex) {
                shuffledChoices.add(defaultChoices.get(i));
            }
        } else {
            shuffledChoices = defaultChoices;
        }


        this.question = question;
        this.navigationEntity = navigationEntity;
        // Customize JLabel
        questionIndex.setText(String.valueOf(index));
        questionText.setText("<html>" + FormatHTMLSafe.format(question.getText()).replaceAll("\n", "<br/>") + "</html>");

        // Check if the questionText contains image and show the image in a Label
        if(question.getq_ImageData()!=null) {
            ImageIcon image = toImageIcon(question.getq_ImageData());
            JLabel questionImage = new JLabel(image);
            questionContent.add(questionImage, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }

        // Setup choice panel
        int numberOfChoices = shuffledChoices.size();

        choicePanel.setLayout(new GridLayoutManager(numberOfChoices, 1, new Insets(0, 0, 0, 0), -1, 8));

        choiceButtonGroup = new ButtonGroup();

        MouseListener choiceComponentListener = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                boolean isCheckBoxesSelected = false;
                for (JComponent component : choiceComponentList) {
                    if (component instanceof JCheckBox castedComponent) {
                        if (castedComponent.isSelected()) isCheckBoxesSelected = true;
                    }
                }

                if (!question.isMultipleChoices() || isCheckBoxesSelected) {
                    navigationEntity.setColor(GUIConfig.NAVIGATION_SELECTED);
                    answerStatus.setText("<html>Answered<br>&nbsp;</html>");
                } else {
                    navigationEntity.setColor(GUIConfig.NAVIGATION_NOT_SELECTED);
                    answerStatus.setText("<html>Not yet answered</html>");
                }
            }
        };

        for (int i = 0; i < numberOfChoices; i++) {

            // Create one JPanel for each choice
            JPanel thisChoice = new JPanel();
            thisChoice.setBackground(new Color(0xE7F3F5));
            BoxLayout box = new BoxLayout(thisChoice, BoxLayout.Y_AXIS); // xep cac components theo chieu doc
            thisChoice.setLayout(box);

            Choice currentChoice = shuffledChoices.get(i);
            JComponent currentChoiceComponent;
            if (question.isMultipleChoices()) {
                currentChoiceComponent = new JCheckBox("<html>" + (char) (65 + i) + ". " + FormatHTMLSafe.format(shuffledChoices.get(i).getText()).replaceAll("\n", "<br/>") + "</html>");
            } else {
                currentChoiceComponent = new JRadioButton("<html>" + (char) (65 + i) + ". " + FormatHTMLSafe.format(shuffledChoices.get(i).getText()).replaceAll("\n", "<br/>") + "</html>");
            }

            thisChoice.add(currentChoiceComponent);

            // Check if the choice contains image and show the image in a Label
            choiceImage = new JLabel();
            if(currentChoice.getc_ImageData()!=null) {
                ImageIcon image = toImageIcon(currentChoice.getc_ImageData());
                choiceImage.setIcon(image);
                thisChoice.add(choiceImage);
            }
            choiceComponentList.add(currentChoiceComponent);

            currentChoiceComponent.addMouseListener(choiceComponentListener);

            if (question.getAnswers().contains(currentChoice)) {
                answerNames.add(String.valueOf((char) (65 + i)));
                answerComponents.add(currentChoiceComponent);
            }

            if (currentChoiceComponent instanceof JRadioButton) {
                choiceButtonGroup.add((JRadioButton) currentChoiceComponent);
            }

            choicePanel.add(thisChoice, new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }

        // Setup answerPanel
        if (!question.isMultipleChoices()) {
            answerLabel.setText("<html>The correct answer is: " + answerNames.get(0) + ". " +  FormatHTMLSafe.format(question.getAnswers().get(0).getText()).replaceAll("\n", "<br/>") + "</html>");

            // Check if the answer contains image and add a Label of it to answerPanel
            if(question.getAnswers().get(0).getc_ImageData()!= null) {
                ImageIcon image = toImageIcon(question.getAnswers().get(0).getc_ImageData());
                JLabel answerImage = new JLabel(image);
                answerPanel.add(answerImage, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            }
        } else {
            StringBuilder answer = new StringBuilder();
            int numOfAns = answerNames.size();

            for (int i = 0; i < answerNames.size() - 1; i++) {
                answer.append(answerNames.get(i)).append(", ");
            }
            answer.append("and ").append(answerNames.get(numOfAns - 1));

            answerLabel.setText("<html>The correct answers are: " + answer + "</html>");
        }

        // Listener
        clearSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JComponent component : choiceComponentList) {
                    if (component instanceof JRadioButton) {
                        choiceButtonGroup.clearSelection();
                    }
                    if (component instanceof JCheckBox) {
                        JCheckBox castedComponent = (JCheckBox) component;
                        castedComponent.setSelected(false);
                    }
                }

                navigationEntity.setColor(GUIConfig.NAVIGATION_NOT_SELECTED);
                answerStatus.setText("<html>Not yet answered</html>");
            }
        });
    }

    //# funtion to re_write an image from its data
    public ImageIcon toImageIcon(byte[] data) throws IOException {
        ImageIcon imageIcon = new ImageIcon(data);

        int oldHeight = imageIcon.getIconHeight();
        int oldWidth = imageIcon.getIconWidth();
        int newWidth = GUIConfig.imageWidth;

        if (oldWidth < newWidth) {
            return imageIcon;
        }
        Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newWidth*oldHeight/oldWidth, Image.SCALE_DEFAULT);
        return new ImageIcon(scaledImage);
    }

    public void disableButton() {
        for (JComponent component : choiceComponentList) {
            component.setEnabled(false);
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
        ArrayList<JComponent> selectedComponents = new ArrayList<>();
        for (JComponent component : choiceComponentList) {
            if (component instanceof JCheckBox castedComponent) {
                if (castedComponent.isSelected())
                    selectedComponents.add(castedComponent);
            }
            if (component instanceof JRadioButton castedComponent) {
                if (castedComponent.isSelected())
                    selectedComponents.add(castedComponent);
            }
        }

        if (selectedComponents.size() == 0) {
            return 0;
        }

        double mark = 0;
        for (JComponent component : selectedComponents) {
            mark += GradeConstants.getGrade(shuffledChoices.get(choiceComponentList.indexOf(component)).getGradeIndex());
        }

        if (mark == 1) {
            navigationEntity.setColor(GUIConfig.NAVIGATION_CORRECT);
            answerPanel.setBackground(new Color(0xDEFFDE));
        } else if (0 < mark && mark < 1)
            navigationEntity.setColor(GUIConfig.NAVIGATION_PARTIALLY_CORRECT);
        else {
            navigationEntity.setColor(GUIConfig.NAVIGATION_INCORRECT);
            return 0;
        }
        return mark;
    }

    public ArrayList<JComponent> getChoiceComponentList() {
        return choiceComponentList;
    }
}
