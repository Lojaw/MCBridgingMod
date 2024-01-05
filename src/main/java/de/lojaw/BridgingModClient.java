package de.lojaw;

import de.lojaw.bridgingmethods.AndromedaBridgingHandler;
import de.lojaw.jni.KeyboardInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class BridgingModClient implements ClientModInitializer {

    public static boolean andromedaBridgingEnabled = true;

    public enum MouseButtonType {
        LEFT_CLICK,
        RIGHT_CLICK
    }

    @Override
    public void onInitializeClient() {
        System.setProperty("java.awt.headless", "false");

        ClientSendMessageEvents.ALLOW_CHAT.register((message) -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            String[] args = message.split(" ");

            if (args.length > 0) {
                String command = args[0].toLowerCase();

                switch (command) {
                    case "click":
                        // Überprüfe, ob genug Parameter für den "click"-Befehl vorhanden sind
                        if (args.length >= 4) {
                            // Lese die Parameter
                            MouseButtonType mouseButtonType = MouseButtonType.valueOf(args[1].toUpperCase());
                            int clicksPerSecond = Integer.parseInt(args[2]);
                            int timeInSeconds = Integer.parseInt(args[3]);

                            return false;
                        } else {
                            // Wenn nicht genug Parameter vorhanden sind, informiere den Spieler
                            if (player != null) {
                                player.sendMessage(Text.of("[YourMod] Der Befehl 'click' erfordert drei Parameter: <mouseButtonType> <clicksPerSecond> <timeInSeconds>."), false);
                            }
                            return false;
                        }

                    case "andromeda":
                        if(player != null) {
                            AndromedaBridgingHandler.executeAndromedaBridging(MinecraftClient.getInstance(), 10); // 10 Sekunden lang
                        }
                        return false;

                    default:
                        if (player != null) {
                            player.sendMessage(Text.of("[YourMod] Unbekannter Befehl."), false);
                        }
                        return false;
                }
            } else {
                // Liste von Befehlen senden
                if (player != null) {
                    player.sendMessage(Text.of("[YourMod] Liste der verfügbaren Befehle:"), false);
                    // TODO: Liste der verfügbaren Befehle
                }
                return false;
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            PlayerEntity player = mc.player;
            if(player != null) {
                AndromedaBridgingHandler.update(); // Aufruf der Update-Methode pro Tick
            }
        });
    }
}