package GUIs;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class NavigationEntityManager {
    public static Color CORRECT = new Color(0x7FFF7F);
    public static Color INCORRECT = new Color(0xE77070);
    public static Color SELECTED = Color.GRAY;
    public static Color NOT_SELECTED = Color.WHITE;
    private JLabel indexLabel;
    private JLabel colorLabel;
    private JPanel navigationEntity;

    public NavigationEntityManager(int i) {
        navigationEntity.setBorder(new LineBorder(Color.BLACK, 1, true));
        indexLabel.setText(String.valueOf(i));
    }

    public JPanel getEntity() {
        return navigationEntity;
    }

    public void setColor(Color color) {
        colorLabel.setBackground(color);
    }
}
