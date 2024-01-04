package de.lojaw;

import de.lojaw.bridgingmethods.AndromedaBridgingHandler;
import de.lojaw.jni.KeyboardInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class BridgingModClient implements ClientModInitializer {
    private static boolean autoPlaceEnabled = false;

    public static boolean andromedaBridgingEnabled = true;
    private static KeyboardInputHandler keyboardHandler;


    public enum MouseButtonType {
        LEFT_CLICK,
        RIGHT_CLICK
    }

    @Override
    public void onInitializeClient() {
        System.setProperty("java.awt.headless", "false");
        keyboardHandler = KeyboardInputHandler.getInstance();

        ClientSendMessageEvents.ALLOW_CHAT.register((message) -> {
            String[] args = message.split(" ");
            ClientPlayerEntity player = MinecraftClient.getInstance().player;

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

                            // Führe die Aktion aus
                            activateAutoPlace(mouseButtonType, clicksPerSecond, timeInSeconds);
                            return false;
                        } else {
                            // Wenn nicht genug Parameter vorhanden sind, informiere den Spieler
                            if (player != null) {
                                player.sendMessage(Text.of("[YourMod] Der Befehl 'click' erfordert drei Parameter: <mouseButtonType> <clicksPerSecond> <timeInSeconds>."), false);
                            }
                            return false;
                        }

                    case "andromeda":
                        andromedaBridgingEnabled = true;
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
            if (keyboardHandler != null) {
                keyboardHandler.updateOnTick();
            }

            //if (autoPlaceEnabled) {
                //autoPlaceBlocks(client);
            //}
            //if (andromedaBridgingEnabled) {
                //try {
                    //AndromedaBridgingHandler.executeAndromedaBridging(client);
                //} catch (InterruptedException e) {
                    //throw new RuntimeException(e);
                //}
                //andromedaBridgingEnabled = false;
            //}
        });
    }

    private void simulateMouseClick(MouseButtonType mouseButtonType, int clicksPerSecond, int timeInSeconds) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Robot robot;
                try {
                    robot = new Robot();

                    // Bestimme den Maustyp basierend auf dem Parameter mouseButtonType
                    int mouseButton = (mouseButtonType == MouseButtonType.LEFT_CLICK) ? InputEvent.BUTTON1_DOWN_MASK : InputEvent.BUTTON3_DOWN_MASK;

                    // Simuliere den Mausklick basierend auf den Parametern mouseButtonType
                    robot.mousePress(mouseButton);
                    robot.mouseRelease(mouseButton);
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }
        };

        // Berechne das Intervall zwischen den Klicks basierend auf clicksPerSecond
        long delay = Math.round(1000.0 / clicksPerSecond);

        // Führe die Timer-Aufgabe in einem festgelegten Intervall aus
        timer.schedule(task, 0, delay);

        // Beende die Timer-Aufgabe nach der angegebenen Zeit (timeInSeconds)
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
            }
        }, timeInSeconds * 1000L);
    }

    private void activateAutoPlace(MouseButtonType mouseButtonType, int clicksPerSecond, int timeInSeconds) {
        autoPlaceEnabled = true;
        simulateMouseClick(mouseButtonType, clicksPerSecond, timeInSeconds);
    }

    private void autoPlaceBlocks(MinecraftClient client) {
        // Implementiere die Logik für die automatische Blockplatzierung
        // ...
        autoPlaceEnabled = false;
    }

    public static void simulateMouseClickWithRobot(MouseButtonType mouseButtonType) {
        Robot robot;
        try {
            robot = new Robot();

            // Bestimme den Maustyp basierend auf dem Parameter mouseButtonType
            int mouseButton = (mouseButtonType == MouseButtonType.LEFT_CLICK) ? InputEvent.BUTTON1_DOWN_MASK : InputEvent.BUTTON3_DOWN_MASK;

            // Simuliere den Mausklick basierend auf den Parametern mouseButtonType
            robot.mousePress(mouseButton);
            robot.mouseRelease(mouseButton);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void simulateKeyPressWithRobot(char key) {
        Robot robot;
        try {
            robot = new Robot();

            // Konvertiere den Zeichen in den entsprechenden KeyEvent-Code
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(key);

            // Simuliere das Drücken der Taste
            robot.keyPress(keyCode);
            Thread.sleep(20);
            // Simuliere das Loslassen der Taste
            robot.keyRelease(keyCode);

            // Simuliere das Drücken der Taste
            robot.keyPress(keyCode);
            Thread.sleep(20);
            // Simuliere das Loslassen der Taste
            robot.keyRelease(keyCode);

            // Simuliere das Drücken der Taste
            robot.keyPress(keyCode);
            Thread.sleep(20);
            // Simuliere das Loslassen der Taste
            robot.keyRelease(keyCode);

        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}