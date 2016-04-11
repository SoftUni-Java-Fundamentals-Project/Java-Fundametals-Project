package bg.softuni.poosweeper.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/**
 * A model class that handles the calculation of elapsed time since
 * the game started.
 */
public class ElapsedTime {

    private Consumer<String> action;
    private Timer timer;
    private Instant startTime;

    /**
     * Creates a new instance with the provided update delegate.
     *
     * @param action a delegate method that receives the formatted time value
     *               and does something with it.
     * @see bg.softuni.poosweeper.controller.MainController#updateTimeLabel(String)
     */
    public ElapsedTime(Consumer<String> action) {
        this.action = action;
    }

    /**
     * Starts a new timer task that updates through the {@link #action} delegate.
     */
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

    /**
     * Stops the timer task. It is important to always call this method before
     * closing the application, because if left running the timer will hang the
     * parent process in a zombie state.
     */
    public void stop() {
        this.timer.cancel();
    }

    /**
     * Formats the time value in a visually pleasing way.
     *
     * @param minutes the elapsed minutes.
     * @param seconds the elapsed seconds.
     * @return the formatted value.
     */
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
