package com.glisco.victus.mixin;

import com.glisco.victus.item.VictusItems;
import com.glisco.victus.util.VictusStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

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

}
