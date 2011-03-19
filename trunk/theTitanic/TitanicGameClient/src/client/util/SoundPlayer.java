package client.util;

/**
 * Simple SoundPlayer
 * It provides you to play audio files of common formats.
 * @author danon
 */

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer extends Thread {

    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
    private URL soundFile;
    private float soundVolume;

    public SoundPlayer(URL src){
        soundFile = src;
        soundVolume = 0.999f;
    }

    public SoundPlayer(URL src, float vol){
        soundFile = src;
        soundVolume = vol;
    }

    /**
     * Play sound file (in new thread) with specified volume
     * @param src URL of the audio file to play;
     * @param vol Relative volume value [0.0f, 1.0f)
     */
    public static void play(URL src, float vol){
        if(vol>=1.0f) vol = 0.999f;
        Thread t = new SoundPlayer(src, vol);
        t.start();
    }

    /**
     * Play sound file (in new thread) with default volume
     * @param src URL of the audio file to play;
     */
    public static void play(URL src){
        Thread t = new SoundPlayer(src);
        t.start();
    }

    @Override
    public void run() {

        AudioInputStream audioInputStream = null;
        AudioFormat format = null;
        SourceDataLine line = null;

        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            format = audioInputStream.getFormat();
        } catch (UnsupportedAudioFileException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
        } catch (LineUnavailableException e) {
            System.err.println(e.getMessage());
        }

        line.start();

        FloatControl volume = (FloatControl)line.getControl(FloatControl.Type.VOLUME);
        volume.setValue(volume.getMaximum()*soundVolume);

        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                { line.write(abData, 0, nBytesRead); }
            }
        } catch (IOException e) {
           System.err.println(e.getMessage());
            return;
        } finally {
            line.drain();
            line.close();
        }
    }
}