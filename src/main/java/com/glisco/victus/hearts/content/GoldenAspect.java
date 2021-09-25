package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class GoldenAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("golden"), 16, 10, GoldenAspect::new);

    public GoldenAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public boolean handleBreak(DamageSource source, float damage, float originalHealth) {
        return false;
    }
}
