package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.player.PlayerEntity;

public class DraconicAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("draconic"), 11, 10, DraconicAspect::new);

    public DraconicAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak() {

    }
}
