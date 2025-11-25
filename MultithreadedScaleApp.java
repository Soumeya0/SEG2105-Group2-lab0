/**
 * Main application class to demonstrate multithreading and synchronization 
 * for two distinct music tasks: 
 * 1. Playing the "do, re, mi..." scale sequentially with a simultaneous final note.
 * 2. Playing "Twinkle Twinkle Little Star" sequentially.
 */
public class MultithreadedScaleApp {

    // Shared utility and state across all tasks
    private static final FilePlayer PLAYER = new FilePlayer();

    // --- TASK 2: TWINKLE TWINKLE LITTLE STAR DATA ---
    private static final String[] SONG_NOTES = {
        // do do sol sol la la sol | fa fa mi mi re re do
        "do.wav", "do.wav", "sol.wav", "sol.wav", "la.wav", "la.wav", "sol.wav", 
        "fa.wav", "fa.wav", "mi.wav", "mi.wav", "re.wav", "re.wav", "do.wav",
        
        // sol sol fa fa mi mi re
        "sol.wav", "sol.wav", "fa.wav", "fa.wav", "mi.wav", "mi.wav", "re.wav", 
        
        // sol sol fa fa mi mi re
        "sol.wav", "sol.wav", "fa.wav", "fa.wav", "mi.wav", "mi.wav", "re.wav",
        
        // do do sol sol la la sol | fa fa mi mi re re do
        "do.wav", "do.wav", "sol.wav", "sol.wav", "la.wav", "la.wav", "sol.wav", 
        "fa.wav", "fa.wav", "mi.wav", "mi.wav", "re.wav", "re.wav", "do.wav"
    };

    // Mapping: Which thread is responsible for which note in the sequence.
    // 1 -> Thread 1 (do, mi, sol, si, do-octave)
    // 2 -> Thread 2 (re, fa, la, do-octave)
    private static final int[] SONG_THREAD_MAP = {
        1, 1, 1, 1, 2, 2, 1, 2, 2, 1, 1, 2, 2, 1, 
        1, 1, 2, 2, 1, 1, 2, 
        1, 1, 2, 2, 1, 1, 2, 
        1, 1, 1, 1, 2, 2, 1, 2, 2, 1, 1, 2, 2, 1 
    };

    public static void main(String[] args) {
        // Execute Task 1: Scale Playback
        runScaleTask();
        
        // Execute Task 2: Song Playback
        runSongTask();
        
        System.out.println("\nApplication finished running both music tasks.");
    }
    
    /**
     * Executes Task 1: Playing the "do, re, mi..." scale sequentially with a 
     * simultaneous final note.
     */
    private static void runScaleTask() {
        System.out.println("--- TASK 1: RUNNING SCALE (do, re, mi... with Simultaneous End) ---");
        
        // Task-specific shared resources
        final Object monitor = new Object();
        final int[] currentNoteIndex = {0}; // Tracks the index 0-7
        
        // Thread 1 notes: do, mi, sol, si, do-octave
        String[] t1Notes = {"do.wav", "mi.wav", "sol.wav", "si.wav", "do-octave.wav"};
        
        // Thread 2 notes: re, fa, la, do-octave
        String[] t2Notes = {"re.wav", "fa.wav", "la.wav", "do-octave.wav"};
        
        Thread thread1 = new Thread(
            new ScalePlayerThread(monitor, currentNoteIndex, PLAYER, t1Notes, "Thread-1 (T1 Notes)"), 
            "Thread-1"
        );
        
        Thread thread2 = new Thread(
            new ScalePlayerThread(monitor, currentNoteIndex, PLAYER, t2Notes, "Thread-2 (T2 Notes)"), 
            "Thread-2"
        );

        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("--- TASK 1 COMPLETE ---\n");
    }

    /**
     * Executes Task 2: Playing "Twinkle Twinkle Little Star" sequentially.
     */
    private static void runSongTask() {
        System.out.println("--- TASK 2: RUNNING SONG (Twinkle Twinkle Little Star) ---");
        
        if (SONG_NOTES.length != SONG_THREAD_MAP.length) {
            System.err.println("Error: Song notes array and thread map array must have the same length.");
            return;
        }

        // Task-specific shared resources
        final Object monitor = new Object();
        final int[] currentNoteIndex = {0}; // Tracks the index 0-41
        
        // Create the two threads, passing the full song data and their thread ID (1 or 2)
        Thread thread1 = new Thread(
            new SongPlayerThread(1, monitor, currentNoteIndex, PLAYER, SONG_NOTES, SONG_THREAD_MAP, "Thread-1 (T1 Notes)"), 
            "Thread-1"
        );
        
        Thread thread2 = new Thread(
            new SongPlayerThread(2, monitor, currentNoteIndex, PLAYER, SONG_NOTES, SONG_THREAD_MAP, "Thread-2 (T2 Notes)"), 
            "Thread-2"
        );

        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("--- TASK 2 COMPLETE ---");
    }
}