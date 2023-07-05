package GUI;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI61 extends DefaultJFrame {
    private JPanel TopBar;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JPanel guiPanel;
    private JLabel directoryLabel;
    private JLabel quizNameLabel;
    private JButton editQuizButton;
    private JButton PREVIEWQUIZNOWButton;
    private JTable attemptTable;
    private JScrollPane tableScrollPane;

    public GUI61(int width, int height) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);
        editQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUI62(getWidth(), getHeight());
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

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI61(1024, 768);
            }
        });
    }

    private void createUIComponents() {
        String[] columnNames = {"Attempt", "State"};
        Object[][] testData = new Object[2][2];
        testData[0][0] = "Preview";
        testData[0][1] = "Never Submitted";
        testData[1][0] = "Preview";
        testData[1][1] = "Never Submitted";
        attemptTable = new JTable(testData, columnNames);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        cellRenderer.setBorder(null);
        attemptTable.getColumn("Attempt").setCellRenderer(cellRenderer);
        attemptTable.getColumn("State").setCellRenderer(cellRenderer);
        attemptTable.setPreferredScrollableViewportSize(new Dimension(-1, 40*attemptTable.getRowCount()));
        attemptTable.setRowHeight(40);

        JTableHeader tableHeader = attemptTable.getTableHeader();
        tableHeader.setBackground(new Color(0x0171A2));
        tableHeader.setForeground(Color.white);
        tableHeader.setPreferredSize(new Dimension(-1, 40));
        tableHeader.setFont(javax.swing.UIManager.getDefaults().getFont("Label.font").deriveFont(Font.BOLD));
        tableHeader.setBorder(new EmptyBorder(0, 0, 0, 0));
    }
}
