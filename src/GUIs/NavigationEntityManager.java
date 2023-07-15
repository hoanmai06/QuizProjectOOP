package GUIs;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class NavigationEntityManager {
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
