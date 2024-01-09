package de.lojaw.jni;

public class NativeOverlayRenderer {
    static {
        try {
            System.out.println("java.library.path: " + System.getProperty("java.library.path"));
            System.loadLibrary("TransparentOverlayDLL");
            System.out.println("TransparentOverlayDLL loaded successfully.");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Error loading TransparentOverlayDLL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static native void initOverlay(); // Native Methode für das Rendering
    public static native void renderOverlay();
    public static native void destroyOverlay();
    public static native byte[] getTextImage(); // Neue Methode, um das Bild zu erhalten
    public static native void renderMinecraftOverlay(); // Neue Methode, um den Minecraft-Overlay-Code auszuführen
}
