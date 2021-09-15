package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.player.PlayerEntity;

public class OceanAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("ocean"), 4, 10, OceanAspect::new);

    public OceanAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak() {

    }
}
