package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;

public class CheeseAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("cheese"), 19, 600, 0xffd800, CheeseAspect::new);

    public CheeseAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    protected boolean handleBreak(DamageSource source, float damage, float originalHealth) {
        final var effects = new ArrayList<StatusEffect>();

        player.getActiveStatusEffects().forEach((effect, instance) -> {
            if (effect.getCategory() != StatusEffectCategory.HARMFUL) return;
            effects.add(effect);
        });

        effects.forEach(player::removeStatusEffect);
        return false;
    }
}
