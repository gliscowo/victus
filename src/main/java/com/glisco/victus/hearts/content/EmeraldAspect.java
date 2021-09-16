package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.player.PlayerEntity;

public class EmeraldAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("emerald"), 18, 10, EmeraldAspect::new);

    public EmeraldAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak() {

    }
}
