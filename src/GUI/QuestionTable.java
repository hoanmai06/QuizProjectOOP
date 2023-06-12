package GUI;

import DataObject.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;

public class QuestionTable extends JTable {
    private final static String[] columnNames = {"isSelected", "Question name", "Actions"};
    public QuestionTable(Category category) {
        super(new QuestionTableModel(category));

        Action edit = new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int row = Integer.parseInt(e.getActionCommand());
//                DefaultTableModel model = (DefaultTableModel) table.getModel();
                JOptionPane.showMessageDialog(null, "Editing row " + row);
            }
        };

//        ButtonColumn inc =
        new ButtonColumn(this, edit, 2);

        this.setRowHeight(35);
        this.getColumnModel().getColumn(0).setMaxWidth(25);
        this.getColumnModel().getColumn(2).setMaxWidth(60);
    }
}
