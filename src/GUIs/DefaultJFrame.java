package GUIs;

import DataObjects.CategoriesSingleton;
import DataObjects.QuizzesSingleton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DefaultJFrame extends JFrame {
    public DefaultJFrame(int width, int height) {
        setTitle("._.");
        setSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                CategoriesSingleton.getInstance().writeCategoriesToFile();
                QuizzesSingleton.getInstance().writeQuizzesToFile();
                System.exit(0);
            }
        });
    }
}
