package com.github.timesquared.totemoverlays;

import io.github.timecubed.tulip.TulipConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class MainClient implements ClientModInitializer {
	private static final MinecraftClient mc = MinecraftClient.getInstance();
	public static final TulipConfigManager tulipInstance = new TulipConfigManager("totem-overlays", false);
	
	public static final String red = "red", green = "green", blue = "blue", alpha = "alpha", disabled = "disabled";
	
	@Override
	public void onInitializeClient() {
		tulipInstance.saveProperty(red, 255);
		tulipInstance.saveProperty(green, 0);
		tulipInstance.saveProperty(blue, 0);
		tulipInstance.saveProperty(alpha, 50);
		tulipInstance.saveProperty(disabled, false);
		
		tulipInstance.load(); // load and save
		
		ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("totemoverlays").executes(context -> {
			mc.send(() -> mc.setScreen(new ConfigScreen(Text.of("config"))));
			return 1;
		}))));
		
		MainServer.LOGGER.info("Initialized Totem Overlays successfully!");
	}
	
	public static void tickRender(MatrixStack matrices, float tickDelta) {
		if (mc.player != null) {
			ItemStack offhandStack = mc.player.getOffHandStack();
			ItemStack mainHandStack = mc.player.getMainHandStack();
			
			Item totem = Items.TOTEM_OF_UNDYING;
			
			if (offhandStack.getItem() != totem && mainHandStack.getItem() != totem && !(tulipInstance.getBoolean(disabled))) {
				DrawableHelper.fill(matrices, 0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight(), rgba(tulipInstance.getInt(red), tulipInstance.getInt(green), tulipInstance.getInt(blue), tulipInstance.getInt(alpha)));
			}
		}
	}
	
	private static int rgba(int r, int g, int b, int a) {
		return (a << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255);
	}
}