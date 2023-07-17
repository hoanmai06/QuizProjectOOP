package GUIs;

import DataObjects.CategoriesSingleton;
import DataObjects.Category;
import DataObjects.Choice;
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
    private JTextPane QuestionTextField;
    private JButton insertImageButton;
    private ArrayList<ChoicePanelManager> choicePanelManagers = new ArrayList<>();
    private static byte[] edit_qImageData;

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

        Category category = CategoriesSingleton.getInstance().getCategories().get(categoryIndex);
        Question editingQuestion = category.getQuestions().get(questionIndex);                                          //#1 Lay ra question trong dataCenter

        // Dien cac thong tin da co cua editingquestion vua lay ra vao cac o tren GUI
        questionNameField.setText(editingQuestion.getName());
        QuestionTextField.setText(editingQuestion.getText());                                                           //#1 Fill textField with textQues

        if(editingQuestion.getq_ImageData()!=null) {
            edit_qImageData = editingQuestion.getq_ImageData();
            JLabel label = new JLabel();
            try {
                label.setIcon(toImageIcon(edit_qImageData));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            label.setVisible(true);
            QuestionTextField.insertComponent(label);                                                                   //#1 Fill textField with image if it has
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
                newChoicePanelManager.setGrade(choice.getGrade());

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
            newChoicePanelManager.setGrade(choice.getGrade());

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

                editingQuestion.setName(questionNameField.getText());
                editingQuestion.setText(QuestionTextField.getText());

                if(QuestionTextField.getComponentCount() != 0) {
                    editingQuestion.setImageData(edit_qImageData);
                }
                else {
                    editingQuestion.setImageData(null);
                }

                for (ChoicePanelManager choicePanelManager : choicePanelManagers) {                                     //#3 lay ra tung ChoicePanel trong ArrayList Panel choice da add tren GUI
                    if (choicePanelManager.getChoiceText().equals("")) continue;                                        //#3 Bo qua choice rong

                    Choice choice = new Choice();

                    choice.setText(choicePanelManager.getChoiceText());
                    choice.setGrade(choicePanelManager.getGrade());

                    if(choicePanelManager.getChoiceImageData() != null) {
                        choice.setc_ImageData(choicePanelManager.getChoiceImageData());                                 //#3 Neu tren pane co anh thi moi set choice data
                    }

                    if (choice.getGrade() == 1) editingQuestion.setAnswer(choice);

                    editingQuestion.addChoice(choice);
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
                QuestionTextField.insertComponent(label);

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

    private void createUIComponents() {
        categoryComboBox = new JComboBox(CategoriesSingleton.getInstance().getCategoryNameList());
    }

}
