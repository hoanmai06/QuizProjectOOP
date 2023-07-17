package GUIs;

import DataObjects.CategoriesSingleton;
import DataObjects.Category;
import DataObjects.Question;
import DataObjects.Quiz;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
    private JLabel IT;

    public GUI63(int width, int height, Quiz quiz) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);

        // Setup questionTable
        ArrayList<Category> categories = CategoriesSingleton.getInstance().getCategories();
        DefaultTableModel questionTableModel = new DefaultTableModel(
                categories.get(0).getGUI63QuestionTableData(),
                new Object[]{"isSelected", "questionText"}
        ) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return getValueAt(0, columnIndex).getClass();
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 1;
            }
        };

        questionTable.setModel(questionTableModel);
        questionTable.getColumn("isSelected").setMaxWidth(25);
        questionTable.getColumn("isSelected").setCellRenderer(new CheckBoxRenderer());
        questionTable.getColumn("questionText").setCellRenderer(new StringRenderer());
        questionTable.setRowHeight(35);
        questionTable.getTableHeader().setUI(null);

        // Listener
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = categoryComboBox.getSelectedIndex();
                Category selectedCategory = categories.get(selectedIndex);

                DefaultTableModel questionTableModel = (DefaultTableModel) questionTable.getModel();
                questionTableModel.setRowCount(0);
                for (Question question : selectedCategory.getQuestions()) {
                    questionTableModel.addRow(question.getGUI63QuestionTableRow());
                }

                selectAllCheckBox.setSelected(false);
            }
        });

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
                    // Go through all row and add selected row to quiz.questions
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
        IT.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new GUI11(getWidth(), getHeight());
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
        //categoryComboBox
        categoryComboBox = new JComboBox(CategoriesSingleton.getInstance().getCategoryNameList());
    }

}
