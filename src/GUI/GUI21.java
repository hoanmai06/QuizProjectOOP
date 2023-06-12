package GUI;

import DataObject.Category;
import DataObject.Singleton;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI21 extends DefaultJFrame {
    private JPanel TopBar;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JPanel guiPanel;
    private JTabbedPane tabbedPane1;
    private JComboBox categoryComboBox;
    private JCheckBox alsoShowQuestionsFromCheckBox;
    private JCheckBox alsoShowOldQuestionCheckBox;
    private JButton CREATENEWQUESTIONButton;
    private JTable questionTable;
    private JComboBox comboBox1;
    private JTextField categoryNameTextField;
    private JButton ADDCATEGORYButton;
    private JTextArea categoryInfoTextArea;
    private JButton CHOOSEAFILEButton;
    private JPanel dashedBorderPanel;
    private JTextField categoryIDNumberTextField;
    String[] columnNames = {"isSelected", "Question name", "Actions"};

    public GUI21(int width, int height) {
        super(width, height);
        categoryComboBox.setSelectedIndex(0);
        setContentPane(guiPanel);
        setVisible(true);

        dashedBorderPanel.setBorder(BorderFactory.createDashedBorder(Color.GRAY, 7, 3));

        CHOOSEAFILEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog((Frame) null, "Choose a file", FileDialog.LOAD);
                fd.setVisible(true);
            }
        });

        CREATENEWQUESTIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GUI32(1024, 768);
            }
        });
        ADDCATEGORYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (categoryNameTextField.getText().isEmpty())
                    JOptionPane.showMessageDialog(null, "Category name is required.");
                else {
                    Category new_category = new Category();

                    new_category.setCategoryName(categoryNameTextField.getText());
                    new_category.setCategoryInfo(categoryInfoTextArea.getText());
                    new_category.setIdNumber(categoryIDNumberTextField.getText());

                    categoryNameTextField.setText("");
                    categoryInfoTextArea.setText("");
                    categoryIDNumberTextField.setText("");

                    Singleton.getInstance().addCategory(new_category);
                    categoryComboBox.addItem(String.format("%s (0)", new_category.getCategoryName()));
                    JOptionPane.showMessageDialog(null, String.format("Added category \"%s\"", new_category.getCategoryName()));
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

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI21(1024, 768);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        categoryComboBox = new JComboBox(Singleton.getInstance().getCategoryNameList());
        questionTable = new JTable(new DefaultTableModel(Singleton.getInstance().getCategories().get(0).getQuestionTableData(), columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return getValueAt(0, columnIndex).getClass();
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 1;
            }
        });

        Action edit = new AbstractAction("Edit") {

            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int row = Integer.valueOf(e.getActionCommand());
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                JOptionPane.showMessageDialog(null, "Editing row " + row);
            }
        };

        ButtonColumn inc = new ButtonColumn(questionTable, edit, 2);

        questionTable.setRowHeight(35);
        questionTable.getColumnModel().getColumn(0).setMaxWidth(25);
        questionTable.getColumnModel().getColumn(2).setMaxWidth(60);
    }

}