package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class IcyAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("icy"), 10, 10, IcyAspect::new);

    public IcyAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak(DamageSource source, float damage, float originalHealth) {

    }
}
