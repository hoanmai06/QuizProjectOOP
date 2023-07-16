package Algorithms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;


public class Countdown {

    public void showCountDown(JLabel labelClock, int seconds) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int i = seconds;
            public void run() {
                int hour = i / 3600;
                int minute = (i % 3600) / 60;
                int second = i % 60;
                labelClock.setText("Time left: " + String.format("%02d:%02d:%02d", hour, minute, second));
//                System.out.print("\r" + String.format("%02d:%02d:%02d", hour, minute, second));
                if (i < 0) {
                    timer.cancel();
                }
                i--;
            }
        }, 0, 1000);

    }
}
