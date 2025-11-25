/**
 * A Runnable class that plays notes from a global song sequence, 
 * synchronized with another thread using a shared monitor. 
 * This thread plays notes only if it is the designated thread for the 
 * current global index.
 */
public class SongPlayerThread implements Runnable {

    private final int threadId; // 1 or 2 (This thread's ID)
    private final Object monitor; // Shared lock object
    private final int[] currentNoteIndex; // Shared counter for the global song position
    private final FilePlayer player;
    private final String[] songNotes; // Full sequence of note filenames
    private final int[] threadMap; // Map of required thread IDs (1 or 2) for each index
    private final String threadName;

    /**
     * Constructor for the SongPlayerThread.
     * @param threadId The ID of this thread (1 or 2).
     * @param monitor The lock object for wait/notify.
     * @param currentNoteIndex The shared array holding the index of the note to play next.
     * @param player The FilePlayer instance to play audio.
     * @param songNotes The full sequence of note filenames for the song.
     * @param threadMap The map defining which thread (1 or 2) plays each note index.
     * @param threadName The name of the thread (for logging).
     */
    public SongPlayerThread(int threadId, Object monitor, int[] currentNoteIndex, FilePlayer player, String[] songNotes, int[] threadMap, String threadName) {
        this.threadId = threadId;
        this.monitor = monitor;
        this.currentNoteIndex = currentNoteIndex;
        this.player = player;
        this.songNotes = songNotes;
        this.threadMap = threadMap;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        // Continue iterating as long as there are notes left to play
        while (currentNoteIndex[0] < songNotes.length) {
            
            int currentIndex = currentNoteIndex[0];
            
            // Assume it's not our turn until proven otherwise
            boolean myTurn = false;
            if (currentIndex < threadMap.length) {
                 // Determine if the required thread ID for the current note matches this thread's ID
                 if (threadMap[currentIndex] == threadId) {
                    myTurn = true;
                }
            } else {
                // The song has finished, exit the loop
                break;
            }

            synchronized (monitor) {
                try {
                    // While the current global index hasn't changed AND it's not my turn, wait.
                    while (currentNoteIndex[0] == currentIndex && !myTurn) {
                        System.out.println(threadName + " waiting for Index: " + currentIndex + ". Current Required Thread: " + threadMap[currentIndex]);
                        monitor.wait();
                        
                        // After waking up, re-evaluate the state
                        currentIndex = currentNoteIndex[0];
                        
                        if (currentIndex < threadMap.length) {
                            myTurn = (threadMap[currentIndex] == threadId);
                        } else {
                            break; // Song finished while waiting
                        }
                    }

                    // Final check to exit if the song is complete
                    if (currentIndex >= songNotes.length) {
                        break;
                    }
                    
                    // --- Synchronization point reached (It's my turn) ---

                    if (myTurn) {
                        String noteToPlay = songNotes[currentIndex];
                        
                        System.out.println(threadName + " is playing " + noteToPlay + " (Index: " + currentIndex + ")");
                        
                        // The player.play() call is blocking until the note finishes
                        player.play(noteToPlay);

                        // Only the thread that successfully played the note increments the shared index
                        currentNoteIndex[0]++;

                        // Notify the other thread(s) that the turn is over
                        monitor.notifyAll();
                    }


                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(threadName + " interrupted.");
                    break; 
                }
            } // end synchronized block
        } // end while loop
        System.out.println(threadName + " finished processing song.");
    }
}