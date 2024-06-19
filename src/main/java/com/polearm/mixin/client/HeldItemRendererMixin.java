package com.polearm.mixin.client;

import net.minecraft.client.render.item.HeldItemRenderer;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Debug(export = true)
@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @ModifyVariable(method = "updateHeldItems", at = @At("STORE"), ordinal = 0)
    private float clampItemHeight(float orig) {

        return (float) Math.clamp(orig, 0.0, 1.0);

    }

}
