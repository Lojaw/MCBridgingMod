package de.lojaw.mixin;

import de.lojaw.BridgingModClient;
import de.lojaw.DebugHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "clipAtLedge", at = @At("HEAD"), cancellable = true)
    protected void onClipAtLedge(CallbackInfoReturnable<Boolean> cir) {
        DebugHelper.debugPlayerEntityMixin(BridgingModClient.andromedaBridgingEnabled, cir);
    }

}

