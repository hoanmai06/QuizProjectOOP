package GUIs;

import DataObjects.QuestionListContainer;

import javax.swing.*;
import java.awt.*;

public class QuestionTable extends JTable {
    private final static String[] columnNames = {"isSelected", "Question name", "Actions"};
    public QuestionTable(QuestionListContainer questionListContainer) {
        super(new QuestionTableModel(questionListContainer));

        setSelectionBackground(new Color(0x009FE5));
        setSelectionForeground(Color.white);
        getColumn("isSelected").setCellRenderer(new CheckBoxRenderer());
        getColumn("Question name").setCellRenderer(new QuestionRenderer());
        setRowHeight(35);
        getColumnModel().getColumn(0).setMaxWidth(25);
        getColumnModel().getColumn(2).setMaxWidth(60);
        getTableHeader().setUI(null);
    }
}
