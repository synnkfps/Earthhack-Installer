package me.earthhack.installer.util;

@FunctionalInterface
public interface SafeRunnable extends Runnable {
    void runSafely() throws Throwable;

    default void run() {
        try {
            this.runSafely();
        } catch (Throwable var2) {
            this.handle(var2);
        }

    }

    default void handle(Throwable t) {
        t.printStackTrace();
    }
}
