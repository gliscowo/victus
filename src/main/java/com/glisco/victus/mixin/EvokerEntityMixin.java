package com.glisco.victus.mixin;

import com.glisco.victus.util.WohooHeartGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EvokerEntity.class)
public abstract class EvokerEntityMixin extends MobEntity {

    protected EvokerEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    private void injectAspectGoal(CallbackInfo ci) {
        final WohooHeartGoal wohooGoal = new WohooHeartGoal((EvokerEntity) (Object) this);
        this.goalSelector.add(2, wohooGoal);
        this.targetSelector.add(1, wohooGoal.lookGoal);
    }

}
