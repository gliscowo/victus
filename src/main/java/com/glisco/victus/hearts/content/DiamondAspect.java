package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.player.PlayerEntity;

public class DiamondAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("diamond"), 1, 10, DiamondAspect::new);

    public DiamondAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak() {

    }
}
