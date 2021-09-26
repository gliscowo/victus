package com.glisco.victus.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class SlaveRevengeGoal extends RevengeGoal {

    private final Entity owner;

    public SlaveRevengeGoal(PathAwareEntity mob, Entity owner) {
        super(mob);
        this.owner = owner;
    }

    @Override
    public boolean canStart() {
        return this.mob.getAttacker() != owner && super.canStart();
    }
}
