import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class GUI32 extends DefaultJFrame {
    private JPanel TopBar;
    private JPanel MidZone1Container;
    private JPanel MidZone1;
    private JPanel MidZone2Container;
    private JPanel MidZone2;
    private JPanel guiPanel;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JTextArea textArea1;
    private JPanel choicePanel1;
    private JComboBox comboBox2;
    private JPanel choicePanel2;
    private JButton BLANKSFOR3MOREButton;
    private JScrollPane mainScrollPane;

    public GUI32() {
        setContentPane(guiPanel);
        setVisible(true);

        mainScrollPane.getVerticalScrollBar().setUnitIncrement(12);
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

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI32();
            }
        });
    }
}
