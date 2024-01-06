package de.lojaw;

import de.lojaw.bridgingmethods.AndromedaBridgingHandler;
import de.lojaw.bridgingmethods.Derpbridging;
import de.lojaw.bridgingmethods.Quarterderpbridging;
import de.lojaw.jni.KeyboardInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class BridgingModClient implements ClientModInitializer {

    public static boolean andromedaBridgingEnabled = false;
    public static boolean derpbridgingEnabled = false;
    private static int rightClickDurationTicks = 0;

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

                    case "quarterderp":
                        if(player != null) {
                            // Rufen Sie hier die execute-Methode von Quarterderpbridging auf
                            // Zum Beispiel mit einer festen Dauer von 10 Sekunden
                            Quarterderpbridging.executeQuarterderpbridging(player, 5);
                        }
                        return false;

                    case "derpbridge":
                        if(player != null) {
                            // Überprüfe, ob die richtige Anzahl von Argumenten vorhanden ist
                            if(args.length >= 2) {
                                try {
                                    // Versuche, das Argument in einen Integer umzuwandeln
                                    int durationInSeconds = Integer.parseInt(args[1]);

                                    // Überprüfe, ob die Dauer positiv ist
                                    if(durationInSeconds > 0) {
                                        Derpbridging.executeDerpbridgingBridging(MinecraftClient.getInstance(), durationInSeconds);
                                    } else {
                                        player.sendMessage(Text.of("[YourMod] Der Wert für 'durationInSeconds' muss positiv sein."), false);
                                    }
                                } catch (NumberFormatException e) {
                                    // Fange die Ausnahme ab, falls das Argument keine gültige Zahl ist
                                    player.sendMessage(Text.of("[YourMod] 'durationInSeconds' muss eine gültige Zahl sein."), false);
                                }
                            } else {
                                // Informiere den Spieler, wenn nicht genügend Argumente vorhanden sind
                                player.sendMessage(Text.of("[YourMod] Der Befehl 'derpbridge' erfordert einen Parameter: <durationInSeconds>."), false);
                            }
                        }
                        return false;

                    case "rightclick":
                        if(args.length >= 2) {
                            int durationInSeconds = Integer.parseInt(args[1]);
                            rightClickDurationTicks = durationInSeconds * 20;
                        } else {
                            if (player != null) {
                                player.sendMessage(Text.of("[YourMod] Der Befehl 'rightclick' erfordert einen Parameter: <durationInSeconds>."), false);
                            }
                        }
                        return false;

                    case "getcoords":
                        if (player != null) {
                            float yaw = player.getYaw();
                            float pitch = player.getPitch();
                            String coords = yaw + " " + pitch;

                            // Kopieren in die Zwischenablage
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            StringSelection stringSelection = new StringSelection(coords);
                            clipboard.setContents(stringSelection, null);

                            player.sendMessage(Text.of("[YourMod] Koordinaten kopiert: " + coords), false);
                        }
                        return false;

                    case "getcoords2":
                        if (player != null) {
                            // Extrahiere die Koordinaten und Ausrichtung des Spielers
                            double x = player.getX();
                            double y = player.getY();
                            double z = player.getZ();
                            float yaw = player.getYaw();
                            float pitch = player.getPitch();

                            // Erstelle die Nachricht mit den Koordinaten und der Ausrichtung
                            String coordsMessage = String.format("X=%.2f, Y=%.2f, Z=%.2f, Yaw=%.2f, Pitch=%.2f", x, y, z, yaw, pitch);

                            // Kopiere die Nachricht in die Zwischenablage
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            StringSelection stringSelection = new StringSelection(coordsMessage);
                            clipboard.setContents(stringSelection, null);

                            // Sende die Nachricht an den Spieler
                            player.sendMessage(Text.of("[YourMod] Koordinaten kopiert: " + coordsMessage), false);
                        }
                        return false;

                    default:
                        if (player != null) {
                            player.sendMessage(Text.of("[YourMod] Unbekannter Befehl."), false);
                        }
                        return true;
                }
            } else {
                // Liste von Befehlen senden
                if (player != null) {
                    player.sendMessage(Text.of("[YourMod] Liste der verfügbaren Befehle:"), false);
                    // TODO: Liste der verfügbaren Befehle
                }
                return true;
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            PlayerEntity player = mc.player;
            if(player != null) {
                //AndromedaBridgingHandler.update(); // Aufruf der Update-Methode pro Tick
                //Quarterderpbridging.update(player);
                Derpbridging.update();
            }

            //if(rightClickDurationTicks > 0) {
                //AndromedaBridgingHandler.performRightClick();
                //rightClickDurationTicks--;
            //}
        });
    }
}