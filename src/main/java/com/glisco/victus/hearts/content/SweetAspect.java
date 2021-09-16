package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.player.PlayerEntity;

public class SweetAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("sweet"), 13, 10, SweetAspect::new);

    public SweetAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak() {

    }
}
