package de.lojaw;

import com.mojang.blaze3d.systems.RenderSystem;
import de.lojaw.bridgingmethods.AndromedaBridgingHandler;
import de.lojaw.bridgingmethods.Derpbridging;
import de.lojaw.bridgingmethods.Quarterderpbridging;
import de.lojaw.jni.KeyboardInputHandler;
import de.lojaw.jni.NativeOverlayRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class BridgingModClient implements ClientModInitializer {

    public static boolean andromedaBridgingEnabled = false;
    public static boolean derpbridgingEnabled = false;
    private static int rightClickDurationTicks = 0;
    //
    private static Vec3d previousPosition = Vec3d.ZERO;
    private static long lastUpdateTime = 0;
    private static String speedText = "0.00 blocks/s";
    private static int tickCounter = 0;
    //

    public enum MouseButtonType {
        LEFT_CLICK,
        RIGHT_CLICK
    }

    @Override
    public void onInitializeClient() {
        System.setProperty("java.awt.headless", "false");
        //HudRenderCallback.EVENT.register(this::renderMyText);

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
                                    // Versuche, das Argument in einen Double umzuwandeln
                                    double durationInSeconds = Double.parseDouble(args[1]);

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

                    case "pressw":
                        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler();
                        keyboardInputHandler.pressKey('W');
                        return false;

                    case "getcoords":
                        if (player != null) {
                            // Ausgeben der OpenGL-Version
                            String glVersion = GL11.glGetString(GL11.GL_VERSION);
                            System.out.println("Aktuelle OpenGL-Version: " + glVersion);
                            NativeOverlayRenderer.renderOverlay();
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
                            NativeOverlayRenderer.renderOverlay();
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

            tickCounter++;
            if (tickCounter >= 20) {
                updateSpeedAnzeige(client);
                tickCounter = 0;
            }

            //if(rightClickDurationTicks > 0) {
                //AndromedaBridgingHandler.performRightClick();
                //rightClickDurationTicks--;
            //}
        });
    }

    public static void updateSpeedAnzeige(MinecraftClient mc) {
        PlayerEntity player = mc.player;
        if (player != null) {
            assert mc.world != null;
            long currentTime = mc.world.getTime(); // Verwendung von world.getTime()
            if (lastUpdateTime != 0) {
                Vec3d currentPosition = player.getPos();
                double distance = currentPosition.distanceTo(previousPosition);
                long timeElapsed = currentTime - lastUpdateTime; // Zeit in Ticks

                // Umrechnung von Ticks in Sekunden (1 Tick = 1/20 Sekunde)
                float timeInSeconds = timeElapsed / 20.0f;

                // Geschwindigkeit in Blöcken pro Sekunde
                float speed = (float) (distance / timeInSeconds);
                speedText = String.format("%.2f blocks/s", speed);
            }

            previousPosition = player.getPos();
            lastUpdateTime = currentTime;
        }
    }

    public static String getSpeedText() {
        return speedText;
    }

}