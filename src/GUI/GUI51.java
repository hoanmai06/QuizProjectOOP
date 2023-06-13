package GUI;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class GUI51 extends DefaultJFrame {
    private JPanel guiPanel;
    private JScrollPane mainScrollPane;
    private JTextField textField1;
    private JTextArea textArea1;
    private JCheckBox displayDesciptionOnCourseCheckBox;
    private JProgressBar progressBar1;
    private JComboBox comboBox1;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JCheckBox enableCheckBox;
    private JCheckBox enableCheckBox1;
    private JComboBox comboBox2;
    private JFormattedTextField formattedTextField1;
    private JButton createButton;
    private JButton cancelButton;
    private JComboBox comboBox5;
    private JProgressBar progressBar2;

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
