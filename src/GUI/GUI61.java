package GUI;

import DataObject.Quiz;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
    private JButton PREVIEW_QUIZ_NOWButton;
    private JTable attemptTable;
    private JScrollPane tableScrollPane;
    private JLabel timeLimitLabel;

    public GUI61(int width, int height, Quiz quiz) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);

        // Set GUI component related to quiz
        quizNameLabel.setText(quiz.getName());
        switch (quiz.getTimeLimit()) {
            case -1 -> timeLimitLabel.setText("Time limit: No limit");
            case 1 -> {
                if (quiz.getTimeLimitType() == Quiz.TIME_TYPE_HOUR) {
                    timeLimitLabel.setText("Time limit: 1 hour");
                } else {
                    timeLimitLabel.setText("Time limit: 1 minute");
                }
            }
            default -> {
                if (quiz.getTimeLimitType() == Quiz.TIME_TYPE_HOUR) {
                    timeLimitLabel.setText("Time limit: " + quiz.getTimeLimit() + " hours");
                } else {
                    timeLimitLabel.setText("Time limit: " + quiz.getTimeLimit() + " minutes");
                }
            }
        }

        // Config attemptTable
        String[] columnNames = {"Attempt", "State"};
        Object[][] tableData = quiz.getAttemptTableData();
        DefaultTableModel attemptTableModel = new DefaultTableModel(tableData, columnNames);

        attemptTable.setModel(attemptTableModel);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        cellRenderer.setBorder(null);
        attemptTable.getColumn("Attempt").setCellRenderer(cellRenderer);
        attemptTable.getColumn("State").setCellRenderer(cellRenderer);
        int preferredViewPortSize = Math.min(40 * attemptTable.getRowCount(), 160);
        attemptTable.setPreferredScrollableViewportSize(new Dimension(-1, preferredViewPortSize));
        attemptTable.setRowHeight(40);

        JTableHeader tableHeader = attemptTable.getTableHeader();
        tableHeader.setBackground(new Color(0x0171A2));
        tableHeader.setForeground(Color.white);
        tableHeader.setPreferredSize(new Dimension(-1, 40));
        tableHeader.setFont(javax.swing.UIManager.getDefaults().getFont("Label.font").deriveFont(Font.BOLD));
        tableHeader.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Listener
        editQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUI62(getWidth(), getHeight(), quiz);
                dispose();
            }
        });

        PREVIEW_QUIZ_NOWButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quiz.getPreviousAttemptList().add("Never Submitted");
                new GUI61(getWidth(), getHeight(), quiz);
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

        // Test quiz
        Quiz quiz = new Quiz();
        quiz.setName("Hello World");
        quiz.setTimeLimit(10);
        quiz.setTimeLimitType(Quiz.TIME_TYPE_MINUTE);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI61(1024, 768, quiz);
            }
        });
    }

    private void createUIComponents() {
    }
}
