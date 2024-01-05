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
    private static boolean isFacingChanged = false;
    private static boolean isRightClickNeeded = false;
    private static boolean toggleDirection = false;
    private static boolean isSTastePressed = false;
    private static int dTasteTickCounter = 0;

    private static boolean isDTastePressed = false;
    private static int dTasteCycleCounter = 0;


    public static void executeAndromedaBridging(MinecraftClient client, int durationInSeconds) {
        BridgingModClient.andromedaBridgingEnabled = true;
        remainingTicks = durationInSeconds * TICKS_PER_SECOND;

        isFacingChanged = true;
        isSTastePressed = true; // S-Taste wird gedrückt, sobald Andromeda-Bridging aktiviert wird
        dTasteTickCounter = 0;
    }

    public static void update() {
        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity player = mc.player;

        if (BridgingModClient.andromedaBridgingEnabled) {
            //if (isRightClickNeeded) {
                //performRightClick();
                //isRightClickNeeded = false; // Rechtsklick wurde durchgeführt, also zurücksetzen
            //}

            //if (isFacingChanged) {
                // Warten für 2 Ticks (100 Millisekunden) nach jedem Blickwinkelwechsel, bevor der Rechtsklick durchgeführt wird
                //if (remainingTicks % (TICKS_PER_SECOND / 20) == 0) {
                    //isRightClickNeeded = true; // Rechtsklick soll durchgeführt werden
                    //isFacingChanged = false; // Blickwinkel wurde geändert, also zurücksetzen
                //}
            //}

            if (remainingTicks % 1 == 0) {
                performRightClick();
            }

            if (remainingTicks % 1 == 0) { // Wechselt die Blickrichtung bei jedem Tick
                if (toggleDirection) {
                    player.setYaw(-142.6F);
                    player.setPitch(79.3F);
                } else {
                    //player.setYaw(-146.5F);
                    //player.setPitch(-62.3F);
                }
                toggleDirection = !toggleDirection; // Umschalten der Richtung
            }

                // S-Taste bei jedem Tick drücken
                KeyboardInputHandler.getInstance().pressKey('S');

                // D-Taste-Zyklus
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



            remainingTicks--;
            if (remainingTicks <= 0) {
                KeyboardInputHandler.getInstance().releaseKey('S');
                AndromedaBridgingHandler.isSTastePressed = false;
                BridgingModClient.andromedaBridgingEnabled = false;
            }
        }
    }

    private static void performRightClick() {
        Robot robot;
        try {
            robot = new Robot();
            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK); // Führt Rechtsklick aus
            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK); // Lässt Rechtsklick los
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

}

// facing north (torwards negative z) (-142.6 / 79.3) und (torwards negative z)  (-146.5 / -62.3)
