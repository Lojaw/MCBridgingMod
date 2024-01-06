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
        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler();

        if (BridgingModClient.derpbridgingEnabled) {
            tickCounter++; // Erhöhe den Tick-Zähler

            switch (tickCounter) {
                case 1:
                    setupInitialPlayerState(player);
                    break;
                case 2:
                case 7:
                case 12:
                case 17:
                    activateForwardMovement(player, keyboardInputHandler);
                    break;
                case 6:
                case 11:
                case 16:
                case 21:
                    stopForwardMovementAndClick(player, keyboardInputHandler);
                    break;
                // Fügen Sie hier weitere Fälle hinzu, falls erforderlich
            }

            remainingTicks--;
            if (remainingTicks <= 0) {
                endDerpbridging(keyboardInputHandler);
            }
        }
    }

    private static void setupInitialPlayerState(PlayerEntity player) {
        assert player != null;
        player.updatePosition(-2.77015, -58.00000, 111.29794);
        player.setYaw(-134.80118F);
        player.setPitch(78.00030F);
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

    private static void endDerpbridging(KeyboardInputHandler keyboardInputHandler) {
        BridgingModClient.derpbridgingEnabled = false;
        tickCounter = 0;
        remainingTicks = 0;
        if (isWTastePressed) {
            keyboardInputHandler.releaseKey('W');
            isWTastePressed = false;
        }
    }

    private static void setPlayerFacing(PlayerEntity player, float yaw, float pitch) {
        player.setYaw(yaw);
        player.setPitch(pitch);
    }
}
