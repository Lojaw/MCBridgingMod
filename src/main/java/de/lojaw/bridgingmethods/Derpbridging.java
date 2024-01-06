// AndromedaBridgingHandler.java
package de.lojaw.bridgingmethods;

import de.lojaw.BridgingModClient;
import de.lojaw.jni.KeyboardInputHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.awt.*;
import java.awt.event.InputEvent;

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

            tickCounter++; // Erhöhe den Tick-Zähler zu Beginn der Methode

            //
            if (tickCounter == 1) {
                // Setze die Position und Ausrichtung des Spielers direkt
                assert player != null;
                player.updatePosition(-2.77015, -58.00000, 111.29794);
                player.setYaw(-134.80118F);
                player.setPitch(78.00030F);
                AndromedaBridgingHandler.performRightClick();
            }
            if (tickCounter == 2) {
                setPlayerFacing(player, 0.04946F, 81.75034F);
                keyboardInputHandler.pressKey('W');
                isWTastePressed = true;
            }
            if (tickCounter == 3) {
            }
            if (tickCounter == 4) {

            }
            if (tickCounter == 5) {
                //keyboardInputHandler.releaseKey('W');
            }
            if (tickCounter == 6) {
                keyboardInputHandler.releaseKey('W'); //bisschen zu weit
                isWTastePressed = false;
                setPlayerFacing(player, -134.80118F, 78.00030F);
                AndromedaBridgingHandler.performRightClick();
            }
            if (tickCounter == 7) {
                setPlayerFacing(player, 0.04946F, 81.75034F);
                keyboardInputHandler.pressKey('W');
            }
            if (tickCounter == 8) {

            }
            if (tickCounter == 9) {
            }
            if (tickCounter == 10) {
            }
            if (tickCounter == 11) {
                keyboardInputHandler.releaseKey('W'); //bisschen zu weit
                setPlayerFacing(player, -134.80118F, 78.00030F);
                AndromedaBridgingHandler.performRightClick();
            }
            if (tickCounter == 12) {
                setPlayerFacing(player, 0.04946F, 81.75034F);
                keyboardInputHandler.pressKey('W');
            }
            if (tickCounter == 13) {

            }
            if (tickCounter == 14) {

            }
            if (tickCounter == 15) {

            }
            if (tickCounter == 16) {
                keyboardInputHandler.releaseKey('W'); //bisschen zu weit
                setPlayerFacing(player, -134.80118F, 78.00030F);
                AndromedaBridgingHandler.performRightClick();
            }
            if (tickCounter == 17) {
                setPlayerFacing(player, 0.04946F, 81.75034F);
                keyboardInputHandler.pressKey('W');
            }
            if (tickCounter == 18) {
            }
            if (tickCounter == 19) {
            }
            if (tickCounter == 20) {
            }
            if (tickCounter == 21) {
                keyboardInputHandler.releaseKey('W'); //bisschen zu weit
                setPlayerFacing(player, -134.80118F, 78.00030F);
                AndromedaBridgingHandler.performRightClick();
            }
            //


            //if (tickCounter % 2 == 0) {
                //setPlayerFacing(player, -134.80118F, 78.00030F); // 1. Tick, 3. Tick, 5. Tick, ...
            //} else {
                //setPlayerFacing(player, -20.05F, 73.65F); // 2. Tick, 4. Tick, 6. Tick, ...
            //}

            remainingTicks--;
            if (remainingTicks <= 0) {
                endDerpBridging();
                tickCounter = 0; // Zähler zurücksetzen beim Beenden
            }

        }
    }



    private static void endDerpBridging() {
        BridgingModClient.derpbridgingEnabled = false;
        tickCounter = 0; // Zähler zurücksetzen
        remainingTicks = 0; // Timer zurücksetzen

        if() {
            KeyboardInputHandler.getInstance().releaseKey('W');
        }

    }

    private static void setPlayerFacing(PlayerEntity player, float yaw, float pitch) {
        player.setYaw(yaw);
        player.setPitch(pitch);
    }
}
