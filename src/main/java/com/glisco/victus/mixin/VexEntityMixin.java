package com.glisco.victus.mixin;

import com.glisco.victus.util.SlaveRevengeGoal;
import com.glisco.victus.util.VictusVexExtension;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VexEntity.class)
public abstract class VexEntityMixin extends MobEntity implements VictusVexExtension {

    protected VexEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void replaceTargetGoal(PlayerEntity owner) {
        ((GoalSelectorAccessor) targetSelector).getGoals().removeIf(prioritizedGoal -> prioritizedGoal.getGoal() instanceof ActiveTargetGoal || prioritizedGoal.getGoal() instanceof RevengeGoal);
        this.targetSelector.add(1, new SlaveRevengeGoal((VexEntity) (Object) this, owner));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> livingEntity != owner && !(livingEntity instanceof VexEntity)));
    }
}
