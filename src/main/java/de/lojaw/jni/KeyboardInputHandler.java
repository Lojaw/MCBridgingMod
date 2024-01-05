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
            instance = new KeyboardInputHandler();
        }
        return instance;
    }

}
