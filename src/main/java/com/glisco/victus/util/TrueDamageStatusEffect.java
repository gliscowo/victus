package com.glisco.victus.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class TrueDamageStatusEffect extends StatusEffect {

    public TrueDamageStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 4393481);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(DamageSource.MAGIC, 6 << amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration >= 1;
    }

    @Override
    public boolean isInstant() {
        return true;
    }
}
