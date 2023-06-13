package GUI;

import DataObject.Category;

import javax.swing.table.DefaultTableModel;

public class QuestionTableModel extends DefaultTableModel {
    private final static String[] columnNames = {"isSelected", "Question name", "Actions"};

    public QuestionTableModel(Category category) {
        super(category.getQuestionTableData(), columnNames);
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column != 1;
    }


}
