package de.lojaw;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class DebugHelper {
    public static void debugPlayerEntityMixin(boolean andromedaBridgingEnabled, CallbackInfoReturnable<Boolean> cir) {

        if (BridgingModClient.andromedaBridgingEnabled) {
            cir.setReturnValue(true);
            cir.cancel(); // Beendet die Methode frühzeitig mit dem angegebenen Rückgabewert
        }
        // Wenn andromedaBridgingEnabled nicht aktiv ist, wird die Originalmethode fortgesetzt
    }
}

