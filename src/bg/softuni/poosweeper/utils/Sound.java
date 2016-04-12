package bg.softuni.poosweeper.utils;

import javafx.scene.media.AudioClip;

import java.util.Random;

/**
 * Utility class for playing audio clip files.
 */
class Sound {

    private static final String FILE_RESOURCES_SOUNDS = "file:resources/sounds/";
    private static final String EXTENSION_MP3 = ".mp3";
    private static final Random random = new Random();

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

    /**
     * Play an audio clip with the given file name.
     *
     * @param fileName the name of the sound file.
     */
    private static void play(String fileName) {
        AudioClip audioClip = new AudioClip(fileName);
        audioClip.play();
    }
}
