package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.hearts.HeartAspectComponent;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class GoldenAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("golden"), 16, 100, 0xFFF77B, GoldenAspect::new);

    public GoldenAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public boolean handleBreak(DamageSource source, float damage, float originalHealth) {
        final HeartAspectComponent aspects = Victus.ASPECTS.get(player);
        int index = findIndex(aspects);

        float percentage = 1f - ((index + 0f) / (player.getMaxHealth() / 2));
        int level = Math.max(0, Math.round(percentage * 5) - 1);

        player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 600, level));

        return false;
    }

    private int findIndex(HeartAspectComponent component) {
        for (int i = 0; i < component.effectiveSize(); i++) {
            if (component.getAspect(i) == this) return i;
        }
        return -1;
    }
}
