package GUIs;

import DataObjects.*;
import DataObjects.Choice;
import com.formdev.flatlaf.FlatLightLaf;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GUI32Edit extends DefaultJFrame {
    private JPanel TopBar;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JPanel guiPanel;
    private JComboBox categoryComboBox;
    private JTextField questionNameField;
    private JComboBox grade1ComboBox;
    private JButton BLANKSFOR3MOREButton;
    private JScrollPane mainScrollPane;
    private JButton cancelButton;
    private JButton saveButton;
    private JPanel choicePanelContainer;
    private JTextField defaultMarkField;
    private JTextArea choice1TextArea;
    private JTextArea choice2TextArea;
    private JComboBox grade2ComboBox;
    private JLabel titleLabel;
    private JTextPane questionTextField;
    private JButton insertImageButton;
    private JLabel errorLabel;
    private ArrayList<ChoicePanelManager> choicePanelManagers = new ArrayList<>();
    private byte[] edit_qImageData;

    public GUI32Edit(int width, int height, int categoryIndex, int questionIndex) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);

        mainScrollPane.getVerticalScrollBar().setUnitIncrement(9);

        // Setup choicePanelContainer
        choicePanelContainer.setLayout(new GridLayoutManager(32, 2, new Insets(0, 0, 0, 10), -1, -1));
        choicePanelContainer.setBackground(new Color(-1));

        // Fill GUI components with the value of editingQuestion
        categoryComboBox.setSelectedIndex(categoryIndex);

        int oldCategoryIndex = categoryIndex;                           // luu tru de xem xet xem co bi thay doi category o luc save khong

        Category category = CategoriesSingleton.getInstance().findcategory(CategoriesSingleton.getInstance().getCategory(),categoryIndex);
        Question editingQuestion = category.getQuestions().get(questionIndex);

        // Dien cac thong tin da co cua editingquestion vua lay ra vao cac o tren GUI
        questionNameField.setText(editingQuestion.getName());
        questionTextField.setText(editingQuestion.getText());                                                           //#1 Fill textField with textQues

        if(editingQuestion.getq_ImageData()!=null) {
            edit_qImageData = editingQuestion.getq_ImageData();
            JLabel label = new JLabel();
            try {
                label.setIcon(toImageIcon(edit_qImageData));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            label.setVisible(true);
            questionTextField.insertComponent(label);                                                                   //#1 Fill textField with image if it has
        }

        // Fill choices with the choices of editingQuestion
        // Nếu choices.size() < 2 thì vẫn phải có 2 ô trống điền choice nên xử lý riêng
        ArrayList<Choice> choices = editingQuestion.getChoices();                                                       //#2 Lay ra choices tu cau hoi da chon
        for (int i = 0; i < 2; i++) {
            if (i >= choices.size()) {
                ChoicePanelManager newChoicePanelManager = new ChoicePanelManager(i + 1);                         //#2 Tao choicePanel moi neu cau hoi lay ra chua du 2 choice
                choicePanelManagers.add(newChoicePanelManager);

                choicePanelContainer.add(newChoicePanelManager.getPanel(), new GridConstraints(i, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
            } else {
                Choice choice = choices.get(i);                                                                         //#2 lay ra choice thu i+1 trong ds choices cua cau hoi lay ra

                ChoicePanelManager newChoicePanelManager = new ChoicePanelManager(i + 1);
                choicePanelManagers.add(newChoicePanelManager);

                newChoicePanelManager.setChoiceText(choice.getText());                                                  //#2 set choicePanel tren GUI theo thong tin tu choice vua chon ra
                newChoicePanelManager.setGrade(choice.getGradeIndex());

                if(choice.getc_ImageData()!=null) {
                    newChoicePanelManager.setChoiceImage(choice.getc_ImageData());                                      //#2 neu choice co anh thi moi set vao Pane
                }

                choicePanelContainer.add(newChoicePanelManager.getPanel(), new GridConstraints(i, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
            }
                                            //#2 them choicePanel nay vao Container
        }

        for (int i = 2; i < choices.size(); i++) {
            Choice choice = choices.get(i);                                                                             //#2 tuong tu ben tren

            ChoicePanelManager newChoicePanelManager = new ChoicePanelManager(i + 1);
            choicePanelManagers.add(newChoicePanelManager);

            newChoicePanelManager.setChoiceText(choice.getText());                                                      //#2
            newChoicePanelManager.setGrade(choice.getGradeIndex());

            if(choice.getc_ImageData()!=null) {
                newChoicePanelManager.setChoiceImage(choice.getc_ImageData());                                      //#2 neu choice co anh thi moi set vao Pane
            }

            choicePanelContainer.add(newChoicePanelManager.getPanel(), new GridConstraints(i, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
        }

        // Listener
        saveButton.addActionListener(new ActionListener() {                                                             //#3 Luu lai thong tin
            @Override
            public void actionPerformed(ActionEvent e) {
                editingQuestion.getChoices().clear();                                                                   //#3 Xac lap lai cau hoi nhu mot cau hoi moi

                if (questionNameField.getText().equals("")) {
                    showError("Question name is required");
                    return;
                }

                if (questionTextField.getText().equals("")) {
                    showError("Question text is required");
                    return;
                }

                editingQuestion.setName(questionNameField.getText());
                editingQuestion.setText(questionTextField.getText());

                if(questionTextField.getComponentCount() != 0) {
                    editingQuestion.setImageData(edit_qImageData);
                }
                else {
                    editingQuestion.setImageData(null);
                }

                double sumOfPositiveGrade = 0;
                for (ChoicePanelManager choicePanelManager : choicePanelManagers) {                                     //#3 lay ra tung ChoicePanel trong ArrayList Panel choice da add tren GUI
                    if (choicePanelManager.getChoiceText().equals("")) continue;                                        //#3 Bo qua choice rong
                    if (GradeConstants.getGrade(choicePanelManager.getGradeIndex()) > 0)
                        sumOfPositiveGrade += GradeConstants.getGrade(choicePanelManager.getGradeIndex());

                    Choice choice = new Choice();

                    // Thêm text và Grade
                    choice.setText(choicePanelManager.getChoiceText());
                    choice.setGradeIndex(choicePanelManager.getGradeIndex());

                    if(choicePanelManager.getChoiceImageData() != null) {
                        choice.setc_ImageData(choicePanelManager.getChoiceImageData());                                 //#3 Neu tren pane co anh thi moi set choice data
                    }

                    // Thêm đáp án
                    if (GradeConstants.GRADE_NONE < choice.getGradeIndex() && choice.getGradeIndex() < GradeConstants.GRADE_MINUS_5)
                        editingQuestion.addAnswer(choice);

                    editingQuestion.addChoice(choice);
                }

                if (editingQuestion.getChoices().size() < 2) {
                    showError("At least 2 choices is required");
                    return;
                }
                if (sumOfPositiveGrade != 1) {
                    showError("Sum of all positive choice's grade must equal 100");
                    return;
                }

               if (oldCategoryIndex != categoryComboBox.getSelectedIndex()) {
                   CategoriesSingleton.getInstance().findcategory(CategoriesSingleton.getInstance().getCategory(),categoryComboBox.getSelectedIndex()).addQuestion(editingQuestion);

                   ArrayList<Question> new_ListQuesOf_OldCate = (category.getQuestions());
                   new_ListQuesOf_OldCate.remove(editingQuestion);
                   CategoriesSingleton.getInstance().findcategory(CategoriesSingleton.getInstance().getCategory(),oldCategoryIndex).setQuestions(new_ListQuesOf_OldCate);

               }

                dispose();
                new GUI21(getWidth(), getHeight(), categoryComboBox.getSelectedIndex());
            }
        });
        BLANKSFOR3MOREButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numberOfChoicePanel = choicePanelManagers.size();

                for (int i = numberOfChoicePanel; i < numberOfChoicePanel + 3; i++) {
                    choicePanelManagers.add(new ChoicePanelManager(i+1));
                    choicePanelContainer.add(choicePanelManagers.get(i).getPanel(), new GridConstraints(i, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));

                    if (i == 31) {
                        BLANKSFOR3MOREButton.setEnabled(false);
                        break;
                    }
                }

                choicePanelContainer.revalidate();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GUI21(getWidth(), getHeight(), categoryComboBox.getSelectedIndex());
            }
        });
        insertImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog((Frame) null, "Choose a file", FileDialog.LOAD);
                fd.setVisible(true);
                File selected = new File(fd.getDirectory());
                String idFile = selected.getAbsolutePath() + File.separator + fd.getFile();
                try {
                    edit_qImageData = Files.readAllBytes(Paths.get(idFile));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                JLabel label = new JLabel();
                try {
                    label.setIcon(toImageIcon(edit_qImageData));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                label.setVisible(true);
                questionTextField.insertComponent(label);

            }
        });
    }

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
                new GUI32Edit(1024, 768, 0, 0);
            }
        });
    }

    private void showError(String errorMessage) {
        errorLabel.setText(errorMessage);
        errorLabel.setVisible(true);
    }

    private void createUIComponents() {
        categoryComboBox = new JComboBox(CategoriesSingleton.getInstance().getCategoryNameList());
    }

}
