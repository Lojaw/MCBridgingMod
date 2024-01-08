package de.lojaw.mixin;

import de.lojaw.BridgingModClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Unique
    private Vec3d previousPosition = Vec3d.ZERO;
    @Unique
    private long lastUpdateTime = 0;

    @Inject(method = "render", at = @At("TAIL"))
    public void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        long currentTime = System.currentTimeMillis();

        if (player != null && lastUpdateTime != 0) {
            Vec3d currentPosition = player.getPos();
            double distance = currentPosition.distanceTo(previousPosition);
            long timeElapsed = currentTime - lastUpdateTime; // Zeit in Millisekunden

            // Geschwindigkeit in Blöcken pro Sekunde
            float speed = (float) (distance / (timeElapsed / 1000.0));

            // Text vorbereiten und rendern
            MatrixStack matrices = context.getMatrices();
            int screenWidth = context.getScaledWindowWidth();
            int screenHeight = context.getScaledWindowHeight();
            TextRenderer textRenderer = client.textRenderer;
            String speedText = BridgingModClient.getSpeedText();
            float x = (float)(screenWidth / 2 - textRenderer.getWidth(speedText) / 2);
            float y = (float)screenHeight / 2;
            int color = 0xFFFFFF;

            VertexConsumerProvider vertexConsumers = context.getVertexConsumers();
            TextRenderer.TextLayerType layerType = TextRenderer.TextLayerType.NORMAL;
            textRenderer.draw(speedText, x, y, color, false, matrices.peek().getPositionMatrix(), vertexConsumers, layerType, 0x00000000, 15728880);
        }

        previousPosition = player != null ? player.getPos() : Vec3d.ZERO;
        lastUpdateTime = currentTime;
    }
}