package com.polearm.client;

import com.polearm.ModelPlugin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import static com.polearm.PolearmMain.WOODEN_SPEAR;

public class PolearmClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		ModelLoadingPlugin.register(new ModelPlugin());

	}
}