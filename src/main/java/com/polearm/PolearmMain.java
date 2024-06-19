package com.polearm;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolearmMain implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("polearm");
	public static final String MOD_ID = "polearm";

	public static final Item WOODEN_SPEAR = new SpearItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(SpearItem.createAttributeModifiers(ToolMaterials.WOOD, 4, -2.8f, 1.5f)));
	public static final Item STONE_SPEAR = new SpearItem(ToolMaterials.STONE, new Item.Settings().attributeModifiers(SpearItem.createAttributeModifiers(ToolMaterials.STONE, 4, -2.8f, 1.5f)));
	public static final Item IRON_SPEAR = new SpearItem(ToolMaterials.IRON, new Item.Settings().attributeModifiers(SpearItem.createAttributeModifiers(ToolMaterials.IRON, 4, -2.8f, 1.5f)));
	public static final Item GOLDEN_SPEAR = new SpearItem(ToolMaterials.GOLD, new Item.Settings().attributeModifiers(SpearItem.createAttributeModifiers(ToolMaterials.GOLD, 4, -2.8f, 1.5f)));
	public static final Item DIAMOND_SPEAR = new SpearItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(SpearItem.createAttributeModifiers(ToolMaterials.DIAMOND, 4, -2.8f, 1.5f)));
	public static final Item NETHERITE_SPEAR = new SpearItem(ToolMaterials.NETHERITE, new Item.Settings().fireproof().attributeModifiers(SpearItem.createAttributeModifiers(ToolMaterials.NETHERITE, 4, -2.8f, 2.25f)));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Loading Polearm!");

		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "wooden_spear"), WOODEN_SPEAR);
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "stone_spear"), STONE_SPEAR);
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "iron_spear"), IRON_SPEAR);
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "golden_spear"), GOLDEN_SPEAR);
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "diamond_spear"), DIAMOND_SPEAR);
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "netherite_spear"), NETHERITE_SPEAR);

		FuelRegistry.INSTANCE.add(WOODEN_SPEAR, 250);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {

			content.addAfter(Items.NETHERITE_SWORD, WOODEN_SPEAR);
			content.addAfter(WOODEN_SPEAR, STONE_SPEAR);
			content.addAfter(STONE_SPEAR, IRON_SPEAR);
			content.addAfter(IRON_SPEAR, GOLDEN_SPEAR);
			content.addAfter(GOLDEN_SPEAR, DIAMOND_SPEAR);
			content.addAfter(DIAMOND_SPEAR, NETHERITE_SPEAR);

		});

		LOGGER.info("Loaded Polearm!");
	}
}