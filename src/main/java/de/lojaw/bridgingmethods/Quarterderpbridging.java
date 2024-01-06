package de.lojaw.bridgingmethods;

import de.lojaw.jni.KeyboardInputHandler;
import net.minecraft.entity.player.PlayerEntity;

public class Quarterderpbridging {
    private static final float[] YAW_VALUES = {-141.89992F, 953.125F};
    private static final float[] PITCH_VALUES = {79.34989F, 75.749565F};

    private static int tickCounter = 0;
    private static boolean isActive = false;
    private static int remainingTicks;


    public static void executeQuarterderpbridging(PlayerEntity player, int durationInSeconds) {
        isActive = true;
        remainingTicks = durationInSeconds * 20; // 20 Ticks pro Sekunde

        // Initialisierung oder zusätzliche Logik, falls erforderlich
    }

    public static void update(PlayerEntity player) {

        if (!isActive) {
            return;
        }

        KeyboardInputHandler handler = new KeyboardInputHandler();
        int[] mousePosition = handler.getMousePosition();
        if (mousePosition.length >= 2) {
            System.out.println("Mausposition: X = " + mousePosition[0] + ", Y = " + mousePosition[1]);
        } else {
            System.out.println("Fehler beim Abrufen der Mausposition");
        }

        AndromedaBridgingHandler.performRightClick();
        if (tickCounter < YAW_VALUES.length) {
            //setPlayerFacing(player, YAW_VALUES[tickCounter], PITCH_VALUES[tickCounter]);
            tickCounter++;
        }

        if (tickCounter >= YAW_VALUES.length) {
            tickCounter = 0; // Zurücksetzen des Zählers
        }

        remainingTicks--;
        if (remainingTicks <= 0) {
            endQuarterderpbridging();
        }
    }

    private static void endQuarterderpbridging() {
        isActive = false;
        tickCounter = 0;
        remainingTicks = 0;

        // Zusätzliche Aktionen, wenn notwendig, um den Modus ordnungsgemäß zu beenden
    }

    private static void setPlayerFacing(PlayerEntity player, float yaw, float pitch) {
        player.setYaw(yaw);
        player.setPitch(pitch);
        // Zusätzliche Aktionen, falls erforderlich
    }
}

