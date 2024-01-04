package de.lojaw.mixin;

import de.lojaw.jni.KeyboardInputHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class ExampleMixin {

	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(CallbackInfo info) {
		/*
		// This code is injected into the start of MinecraftServer.loadWorld()V
		System.out.println("EXAMPLE MIXIN LOADED");
		System.out.println("JVM Architektur: " + System.getProperty("os.arch"));
		System.out.println("Aktuelles Arbeitsverzeichnis: " + System.getProperty("user.dir"));
		KeyboardInputHandler keyboardHandler = KeyboardInputHandler.getInstance();

		new Thread(() -> {
			try {
				Thread.sleep(10000); // Warte 5 Sekunden
				System.out.println("GESTARTET");
				//keyboardHandler.startAlternatingKeys(); // Starte die Tastenalternierung

				MinecraftClient mc = MinecraftClient.getInstance();
				PlayerEntity player = mc.player;

				if(player != null) {

					//0 oder 360 Grad: Blick nach Norden
					//90 Grad: Blick nach Osten
					//180 Grad: Blick nach Süden
					//270 Grad: Blick nach Westen
					player.setYaw(135); // Setzen Sie den neuen Yaw-Wert (in Grad)

					//0 Grad: Geradeaus auf dem Horizont
					//-90 Grad: Direkt nach oben
					//90 Grad: Direkt nach unten
					player.setPitch(-45); // Setzen Sie den neuen Pitch-Wert (in Grad)

				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		 */
	}
}