package bg.softuni.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class ElapsedTime {

    private Consumer<String> action;
    private Timer timer;
    private Instant startTime;

    public ElapsedTime(Consumer<String> action) {
        this.action = action;
    }

    public void start() {

        this.timer = new Timer();
        this.startTime = Instant.now();

        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Duration elapsedTime = Duration.between(startTime, Instant.now());
                action.accept(formatTime(elapsedTime.getSeconds() / 60, elapsedTime.getSeconds() % 60));
            }
        }, 0, 500);
    }

    public void stop() {
        this.timer.cancel();
    }

    private String formatTime(long minutes, long seconds) {

        String result = "";

        if (minutes < 10) {
            result += "0";
        }
        result += minutes;

        result += ":";

        if (seconds < 10) {
            result += "0";
        }
        result += seconds;

        return result;
    }
}
