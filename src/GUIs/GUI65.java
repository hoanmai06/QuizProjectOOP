package GUIs;

import Algorithms.Randomfeature;
import DataObjects.CategoriesSingleton;
import DataObjects.Category;
import DataObjects.Question;
import DataObjects.Quiz;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

        // Setup questionTable
        ArrayList<Category> categories = CategoriesSingleton.getInstance().getCategories();
        DefaultTableModel questionTableModel = new DefaultTableModel(
                categories.get(0).getGUI65QuestionTableData(),
                new Object[]{"questionText"}
        ) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return getValueAt(0, columnIndex).getClass();
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
//        questionTable.getColumn("questionText").setCellRenderer(new QuestionRenderer());
        questionTable.setModel(questionTableModel);
        questionTable.setRowHeight(35);
        questionTable.getTableHeader().setUI(null);

        // Set up number of random question combo box
        int numberOfQuestion = CategoriesSingleton.getInstance().getCategory().getQuestions().size();

        String[] comboBoxList = new String[numberOfQuestion + 1];
        for (int i = 0; i <= numberOfQuestion; i++)
            comboBoxList[i] = String.valueOf(i);

        numberOfRandomQuestionComboBox.setModel(new DefaultComboBoxModel(comboBoxList));

        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = categoryComboBox.getSelectedIndex();
                Category selectedCategory = categories.get(selectedIndex);

                if(includeQuestionsFromSubcategoriesCheckBox.isSelected())
                {
                    DefaultTableModel questionTableModel = (DefaultTableModel) questionTable.getModel();
                    questionTableModel.setRowCount(0);
                    CategoriesSingleton.getInstance().addSubcategiesgui65(selectedCategory,questionTableModel);
                    int numberOfQuestion = CategoriesSingleton.getInstance().getAllQuestion(selectedCategory).size();
                    numberOfRandomQuestionComboBox.removeAllItems();
                    String[] comboBoxList = new String[numberOfQuestion + 1];
                    for (int i = 0; i <= numberOfQuestion; i++)
                        comboBoxList[i] = String.valueOf(i);

                    numberOfRandomQuestionComboBox.setModel(new DefaultComboBoxModel(comboBoxList));
                }
                else{
                    DefaultTableModel questionTableModel = (DefaultTableModel) questionTable.getModel();
                    questionTableModel.setRowCount(0);
                    for (Question question : selectedCategory.getQuestions()) {
                        questionTableModel.addRow(question.getGUI65QuestionTableRow());
                    }
                    numberOfRandomQuestionComboBox.removeAllItems();
                    int numberOfQuestion = selectedCategory.getQuestions().size();
                    String[] comboBoxList = new String[numberOfQuestion + 1];
                    for (int i = 0; i <= numberOfQuestion; i++)
                        comboBoxList[i] = String.valueOf(i);

                    numberOfRandomQuestionComboBox.setModel(new DefaultComboBoxModel(comboBoxList));
                }



            }
        });

        ADDRANDOMQUESTIONTOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = categoryComboBox.getSelectedIndex();
                Category selectedCategory = CategoriesSingleton.getInstance().findcategory(CategoriesSingleton.getInstance().getCategory(),selectedIndex);

                if(includeQuestionsFromSubcategoriesCheckBox.isSelected()){
                    quiz.getQuestions().addAll(Randomfeature.randomQuestion(numberOfRandomQuestionComboBox.getSelectedIndex(),CategoriesSingleton.getInstance().getAllQuestion(selectedCategory)));

                    new GUI62(getWidth(), getHeight(), quiz);
                    dispose();
                }
                else {
                    quiz.getQuestions().addAll(Randomfeature.randomQuestion(numberOfRandomQuestionComboBox.getSelectedIndex(), selectedCategory.getQuestions()));

                    new GUI62(getWidth(), getHeight(), quiz);
                    dispose();
                }
            }
        });
        includeQuestionsFromSubcategoriesCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = categoryComboBox.getSelectedIndex();
                Category selectedCategory = categories.get(selectedIndex);

                if(includeQuestionsFromSubcategoriesCheckBox.isSelected())
                {
                    DefaultTableModel questionTableModel = (DefaultTableModel) questionTable.getModel();
                    questionTableModel.setRowCount(0);
                    CategoriesSingleton.getInstance().addSubcategiesgui65(selectedCategory,questionTableModel);
                    int numberOfQuestion = CategoriesSingleton.getInstance().getAllQuestion(selectedCategory).size();
                    numberOfRandomQuestionComboBox.removeAllItems();
                    String[] comboBoxList = new String[numberOfQuestion + 1];
                    for (int i = 0; i <= numberOfQuestion; i++)
                        comboBoxList[i] = String.valueOf(i);

                    numberOfRandomQuestionComboBox.setModel(new DefaultComboBoxModel(comboBoxList));
                }
                else{
                    DefaultTableModel questionTableModel = (DefaultTableModel) questionTable.getModel();
                    questionTableModel.setRowCount(0);
                    for (Question question : selectedCategory.getQuestions()) {
                        questionTableModel.addRow(question.getGUI65QuestionTableRow());
                    }
                    numberOfRandomQuestionComboBox.removeAllItems();
                    int numberOfQuestion = selectedCategory.getQuestions().size();
                    String[] comboBoxList = new String[numberOfQuestion + 1];
                    for (int i = 0; i <= numberOfQuestion; i++)
                        comboBoxList[i] = String.valueOf(i);

                    numberOfRandomQuestionComboBox.setModel(new DefaultComboBoxModel(comboBoxList));
                }




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
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI65(1024, 768, quiz);
            }
        });
    }

    private void createUIComponents() {
        //categoryComboBox
        categoryComboBox = new JComboBox(CategoriesSingleton.getInstance().getCategoryNameList());
    }

}
