package com.polearm.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.polearm.PlayerEntityAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

@Debug(export = true)
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityAccess {

    @Shadow protected boolean isSubmergedInWater;

    @Shadow public abstract float getAttackCooldownProgress(float baseTime);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "attack",
    at = @At(value = "STORE"),
    ordinal = 2)
    private boolean CritCheck(boolean b, @Local(argsOnly = true) Entity target, @Local(ordinal = 0) boolean bl) {

        return this.polearm$IsCritWorthy() && target instanceof LivingEntity && bl;

    }

    @Unique
    public boolean polearm$IsCritWorthy() {

        float attackCooldown = this.getAttackCooldownProgress(0.5f);
        boolean axeCrit = this.fallDistance > 0.0f && !this.isOnGround() && !this.isClimbing() && !this.isTouchingWater() && !this.hasStatusEffect(StatusEffects.BLINDNESS) && !this.hasVehicle();
        boolean swordCrit = !this.isSubmergedInWater && !this.hasStatusEffect(StatusEffects.BLINDNESS) && attackCooldown > 0.9f && attackCooldown < 1.1f;
        return (axeCrit && this.getMainHandStack().getItem() instanceof AxeItem) || (swordCrit && this.getMainHandStack().getItem() instanceof SwordItem);

    }

    @ModifyArg(method = "getAttackCooldownProgress", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"), index = 2)
    float capCooldownProgress(float value) {

        return 1.2f;

    }

}
