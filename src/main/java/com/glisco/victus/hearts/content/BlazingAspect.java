package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.ibm.icu.util.CodePointTrie;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;

public class BlazingAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("blazing"), 9, 10, BlazingAspect::new);

    public BlazingAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak() {
        double speed = 1.5;
        SmallFireballEntity fire1 = new SmallFireballEntity(EntityType.SMALL_FIREBALL, player.world);
        fire1.updatePositionAndAngles(player.getX()+1, player.getY()+1, player.getZ(),0,0);
        player.world.spawnEntity(fire1);
        fire1.setVelocity(1,0,0);

    }
}
