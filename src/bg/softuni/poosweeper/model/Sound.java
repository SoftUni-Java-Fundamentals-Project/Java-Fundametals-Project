package bg.softuni.poosweeper.model;

import javafx.scene.media.AudioClip;

import java.util.Random;

/**
 * Utility class for playing audio clip files.
 */
public class Sound {

    public static final String FILE_RESOURCES_SOUNDS = "file:resources/sounds/";
    public static final String EXTENSION_MP3 = ".mp3";
    private static Random random = new Random();

    /**
     * Play an audio clip with the given file name.
     *
     * @param fileName the name of the sound file.
     */
    public static void play(String fileName) {
        AudioClip audioClip = new AudioClip(fileName);
        audioClip.play();
    }

    /**
     * Play a random fart audio clip.
     */
    public static void playRandomFartClip() {
        int index = random.nextInt(7) + 1;
        play(FILE_RESOURCES_SOUNDS + "fart" + index + EXTENSION_MP3);
    }

    /**
     * Play winning audio clip.
     */
    public static void playWinningClip() {
        play(FILE_RESOURCES_SOUNDS + "winning" + EXTENSION_MP3);
    }
}
