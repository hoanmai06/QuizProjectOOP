import javax.swing.*;
import java.awt.*;

public class DefaultJFrame extends JFrame {
    public DefaultJFrame(int width, int height) {
        setTitle("._.");
        setSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
