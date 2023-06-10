import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class GUI51 extends DefaultJFrame {
    private JPanel guiPanel;
    private JPanel topbar;
    private JPanel TopBar;

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
