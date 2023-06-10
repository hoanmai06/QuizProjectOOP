import com.formdev.flatlaf.FlatLightLaf;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

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
    private JButton cancelButton;
    private JButton saveButton;
    private JPanel choicePanel;

    public GUI32(int width, int height) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);

        mainScrollPane.getVerticalScrollBar().setUnitIncrement(9);

        BLANKSFOR3MOREButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Implement later");
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GUI21(getWidth(), getHeight());
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
                new GUI32(1024, 768);
            }
        });
    }

}
