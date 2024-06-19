package com.polearm;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class ModelPlugin implements ModelLoadingPlugin {

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        pluginContext.addModels(Identifier.of(PolearmMain.MOD_ID, "item/wooden_spear_in_hand"));
        pluginContext.addModels(Identifier.of(PolearmMain.MOD_ID, "item/stone_spear_in_hand"));
        pluginContext.addModels(Identifier.of(PolearmMain.MOD_ID, "item/iron_spear_in_hand"));
        pluginContext.addModels(Identifier.of(PolearmMain.MOD_ID, "item/golden_spear_in_hand"));
        pluginContext.addModels(Identifier.of(PolearmMain.MOD_ID, "item/diamond_spear_in_hand"));
        pluginContext.addModels(Identifier.of(PolearmMain.MOD_ID, "item/netherite_spear_in_hand"));
    }
}
