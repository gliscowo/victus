package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import oshi.util.platform.unix.openbsd.FstatUtil;

public class OceanAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("ocean"), 4, 10, HeartAspect.belowHealthPercentage(.75f), OceanAspect::new);

    public OceanAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void update() {
        if (player.isSubmergedInWater()) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 5, 0, true, true, true));
            if (player.getHealth() <= player.getMaxHealth() * 0.25) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 5, 0, true, true, true));
            }
        }
    }


    @Override
    public void handleBreak() {
        player.removeStatusEffect(StatusEffects.CONDUIT_POWER);
        player.removeStatusEffect(StatusEffects.DOLPHINS_GRACE);
    }
}
