package Algorithms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Countdown {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel labelClock = new JLabel();
        labelClock.setBounds(20, 20, 80, 20);

        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());
        panel.setLayout(new FlowLayout());
        frame.add(panel);
        panel.add(labelClock);
        // dóng chương trình khi đóng của sổ
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });
        frame.setVisible(true);
        int seconds = 60 * 60;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int i = seconds;
            public void run() {
                int hour = i / 3600;
                int minute = (i % 3600) / 60;
                int second = i % 60;
                labelClock.setText("\r" + String.format("%02d:%02d:%02d", hour, minute, second));
                System.out.print("\r" + String.format("%02d:%02d:%02d", hour, minute, second));
                if (i < 0)
                    timer.cancel();
                i--;
            }
        }, 0, 1000);
    }
}
