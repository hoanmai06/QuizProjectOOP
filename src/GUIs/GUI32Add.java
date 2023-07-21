package GUIs;

import DataObjects.CategoriesSingleton;
import DataObjects.Choice;
import DataObjects.GradeConstants;
import DataObjects.Question;
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

public class GUI32Add extends DefaultJFrame {
    private JPanel TopBar;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JPanel guiPanel;
    private JComboBox categoryComboBox;
    private JTextField questionNameField;
    private JButton BLANKSFOR3MOREButton;
    private JScrollPane mainScrollPane;
    private JButton cancelButton;
    private JButton saveButton;
    private JPanel choicePanelContainer;
    private JTextField defaultMarkField;
    private JLabel titleLabel;
    private JButton saveAndContinueButton;
    private JTextPane questionTextField;
    private JButton insertImageButton;
    private JLabel errorLabel;
    private ArrayList<ChoicePanelManager> choicePanelManagers = new ArrayList<>();
    private byte[] qImageData;

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

        // Restore editLaterQuestion (if needed)
        Question editLaterQuestion = CategoriesSingleton.getInstance().getEditLaterQuestion();                                //#1: Mang cau hoi dang adding ra Fill vao GUI
        if (editLaterQuestion != null) {
            questionNameField.setText(editLaterQuestion.getName());
            questionTextField.setText(editLaterQuestion.getText());

            if(editLaterQuestion.getq_ImageData()!=null) {
                qImageData = editLaterQuestion.getq_ImageData();
                JLabel label = new JLabel();
                try {
                    label.setIcon(toImageIcon(qImageData));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                label.setVisible(true);
                questionTextField.insertComponent(label);                                                                   //#1 Fill textField with image if it has
            }

            // Fill choices with the choices of editLaterQuestion
            // Nếu choices.size() < 2 thì vẫn phải có 2 ô trống điền choice nên xử lý riêng
            ArrayList<Choice> choices = editLaterQuestion.getChoices();                                                    //#1: Lay ra choices cua cau hoi dang adding ra Fill vao GUI
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
                    newChoicePanelManager.setGrade(choice.getGradeIndex());

                    if(choice.getc_ImageData()!=null) {
                        newChoicePanelManager.setChoiceImage(choice.getc_ImageData());                                  //#1 Neu choice lay ra co anh thi moi setcImage
                    }

                    choicePanelContainer.add(newChoicePanelManager.getPanel(), new GridConstraints(i, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
                }
            }
                                    // cac ham .getPanel() nay tra ve cai Panel to cua 1 choice roi add vao choicePanelContainer (cai co dan trong GUI32)

            for (int i = 2; i < choices.size(); i++) {
                Choice choice = choices.get(i);

                ChoicePanelManager newChoicePanelManager = new ChoicePanelManager(i + 1);
                choicePanelManagers.add(newChoicePanelManager);

                newChoicePanelManager.setChoiceText(choice.getText());
                newChoicePanelManager.setGrade(choice.getGradeIndex());

                if(choice.getc_ImageData()!=null) {
                    newChoicePanelManager.setChoiceImage(choice.getc_ImageData());                                  //#1 Neu choice lay ra co anh thi moi setcImage
                }

                choicePanelContainer.add(newChoicePanelManager.getPanel(), new GridConstraints(i, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
            }
        } else {                        //#2 Khi ma ko con cau hoi do dang (adding)
                                        //   thi khong can dien vao cac textField name va text cua Question.
                                        //   thay vao do chi la them moi cac choicePanelManager.
            choicePanelManagers.add(new ChoicePanelManager(1));
            choicePanelContainer.add(choicePanelManagers.get(0).getPanel(), new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
            choicePanelManagers.add(new ChoicePanelManager(2));
            choicePanelContainer.add(choicePanelManagers.get(1).getPanel(), new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
        }
                                        // Xuat hien .getPanel() la fill thong tin cua cac choices vao Container (luc nay cac truong thong tin deu dang rong)

        BLANKSFOR3MOREButton.addActionListener(new ActionListener() {                                       //#3 them choice (KO van de lam)
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

        saveButton.addActionListener(new ActionListener() {                                                 //#4 nut save
            @Override
            public void actionPerformed(ActionEvent e) {                                                //#4 Mang thong tin tren GUI set vao newQuestion de save-> dataCenter
                Question newQuestion = new Question();

                if (questionNameField.getText().equals("")) {
                    showError("Question name is required");
                    return;
                }

                if (questionTextField.getText().equals("")) {
                    showError("Question text is required");
                    return;
                }

                newQuestion.setName(questionNameField.getText());
                newQuestion.setText(questionTextField.getText());                   // luc nay getText() chi tra ve text be tren cua JtextPane thoi chu kho chua text cua component Jlabel

                if(questionTextField.getComponentCount() != 0) {                    // text cua JtextPane khong duoc tinh la mot component. kiem tra so luong xem co label nao ko thoi.
                    newQuestion.setImageData(qImageData);
                }
                else {
                    newQuestion.setImageData(null);
                }

                double sumOfPositiveGrade = 0;
                for (ChoicePanelManager choicePanelManager : choicePanelManagers) {                     //#4 Lay tung choicePanel ra khoi Manager
                    if (choicePanelManager.getChoiceText().equals("")) continue;                        //#4 bo qua Choice rong
                    if (GradeConstants.getGrade(choicePanelManager.getGradeIndex()) > 0)
                        sumOfPositiveGrade += GradeConstants.getGrade(choicePanelManager.getGradeIndex());

                    Choice choice = new Choice();

                    // Thêm text và Grade
                    choice.setText(choicePanelManager.getChoiceText());                                 //#4 Lay thong tin tu choicePanel set vao choice
                    choice.setGradeIndex(choicePanelManager.getGradeIndex());

                    if(choicePanelManager.getChoiceImageData() != null) {
                        choice.setc_ImageData(choicePanelManager.getChoiceImageData());                 //#4 neu trong GUI co anh thi moi set data cho choice
                    }

                    // Thêm đáp án
                    if (GradeConstants.GRADE_NONE < choice.getGradeIndex() && choice.getGradeIndex() < GradeConstants.GRADE_MINUS_5)
                        newQuestion.addAnswer(choice);

                    newQuestion.addChoice(choice);
                }

                if (newQuestion.getChoices().size() < 2) {
                    showError("At least 2 choices is required");
                    return;
                }
                if (sumOfPositiveGrade != 1) {
                    showError("Sum of all positive choice's grade must equal 100");
                    return;
                }

                CategoriesSingleton.getInstance().findcategory(CategoriesSingleton.getInstance().getCategory(),categoryComboBox.getSelectedIndex()).addQuestion(newQuestion);
                CategoriesSingleton.getInstance().setEditLaterQuestion(null);

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


        saveAndContinueButton.addActionListener(new ActionListener() {                                      //#5 save cai cau hoi dang lam do (editingQ)
            @Override
            public void actionPerformed(ActionEvent e) {
                Question editingQuestion = new Question();
                editingQuestion.setName(questionNameField.getText());                                       //#5 lay cac thong tin tren GUI de save vao cau hoi dang editing
                editingQuestion.setText(questionTextField.getText());

                if(questionTextField.getComponentCount() != 0) {
                    editingQuestion.setImageData(qImageData);
                }
                else {
                    editingQuestion.setImageData(null);
                }

                for (ChoicePanelManager choicePanelManager : choicePanelManagers) {                         //#6 Lay tung choicepanel ra khoi Manager
                    if (choicePanelManager.getChoiceText().equals("")) continue;

                    Choice choice = new Choice();

                    choice.setText(choicePanelManager.getChoiceText());                                     //#6 Lay cac thong tin trong choicePanel gan vao choice moi
                    choice.setGradeIndex(choicePanelManager.getGradeIndex());

                    if(choicePanelManager.getChoiceImageData() != null) {
                        choice.setc_ImageData(choicePanelManager.getChoiceImageData());                 //#6 neu trong GUI co anh thi moi set data cho choice
                    }

                    editingQuestion.addChoice(choice);
                }

                CategoriesSingleton.getInstance().setEditLaterQuestion(editingQuestion);
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
                    qImageData = Files.readAllBytes(Paths.get(idFile));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                JLabel label = new JLabel();
                try {
                    label.setIcon(toImageIcon(qImageData));
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
                new GUI32Add(1024, 768, 0);
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
