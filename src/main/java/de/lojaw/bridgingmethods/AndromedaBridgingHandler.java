// AndromedaBridgingHandler.java
package de.lojaw.bridgingmethods;

import de.lojaw.BridgingModClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.minecraft.util.math.BlockPos;

import static de.lojaw.BridgingModClient.simulateKeyPressWithRobot;
import static de.lojaw.BridgingModClient.simulateMouseClickWithRobot;

public class AndromedaBridgingHandler {
    private static final int BRIDGING_DISTANCE = 5; // Abstand zwischen den zu platzierenden Blöcken

    public static void executeAndromedaBridging(MinecraftClient client) throws InterruptedException {
        initAndromedaBridging(client);
        //for (int i = 0; i < BRIDGING_DISTANCE; i++) {
            //placeBlockBelowPlayer(client);
            //placeBlockAbovePlayer(client);
        //}
    }

    private static void initAndromedaBridging(MinecraftClient client) throws InterruptedException {
        ClientPlayerEntity player = client.player;
        if (player != null) {


            // Richte den Spieler so aus, dass er nach unten auf den Block schaut
            //player.setPitch(90.0F);
            //player.setSprinting(true);
            //simulateKeyPressWithRobot('w');

            // Platzieren Sie einen Block unterhalb des Spielers, falls keiner vorhanden ist
            //BlockPos blockBelow = player.getBlockPos().down();
            //if (client.world.getBlockState(blockBelow).isAir()) {
                //simulateMouseClickWithRobot(BridgingModClient.MouseButtonType.RIGHT_CLICK);
            //}

            // Platziere einen Block überhalb des Spielers, falls keiner vorhanden ist
            //BlockPos blockAbove = player.getBlockPos().up();
            //if (client.world.getBlockState(blockAbove).isAir()) {
                //simulateMouseClickWithRobot(BridgingModClient.MouseButtonType.RIGHT_CLICK);
            //}

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            // Der Code, der nach 5 Sekunden ausgeführt werden soll
            // Zum Beispiel das Verlassen der Methode oder das Ausführen weiterer Aktionen
            // Schließen Sie den Executor nach der Ausführung
            executor.schedule(executor::shutdown, 5, TimeUnit.SECONDS);

        }
    }

    private static void placeBlockBelowPlayer(MinecraftClient client) {
        simulateMouseClickWithRobot(BridgingModClient.MouseButtonType.RIGHT_CLICK);
    }

    private static void placeBlockAbovePlayer(MinecraftClient client) {
        simulateMouseClickWithRobot(BridgingModClient.MouseButtonType.RIGHT_CLICK);
    }
}
