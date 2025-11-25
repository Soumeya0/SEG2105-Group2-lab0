import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 * Class that plays music files at given paths and blocks the calling thread
 * until the clip has finished playing.
 */
public class FilePlayer {

    /**
     * Plays an audio clip located at the given path.
     * This method is synchronized and blocks the calling thread until the clip
     * has completely finished.
     * @param filePath the path to the audio clip that should be played
     */
    public synchronized void play(String filePath) {
        try {
            // Get the audio input stream from the file path
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();

            // Open the clip and add a listener to wait for the STOP event
            clip.open(audioInputStream);

            // Use an object to hold the monitor lock and wait for the audio to finish
            final Object lock = new Object();
            
            // LineListener is triggered when the clip starts or stops
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        // When the clip stops, notify the waiting thread
                        synchronized (lock) {
                            lock.notifyAll();
                        }
                    }
                }
            });

            // Start the clip
            clip.start();

            // Wait until the LineListener notifies us that the clip has stopped
            synchronized (lock) {
                // If the clip is still running (Active), wait
                if (clip.isRunning()) {
                    lock.wait();
                }
            }
            
            // Clean up resources
            clip.close();
            audioInputStream.close();

        } catch (Exception e) {
            System.out.println("Error with playing sound at " + filePath);
            e.printStackTrace();
        }
    }
}