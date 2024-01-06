// AndromedaBridgingHandler.java
package de.lojaw.bridgingmethods;

import de.lojaw.BridgingModClient;
import de.lojaw.jni.KeyboardInputHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

public class Derpbridging {
    private static int tickCounter = 0;
    private static int remainingTicks;
    private static boolean isWTastePressed = false;

    public static void executeDerpbridgingBridging(MinecraftClient client, double durationInSeconds) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity player = mc.player;
        if (player != null) {

            BridgingModClient.derpbridgingEnabled = true;
            remainingTicks = (int) Math.ceil(durationInSeconds * 20); // Runde auf die nächste ganze Zahl
        }
    }

    public static void update() {
        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity player = mc.player;


        if (BridgingModClient.derpbridgingEnabled) {
            tickCounter++; // Erhöhe den Tick-Zähler zu Beginn der Methode
            KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler();

            if (tickCounter == 1) {
                startDerpBridging(player); // Aufruf der Startmethode nur beim ersten Tick
            }

            // Wiederhole die Aktionen in regelmäßigen Intervallen
            if (tickCounter % 5 == 1) {
                activateForwardMovement(player, keyboardInputHandler);
            } else if (tickCounter % 5 == 0) {
                stopForwardMovementAndClick(player, keyboardInputHandler);
            }

            remainingTicks--;
            if (remainingTicks <= 0) {
                endDerpBridging();
            }
        }
    }



    private static void endDerpBridging() {
        BridgingModClient.derpbridgingEnabled = false;
        tickCounter = 0; // Zähler zurücksetzen
        remainingTicks = 0; // Timer zurücksetzen

        if(isWTastePressed) {
            KeyboardInputHandler.getInstance().releaseKey('W');
        }

    }

    private static void startDerpBridging(PlayerEntity player) {
        // Setze die Position und Ausrichtung des Spielers direkt
        player.updatePosition(-2.77015, -58.00000, 111.29794);
        setPlayerFacing(player, -134.80118F, 78.00030F);
        AndromedaBridgingHandler.performRightClick();
    }

    private static void activateForwardMovement(PlayerEntity player, KeyboardInputHandler keyboardInputHandler) {
        setPlayerFacing(player, 0.04946F, 81.75034F);
        keyboardInputHandler.pressKey('W');
        isWTastePressed = true;
    }

    private static void stopForwardMovementAndClick(PlayerEntity player, KeyboardInputHandler keyboardInputHandler) {
        keyboardInputHandler.releaseKey('W');
        isWTastePressed = false;
        setPlayerFacing(player, -134.80118F, 78.00030F);
        AndromedaBridgingHandler.performRightClick();
    }

    private static void setPlayerFacing(PlayerEntity player, float yaw, float pitch) {
        player.setYaw(yaw);
        player.setPitch(pitch);
    }
}
