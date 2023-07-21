package GUIs;

import DataObjects.GradeConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ChoicePanelManager {
    private JPanel choicePanel;
    private JLabel choiceLabel;
    private JComboBox choiceGradeComboBox;
    private JTextPane choiceTextArea;
    private JButton insertImageButton;
    private JButton testButton;
    private static byte[] cEditImageData;

    public ChoicePanelManager(int index) {
        choiceLabel.setText("Choice " + index);
        insertImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog((Frame) null, "Choose a file", FileDialog.LOAD);
                fd.setVisible(true);
                File selected = new File(fd.getDirectory());
                String idFile = selected.getAbsolutePath() + File.separator + fd.getFile();
                try {
                    cEditImageData = Files.readAllBytes(Paths.get(idFile));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                JLabel label = new JLabel();
                try {
                    label.setIcon(toImageIcon(cEditImageData));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                label.setVisible(true);
                choiceTextArea.insertComponent(label);
            }
        });
    }

    public JPanel getPanel() {
        return choicePanel;
    }

    public String getChoiceText() {
        return choiceTextArea.getText();
    }

    public int getGradeIndex() {
        return choiceGradeComboBox.getSelectedIndex();
    }

    public void setGrade(int index) {
        choiceGradeComboBox.setSelectedIndex(index);
    }

    public void setChoiceText(String choiceText) {
        choiceTextArea.setText(choiceText);
    }

    public void setChoiceImage(byte[] data) {                                                   //#? lay data tu question de chen anh vao choicePanel
        cEditImageData = data;
        JLabel label = new JLabel();
        try {
            label.setIcon(toImageIcon(data));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        label.setVisible(true);
        choiceTextArea.insertComponent(label);
    }

    public byte[] getChoiceImageData() {
        if(choiceTextArea.getComponentCount() != 0) {
            return cEditImageData;
        }
        else {
            cEditImageData = null;
            return null;
        }
    }

    public ImageIcon toImageIcon(byte[] data) throws IOException {
        ImageIcon imageIcon = new ImageIcon(data);

        int oldHeight = imageIcon.getIconHeight();
        int oldWidth = imageIcon.getIconWidth();
        int newWidth = GUIConfig.imageWidth;

        if (oldWidth < newWidth) {
            return imageIcon;
        }
        Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newWidth*oldHeight/oldWidth, Image.SCALE_DEFAULT);
        return new ImageIcon(scaledImage);
    }
}
