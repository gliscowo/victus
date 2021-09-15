package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.player.PlayerEntity;

public class BundleAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("bundle"), 2, 10, BundleAspect::new);

    public BundleAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak() {
        player.heal(6f);
    }
}
