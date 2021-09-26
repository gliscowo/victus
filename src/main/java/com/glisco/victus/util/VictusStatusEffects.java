package com.glisco.victus.util;

import com.glisco.owo.registration.reflect.AutoRegistryContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.registry.Registry;

public class VictusStatusEffects implements AutoRegistryContainer<StatusEffect> {

    public static final HeartbleedStatusEffect HEARTBLEED = new HeartbleedStatusEffect();
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
