package com.glisco.victus.util;

import com.glisco.victus.Victus;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.registry.Registry;

public class VictusStatusEffects {

    public static final VictusStatusEffect HEARTBLEED = new VictusStatusEffect(StatusEffectType.NEUTRAL, 0xAE0000);
    public static final VictusStatusEffect RESURGENCE = new VictusStatusEffect(StatusEffectType.BENEFICIAL, 0xAE7733);
    public static final TrueDamageStatusEffect TRUE_DAMAGE = new TrueDamageStatusEffect();

    public static void register() {
        Registry.register(Registry.STATUS_EFFECT, Victus.id("heartbleed"), HEARTBLEED);
        Registry.register(Registry.STATUS_EFFECT, Victus.id("resurgence"), RESURGENCE);
        Registry.register(Registry.STATUS_EFFECT, Victus.id("true_damage"), TRUE_DAMAGE);
    }

}
