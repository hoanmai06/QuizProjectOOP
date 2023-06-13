package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoicePanelFactory {
    private JPanel choicePanel;
    private JLabel choiceLabel;
    private JComboBox gradeBox;
    private JButton testButton;

    public JPanel getJPanel(int i) {
        choiceLabel.setText("Choice " + i);

        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Activated by choice " + i);
            }
        });

        return choicePanel;
    }

}
