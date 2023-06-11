import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;

public class ChoicePanelFactory {
    private JPanel choicePanel;
    private JLabel choiceLabel;
    private JComboBox gradeBox;

    public JPanel getJPanel() {
        return choicePanel;
    }

}
