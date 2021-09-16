package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.player.PlayerEntity;

public class EvokingAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("evoking"), 12, 10, EvokingAspect::new);

    public EvokingAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak() {

    }
}
