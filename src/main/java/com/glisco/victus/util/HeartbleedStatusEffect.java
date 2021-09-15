package com.glisco.victus.util;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class HeartbleedStatusEffect extends StatusEffect {

    public HeartbleedStatusEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xAE0000);
    }
}
