package com.glisco.victus.mixin;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.hearts.content.IronAspect;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageTracker;onDamage(Lnet/minecraft/entity/damage/DamageSource;FF)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private void onTakeDamage(DamageSource source, float amount, CallbackInfo ci, float originalHealth) {
        float health = originalHealth - amount;
        int affectedAspect = Math.max(0, (int) Math.ceil((health + 1) / 2d) - 1);

        Victus.ASPECTS.get(this).damageAspect(affectedAspect, source, amount, originalHealth);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onEndTick(CallbackInfo ci) {
        Victus.ASPECTS.get(this).tick();

        if (this.getHealth() >= this.getMaxHealth() * .35f) return;
        if (!Victus.ASPECTS.get(this).hasAspect(IronAspect.TYPE, HeartAspect.IS_NOT_ACTIVE)) return;

        List<IronGolemEntity> golems = this.world.getEntitiesByClass(IronGolemEntity.class, new Box(this.getBlockPos()).expand(10), Entity::isAlive);
        golems.forEach(ironGolemEntity -> ironGolemEntity.setTarget(this));
    }

}
