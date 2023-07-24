package com.github.timesquared.totemoverlays.mixin.client;

import com.github.timesquared.totemoverlays.MainClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHUD {
	@Inject(at = @At("HEAD"), method = "render")
	public void onTick(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
		MainClient.tickRender(matrices, tickDelta);
	}
}