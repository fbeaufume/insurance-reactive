package com.adeliosys.insurance;

/**
 * Utility methods.
 */
public class Utils {

    /**
     * Return the duration in msec between a System.nanoTime() timestamp and now.
     */
    public static long getDuration(long timestamp) {
        return (System.nanoTime() - timestamp) / 1_000_000L;
    }

    /**
     * Pause for a given duration in msec.
     */
    public static void pause(long duration) {
        if (duration > 0) {
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
