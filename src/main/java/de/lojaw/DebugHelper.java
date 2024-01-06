package de.lojaw;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.event.KeyEvent;

public class DebugHelper {
    public static void debugPlayerEntityMixin(boolean andromedaBridgingEnabled, CallbackInfoReturnable<Boolean> cir) {

        if (BridgingModClient.andromedaBridgingEnabled || BridgingModClient.derpbridgingEnabled) {
            cir.setReturnValue(true);
            cir.cancel(); // Beendet die Methode frühzeitig mit dem angegebenen Rückgabewert
        }
        // Wenn andromedaBridgingEnabled nicht aktiv ist, wird die Originalmethode fortgesetzt
    }

    public static void debugSprintMixin(ClientPlayerEntity player, boolean andromedaBridgingEnabled) {
        //System.out.println("SPRINT MIXIN LOADED");

        if(andromedaBridgingEnabled || BridgingModClient.derpbridgingEnabled) {
            if (player.isOnGround()) {
                //player.jump();
            }

            if (MinecraftClient.getInstance().options.forwardKey.isPressed()) {
                player.setSprinting(true);
            }
        }

        //KeyboardSimulator simulator = new KeyboardSimulator();
        //simulator.pressKey(KeyEvent.VK_W); // Simuliert das Drücken der W-Taste

        // Hier können Sie Breakpoints setzen
    }

}

