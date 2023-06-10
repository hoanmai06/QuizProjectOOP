import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class GUI11 extends DefaultJFrame {
    private JPanel guiPanel;
    private JButton TURNEDITINGONButton;
    private JPanel TopBar;
    private JPanel MidZone1;
    private JPanel MidZone2;
    private JButton quiz1Button;
    private JButton quiz5Button;
    private JButton quiz4Button;
    private JButton quiz2Button;
    private JButton quiz3Button;
    private JPanel MidZone1Container;
    private JPanel MidZone2Container;
    public JButton settingButton;
    private JButton questionsButton;
    private JButton categoriesButton;
    private JButton importButton;
    private JPanel popupPanel;
    private JButton exportButton;

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

            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        GUI11 myForm = new GUI11(1024, 768);
    }

}
