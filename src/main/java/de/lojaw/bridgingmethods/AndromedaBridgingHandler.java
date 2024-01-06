// AndromedaBridgingHandler.java
package de.lojaw.bridgingmethods;

import de.lojaw.BridgingModClient;
import de.lojaw.jni.KeyboardInputHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.awt.*;
import java.awt.event.InputEvent;

enum Direction {
    NORTH, SOUTH, EAST, WEST; // Richtungen, zwischen denen gewechselt werden kann
}

public class AndromedaBridgingHandler {
    private static final int TICKS_PER_SECOND = 20; // Minecraft läuft mit 20 Ticks pro Sekunde
    private static int remainingTicks;
    private static boolean toggleDirection = false;
    private static boolean isSTastePressed = false;

    private static boolean isDTastePressed = false;
    private static int dTasteCycleCounter = 0;
    private static int tickCounter = 0; // Neuer Zähler für Ticks
    private static int wTasteTickCounter = 0;
    private static boolean isWTastePressed = false;
    private static final int W_TASTE_TICKS = 60; // Anzahl der Ticks, die die W-Taste gedrückt gehalten wird


    public static void executeAndromedaBridging(MinecraftClient client, int durationInSeconds) {
        BridgingModClient.andromedaBridgingEnabled = true;
        remainingTicks = durationInSeconds * TICKS_PER_SECOND;

        // Initialisieren der Tastenstatus
        isSTastePressed = true;
        isDTastePressed = true; // Initialisieren, dass D-Taste gedrückt ist
        dTasteCycleCounter = 0; // Start des Zyklus für D-Taste

        // Sofortiges Drücken von S und D
        //KeyboardInputHandler.getInstance().pressKey('S');
        //KeyboardInputHandler.getInstance().pressKey('D');
    }

    public static void update() {
        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity player = mc.player;

        if (BridgingModClient.andromedaBridgingEnabled) {

            switch (tickCounter % 3) {
                case 0:
                    if (isWTastePressed) {
                        //KeyboardInputHandler.getInstance().releaseKey('W'); // W-Taste loslassen
                        isWTastePressed = false;
                    }
                    //setPlayerFacing(player, -142.6F, 79.3F); // 1. Tick
                    break;
                case 1:
                    //setPlayerFacing(player, -146.5F, -62.3F); // 2. Tick
                    break;
                case 2:
                    setPlayerFacing(player, 1.5F, -7.1F); // 3. Tick
                    KeyboardInputHandler.getInstance().pressKey('W'); // W-Taste drücken
                    isWTastePressed = true;
                    break;
            }

            tickCounter++;
            if (tickCounter >= 3) tickCounter = 0; // Zurücksetzen des Zählers

            remainingTicks--;
            if (remainingTicks <= 0) {
                endAndromedaBridging();
                tickCounter = 0; // Zähler zurücksetzen beim Beenden
            }
        }
    }

    public static void performRightClick() {
        Robot robot;
        try {
            robot = new Robot();
            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK); // Führt Rechtsklick aus
            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK); // Lässt Rechtsklick los
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private static void handleDTasteCycle() {
        if (dTasteCycleCounter < 20) { // 800 Millisekunden gedrückt halten
            if (!isDTastePressed) {
                KeyboardInputHandler.getInstance().pressKey('D');
                isDTastePressed = true;
            }
        } else if (dTasteCycleCounter < 24) { // 400 Millisekunden loslassen
            if (isDTastePressed) {
                KeyboardInputHandler.getInstance().releaseKey('D');
                isDTastePressed = false;
            }
        }

        dTasteCycleCounter++;
        if (dTasteCycleCounter >= 24) {
            dTasteCycleCounter = 0; // Zyklus zurücksetzen
        }
    }

    private static void endAndromedaBridging() {
        if (isDTastePressed) {
            KeyboardInputHandler.getInstance().releaseKey('D');
        }

        KeyboardInputHandler.getInstance().releaseKey('W');
        if (wTasteTickCounter > 0) {
            KeyboardInputHandler.getInstance().releaseKey('W'); // Stellen Sie sicher, dass die W-Taste freigegeben wird
            wTasteTickCounter = 0; // Setzen Sie den Zähler zurück, um weiteres Drücken zu verhindern
        }

        isSTastePressed = false;
        isDTastePressed = false;
        BridgingModClient.andromedaBridgingEnabled = false;

        // Zusätzliche Aktionen, wenn notwendig, um den Modus ordnungsgemäß zu beenden
    }

    private static void setPlayerFacing(PlayerEntity player, float yaw, float pitch) {
        player.setYaw(yaw);
        player.setPitch(pitch);
        performRightClick(); // Rechtsklick ausführen
    }

}

// facing north (torwards negative z) (-142.6 / 79.3) und (torwards negative z)  (-146.5 / -62.3)
