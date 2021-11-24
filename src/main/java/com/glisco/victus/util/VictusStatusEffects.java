package com.glisco.victus.util;

import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.registry.Registry;

public class VictusStatusEffects implements AutoRegistryContainer<StatusEffect> {

    public static final VictusStatusEffect HEARTBLEED = new VictusStatusEffect(StatusEffectCategory.NEUTRAL, 0xAE0000);
    public static final VictusStatusEffect RESURGENCE = new VictusStatusEffect(StatusEffectCategory.BENEFICIAL, 0xAE7733);
    public static final TrueDamageStatusEffect TRUE_DAMAGE = new TrueDamageStatusEffect();

    @Override
    public Registry<StatusEffect> getRegistry() {
        return Registry.STATUS_EFFECT;
    }

    @Override
    public Class<StatusEffect> getTargetFieldType() {
        return StatusEffect.class;
    }
}
