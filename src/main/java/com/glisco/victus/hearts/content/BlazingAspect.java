package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.player.PlayerEntity;

public class BlazingAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("blazing"), 9, 10, BlazingAspect::new);

    public BlazingAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak() {

    }
}
