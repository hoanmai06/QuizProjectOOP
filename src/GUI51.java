import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class GUI51 extends DefaultJFrame {
    private JPanel guiPanel;
    private JTextField textField1;
    private JTextArea textArea1;
    private JCheckBox displayDecriptionOnCourseCheckBox;

    public GUI51(int width, int height) {
        super(width, height);
        setContentPane(guiPanel);
        setVisible(true);
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        GUI51 gui51 = new GUI51(1024, 768);
    }
}
