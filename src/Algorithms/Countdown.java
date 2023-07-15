package Algorithms;
import java.util.Timer;
import java.util.TimerTask;

public class Countdown {
    public static void main(String[] args) {
        int seconds = 60 * 60;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int i = seconds;
            public void run() {
                int hour = i / 3600;
                int minute = (i % 3600) / 60;
                int second = i % 60;
                System.out.print("\r" + String.format("%02d:%02d:%02d", hour, minute, second));
                if (i < 0)
                    timer.cancel();
                i--;
            }
        }, 0, 1000);
    }
}
