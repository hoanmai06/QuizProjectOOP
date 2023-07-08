package GUI;

import DataObject.CategoriesSingleton;
import DataObject.Category;
import DataObject.Question;
import DataObject.Quiz;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GUI63 extends DefaultJFrame {
    private JPanel TopBar;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JCheckBox alsoShowQuestionsFromCheckBox;
    private JCheckBox alsoShowOldQuestionCheckBox;
    private JComboBox categoryComboBox;
    private JTable questionTable;
    private JPanel guiPanel;
    private JButton ADDSELECTEDQUESTIONTOButton;
    private JCheckBox selectAllCheckBox;

    public GUI63(int width, int height, Quiz quiz) {
        // TODO the constructor must have a quiz parameter
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);

        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = categoryComboBox.getSelectedIndex();
                Category selectedCategory = CategoriesSingleton.getInstance().getCategories().get(selectedIndex);

                QuestionTableModel questionTableModel = (QuestionTableModel) questionTable.getModel();
                questionTableModel.setRowCount(0);
                for (Question question : selectedCategory.getQuestions()) {
                    questionTableModel.addRow(question.getQuestionTableRow());
                }
            }
        });

        Action edit = new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());
                dispose();
                new GUI62(getWidth(), getHeight(), quiz);
            }
        };

        new EditButtonColumn(questionTable, edit, 2);

        selectAllCheckBox.addActionListener(new ActionListener() {
            // Go through all row and set value at the first column to the value of selectAllCheckBox
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < questionTable.getRowCount(); i++) {
                    questionTable.getModel().setValueAt(selectAllCheckBox.isSelected(), i, 0);
                }
            }
        });
        ADDSELECTEDQUESTIONTOButton.addActionListener(new ActionListener() {
            // Go through all row and add question of the selected tow to quiz.question
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < questionTable.getRowCount(); i++) {
                    // TODO Go through all row and add selected row to quiz.questions
                    if ((boolean) questionTable.getModel().getValueAt(i, 0))
                        quiz.addQuestion(
                                CategoriesSingleton
                                .getInstance()
                                .getCategories()
                                .get(categoryComboBox.getSelectedIndex())
                                .getQuestions()
                                .get(i)
                        );
                }

                new GUI62(getWidth(), getHeight(), quiz);
                dispose();
            }
        });
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

        Quiz quiz = new Quiz();
        quiz.setName("Hello World");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI63(1024, 768, quiz);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        //categoryComboBox
        categoryComboBox = new JComboBox(CategoriesSingleton.getInstance().getCategoryNameList());

        // questionTable
        questionTable = new QuestionTable(CategoriesSingleton.getInstance().getCategories().get(0));

    }
}
