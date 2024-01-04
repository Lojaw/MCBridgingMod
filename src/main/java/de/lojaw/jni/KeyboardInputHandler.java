package de.lojaw.jni;

public class KeyboardInputHandler {
    public KeyboardInputHandler() {
    }

    // Deklaration der nativen Methoden
    public native void sendSignal();
    public native void pressKey(char key);
    public native void releaseKey(char key);
    private static KeyboardInputHandler instance;

    // Laden der nativen Bibliothek
    static {
        System.loadLibrary("SimulateHardwareInputs");
    }

    public static KeyboardInputHandler getInstance() {
        if (instance == null) {
            instance = new KeyboardInputHandler(30); // Beispiel: 30 Sekunden lang
        }
        return instance;
    }

    private boolean pressA = true;
    private int tickCount = 0;
    private final int toggleInterval = 20; // Wechselt jede Sekunde (20 Ticks)
    private int totalTicks = 0;
    private boolean isRunning = false;

    public KeyboardInputHandler(int totalDurationSeconds) {
        this.totalTicks = totalDurationSeconds * 20; // 20 Ticks pro Sekunde
    }

    public void startAlternatingKeys() {
        pressKey('W');
        isRunning = true;
    }

    public void updateOnTick() {
        if (!isRunning) {
            return;
        }

        if (tickCount % toggleInterval == 0) {
            if (pressA) {
                pressKey('A');
                releaseKey('D');
            } else {
                pressKey('D');
                releaseKey('A');
            }
            pressA = !pressA;
        }

        if (tickCount >= totalTicks) {
            stopAlternatingKeys();
        }

        tickCount++;
    }

    public void stopAlternatingKeys() {
        releaseKey('W');
        releaseKey('A');
        releaseKey('D');
        isRunning = false;
    }
}
