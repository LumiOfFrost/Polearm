package com.polearm.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.polearm.PlayerEntityAccess;
import com.polearm.SpearItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TridentItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityAccess {

    @Shadow protected boolean isSubmergedInWater;

    @Shadow public abstract float getAttackCooldownProgress(float baseTime);

    @Unique
    boolean wasCritWorthy = false;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At("HEAD"))
    private void setWasCritWorthy(Entity target, CallbackInfo ci) {

        this.wasCritWorthy = this.polearm$IsCritWorthy(target);

    }

    @ModifyVariable(method = "attack",
    at = @At(value = "STORE"),
    ordinal = 2)
    private boolean CritCheck(boolean b, @Local(argsOnly = true) Entity target, @Local(ordinal = 0) boolean bl) {

        return this.wasCritWorthy && target instanceof LivingEntity && bl;

    }

    @Unique
    public boolean polearm$IsCritWorthy(Entity target) {

        Item mainHand = this.getMainHandStack().getItem();
        float attackCooldown = this.getAttackCooldownProgress(0.5f);
        boolean axeCrit = this.fallDistance > 0.0f && !this.isOnGround() && !this.isClimbing() && !this.isTouchingWater() && !this.hasStatusEffect(StatusEffects.BLINDNESS) && !this.hasVehicle();
        boolean swordCrit = !this.isSubmergedInWater && !this.hasStatusEffect(StatusEffects.BLINDNESS) && attackCooldown > 0.95f && attackCooldown < 1.05f && !this.isSprinting();
        boolean spearCrit = !this.hasStatusEffect(StatusEffects.BLINDNESS) && (target != null && this.distanceTo(target) <= 3.5f) && this.isSprinting();
        return (axeCrit && mainHand instanceof AxeItem) ||
                (swordCrit && mainHand instanceof SwordItem) ||
                (spearCrit && (mainHand instanceof SpearItem || mainHand instanceof TridentItem));

    }

    @ModifyArg(method = "getAttackCooldownProgress", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"), index = 2)
    float capCooldownProgress(float value) {

        return 1.2f;

    }

}
