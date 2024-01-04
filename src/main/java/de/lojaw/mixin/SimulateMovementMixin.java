package de.lojaw.mixin;

import net.minecraft.client.MinecraftClient;

public class SimulateMovementMixin {
    MinecraftClient client = MinecraftClient.getInstance();

    boolean isForwardPressed = client.options.forwardKey.isPressed();
    boolean isBackPressed = client.options.backKey.isPressed();
    boolean isLeftPressed = client.options.leftKey.isPressed();
    boolean isRightPressed = client.options.rightKey.isPressed();

// Verwenden Sie diese Booleans, um zu bestimmen, ob W, A, S oder D gedrückt sind
}
