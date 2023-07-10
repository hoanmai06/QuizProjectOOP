package GUIs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoicePanelManager {
    private JPanel choicePanel;
    private JLabel choiceLabel;
    private JComboBox choiceGradeBox;
    private JButton testButton;
    private JTextArea choiceTextArea;

    public ChoicePanelManager(int index) {
        choiceLabel.setText("Choice " + index);

        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Activated by choice " + index);
            }
        });
    }

    public JPanel getChoicePanel() {
        return choicePanel;
    }

}
