package GUI;

import DataObject.Category;
import DataObject.QuizzesSingleton;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI11 extends DefaultJFrame {
    private JPanel guiPanel;
    private JButton TURNEDITINGONButton;
    private JPanel TopBar;
    private JPanel MidZone1;
    private JPanel MidZone2;
    private JPanel MidZone1Container;
    private JPanel MidZone2Container;
    public JButton settingButton;
    private JButton questionsButton;
    private JButton categoriesButton;
    private JButton importButton;
    private JPanel popupPanel;
    private JButton exportButton;
    private JTable quizTable;

    public GUI11(int width, int height) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);

        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupPanel.setVisible(!popupPanel.isVisible());
            }
        });

        questionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUI21(getWidth(), getHeight());
                dispose();
            }
        });
        TURNEDITINGONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUI51(getWidth(), getHeight());
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

        GUI11 myForm = new GUI11(1024, 768);
    }

    private void createUIComponents() {
        Action quiz = new AbstractAction("Quiz") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());

                new GUI61(getWidth(), getHeight());
                dispose();
            }
        };

        String[] columnNames = {"Quiz"};
        quizTable = new JTable(new DefaultTableModel(QuizzesSingleton.getInstance().getQuizTableData(), columnNames));
        quizTable.getTableHeader().setUI(null);
        quizTable.setSelectionBackground(new Color(0x009FE5));
        quizTable.setRowHeight(40);
        new QuizButtonColumn(quizTable, quiz, 0);

    }
}
