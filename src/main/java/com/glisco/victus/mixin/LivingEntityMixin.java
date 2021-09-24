package com.glisco.victus.mixin;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.content.IcyAspect;
import com.glisco.victus.item.VictusItems;
import com.glisco.victus.util.VictusStatusEffects;
import net.minecraft.block.TransparentBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "dropLoot", at = @At("TAIL"))
    private void dropEmptyAspect(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        if (!causedByPlayer) return;
        var entity = (LivingEntity) (Object) this;

        if (entity instanceof Monster) {
            if (entity.world.random.nextFloat() > 0.5f) return;
            if (!entity.hasStatusEffect(VictusStatusEffects.HEARTBLEED)) return;

            entity.dropItem(VictusItems.BLANK_HEART_ASPECT);
        } else if (entity instanceof VillagerEntity villager) {
            if (villager.getVillagerData().getLevel() < 4) return;

            entity.dropItem(VictusItems.BROKEN_HEART);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @ModifyVariable(method = "travel", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;onGround:Z", ordinal = 2), ordinal = 0)
    private float removeSlipperiness(float t) {
        if (!((Object) this instanceof PlayerEntity)) return t;
        if (!Victus.ASPECTS.get(this).hasActiveAspect(IcyAspect.TYPE)) return t;

        if (!(this.world.getBlockState(this.getVelocityAffectingPos()).getBlock() instanceof TransparentBlock)) return t;

        return .6f;
    }

}
