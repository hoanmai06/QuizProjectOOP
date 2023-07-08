package GUIs;

import DataObjects.CategoriesSingleton;
import DataObjects.Category;
import DataObjects.Question;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JComboBox comboBox1;

    public GUI65(int width, int height) {
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

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI65(1024, 768);
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
