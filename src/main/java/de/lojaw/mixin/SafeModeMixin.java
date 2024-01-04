package de.lojaw.mixin;

import de.lojaw.jni.KeyboardInputHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class SafeModeMixin {
    @ModifyVariable(method = "move", at = @At("HEAD"), ordinal = 0)
    private Vec3d modifyMovement(Vec3d movement) {

        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        BlockPos pos = player.getBlockPos();

        if (isDangerous(pos, player)) {
            // Ändern Sie den Bewegungsvektor, um die Bewegung zu verhindern
            return new Vec3d(0, movement.y, 0); // Beispiel: Verhindert Bewegung in X- und Z-Richtung
        }

        return movement;
    }

    private boolean isDangerous(BlockPos pos, ClientPlayerEntity player) {
        // Überprüfen, ob der Spieler auf dem Boden steht
        if (!player.isOnGround()) {
            return false;
        }

        // Ermitteln der Bewegungsrichtung des Spielers
        Vec3d velocity = player.getVelocity();
        Direction direction = Direction.getFacing(velocity.x, velocity.y, velocity.z);

        // Überprüfen des Blocks in der Bewegungsrichtung
        BlockPos nextBlockPos = pos.offset(direction);
        BlockState nextBlockState = player.getWorld().getBlockState(nextBlockPos);

        // Wenn der nächste Block Luft ist, ist es gefährlich
        return nextBlockState.isAir();
    }

}

