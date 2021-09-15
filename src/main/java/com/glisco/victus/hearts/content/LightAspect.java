package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.player.PlayerEntity;

public class LightAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("light"), 5, 10, LightAspect::new);

    public LightAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak() {

    }
}
