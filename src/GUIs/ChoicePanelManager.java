package GUIs;

import javax.swing.*;

public class ChoicePanelManager {
    private JPanel choicePanel;
    private JLabel choiceLabel;
    private JComboBox choiceGradeComboBox;
    private JButton testButton;
    private JTextArea choiceTextArea;

    public ChoicePanelManager(int index) {
        choiceLabel.setText("Choice " + index);
    }

    public JPanel getPanel() {
        return choicePanel;
    }

    public String getChoiceText() {
        return choiceTextArea.getText();
    }

    public int getGrade() {
        return choiceGradeComboBox.getSelectedIndex();
    }

    public void setGrade(int index) {
        choiceGradeComboBox.setSelectedIndex(index);
    }

    public void setChoiceText(String choiceText) {
        choiceTextArea.setText(choiceText);
    }
}
