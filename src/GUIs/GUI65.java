package GUIs;

import DataObjects.CategoriesSingleton;
import DataObjects.Category;
import DataObjects.Question;
import DataObjects.Quiz;
import com.formdev.flatlaf.FlatLightLaf;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.stream.IntStream;

public class GUI65 extends DefaultJFrame {
    private JPanel guiPanel;
    private JPanel TopBar;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JCheckBox includeQuestionsFromSubcategoriesCheckBox;
    private JComboBox categoryComboBox;
    private JTable questionTable;
    private JButton ADDRANDOMQUESTIONTOButton;
    private JTabbedPane tabbedPane1;
    private JPanel existingCategory;
    private JPanel newCategory;
    private JComboBox numberOfRandomQuestionComboBox;

    public GUI65(int width, int height, Quiz quiz) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);

        // Set up number of random question combo box
        int numberOfQuestion =
                CategoriesSingleton
                        .getInstance()
                        .getCategories()
                        .get(categoryComboBox.getSelectedIndex())
                        .getQuestions()
                        .size();
        String[] comboBoxList = new String[numberOfQuestion + 1];
        for (int i = 0; i <= numberOfQuestion; i++)
            comboBoxList[i] = String.valueOf(i);

        numberOfRandomQuestionComboBox.setModel(new DefaultComboBoxModel(comboBoxList));

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
                new GUI32(getWidth(), getHeight(), categoryComboBox.getSelectedIndex(), row);
            }
        };

        new EditButtonColumn(questionTable, edit, 2);
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
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI65(1024, 768, quiz);
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
