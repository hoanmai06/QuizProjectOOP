package GUIs;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class QuestionRenderer extends JLabel implements TableCellRenderer {
    public QuestionRenderer() {
        setOpaque(true);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setBackground(row%2==0 ? Color.white : UIManager.getColor("Table.alternateRowColor"));

        setText((String) value);

        return this;
    }
}
