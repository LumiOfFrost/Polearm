package com.polearm.mixin.client;

import com.polearm.PlayerEntityAccess;
import com.polearm.PolearmMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Debug(export = true)
@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Shadow @Final private MinecraftClient client;
    @Unique
    private static final Identifier CROSSHAIR_ATTACK_INDICATOR_FULL_CRIT_TEXTURE = Identifier.of(PolearmMain.MOD_ID, "hud/crosshair_attack_indicator_full_crit");
    @Unique
    private static final Identifier CROSSHAIR_ATTACK_INDICATOR_PROGRESS_CRIT_TEXTURE = Identifier.of(PolearmMain.MOD_ID, "hud/crosshair_attack_indicator_progress_crit");
    @Unique
    private static final Identifier HOTBAR_ATTACK_INDICATOR_PROGRESS_CRIT_TEXTURE = Identifier.of(PolearmMain.MOD_ID, "hud/hotbar_attack_indicator_progress_crit");

    @ModifyArg(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1), index = 0)
    private Identifier crosshairAttackIndicatorFullCritMixin(Identifier texture) {

        assert this.client.player != null;
        return ((PlayerEntityAccess)this.client.player).polearm$IsCritWorthy(this.client.targetedEntity) ? CROSSHAIR_ATTACK_INDICATOR_FULL_CRIT_TEXTURE : texture;

    }
    @ModifyArg(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIIIIII)V"), index = 0)
    private Identifier crosshairAttackIndicatorProgressCritMixin(Identifier texture) {

        assert this.client.player != null;
        return ((PlayerEntityAccess)this.client.player).polearm$IsCritWorthy(this.client.targetedEntity) ? CROSSHAIR_ATTACK_INDICATOR_PROGRESS_CRIT_TEXTURE : texture;

    }
    @ModifyArg(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIIIIII)V"), index = 0)
    private Identifier hotbarAttackIndicatorCritMixin(Identifier texture) {

        assert this.client.player != null;
        return ((PlayerEntityAccess)this.client.player).polearm$IsCritWorthy(this.client.targetedEntity) ? HOTBAR_ATTACK_INDICATOR_PROGRESS_CRIT_TEXTURE : texture;

    }

}
