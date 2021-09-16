package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.player.PlayerEntity;

public class ArcheryAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("archery"), 14, 10, ArcheryAspect::new);

    public ArcheryAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak() {

    }
}
