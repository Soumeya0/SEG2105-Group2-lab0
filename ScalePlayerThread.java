/**
 * A Runnable class that plays a scale sequence, synchronized 
 * to ensure sequential notes (do, re, mi, fa, sol, la, si) 
 * and simultaneous final note (do-octave).
 */
public class ScalePlayerThread implements Runnable {

    // The shared object used for synchronization (wait/notify)
    private final Object monitor;
    // The shared counter for the current note in the sequence (0-7)
    private final int[] currentNoteIndex;
    private final FilePlayer player;
    private final String[] notesToPlay;
    private final String threadName;

    /**
     * Constructor for the ScalePlayerThread.
     * @param monitor The lock object for wait/notify.
     * @param currentNoteIndex The shared array holding the index of the note to play next.
     * @param player The FilePlayer instance to play audio.
     * @param notesToPlay The specific notes this thread is responsible for.
     * @param threadName The name of the thread (for logging).
     */
    public ScalePlayerThread(Object monitor, int[] currentNoteIndex, FilePlayer player, String[] notesToPlay, String threadName) {
        this.monitor = monitor;
        this.currentNoteIndex = currentNoteIndex;
        this.player = player;
        this.notesToPlay = notesToPlay;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        // High Notes (T1): do(0), mi(2), sol(4), si(6), do-octave(7)
        // Low Notes (T2): re(1), fa(3), la(5), do-octave(7)

        int noteIndexInSequence = -1;

        for (int i = 0; i < notesToPlay.length; i++) {
            
            // Determine the global index this note corresponds to (0 to 7).
            if (notesToPlay[i].equals("do.wav")) noteIndexInSequence = 0;
            else if (notesToPlay[i].equals("re.wav")) noteIndexInSequence = 1;
            else if (notesToPlay[i].equals("mi.wav")) noteIndexInSequence = 2;
            else if (notesToPlay[i].equals("fa.wav")) noteIndexInSequence = 3;
            else if (notesToPlay[i].equals("sol.wav")) noteIndexInSequence = 4;
            else if (notesToPlay[i].equals("la.wav")) noteIndexInSequence = 5;
            else if (notesToPlay[i].equals("si.wav")) noteIndexInSequence = 6;
            else if (notesToPlay[i].equals("do-octave.wav")) noteIndexInSequence = 7;
            
            // Notes before the final simultaneous note (index 7) must be synchronized
            if (noteIndexInSequence < 7) {
                synchronized (monitor) {
                    try {
                        // Wait for the correct turn
                        while (currentNoteIndex[0] != noteIndexInSequence) {
                            System.out.println(threadName + " waiting for turn " + noteIndexInSequence + ". Current: " + currentNoteIndex[0]);
                            monitor.wait();
                        }
    
                        // Play the note
                        System.out.println(threadName + " is playing " + notesToPlay[i] + " at index " + currentNoteIndex[0]);
                        player.play(notesToPlay[i]);
    
                        // Increment the shared index for the next note
                        currentNoteIndex[0]++;
    
                        // Notify the other thread that the turn is over
                        monitor.notifyAll();
    
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println(threadName + " interrupted.");
                    }
                }
            } else if (noteIndexInSequence == 7) {
                 // Final simultaneous note (do-octave) is played outside the synchronized block
                 // after index 6 is complete.
                 synchronized(monitor) {
                     try { // Added try block to handle InterruptedException
                         // Wait until the sequential part of the scale is complete (index 7 is reached)
                         while (currentNoteIndex[0] < 7) {
                             monitor.wait();
                         }
                     } catch (InterruptedException e) {
                         Thread.currentThread().interrupt();
                         System.out.println(threadName + " interrupted while waiting for final note.");
                         return; // Exit run method if interrupted
                     }
                 }
                
                 // Play the final note concurrently
                 System.out.println(threadName + " is playing final (concurrent) " + notesToPlay[i] + " at index 7.");
                 player.play(notesToPlay[i]);
                 // We don't increment currentNoteIndex here to allow the other thread to also play.
            }
        }
        System.out.println(threadName + " finished.");
    }
}