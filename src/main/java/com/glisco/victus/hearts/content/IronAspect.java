package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class IronAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("iron"), 17, 300, IronAspect::new);
    public static final int NO_DROPS_FLAG = 0x1;

    public IronAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public boolean handleBreak(DamageSource source, float damage, float originalHealth) {
        var golem = EntityType.IRON_GOLEM.create(player.world);

        Victus.ENTITY_FLAGS.get(golem).setFlag(NO_DROPS_FLAG);
        golem.updatePositionAndAngles(player.getX(), player.getY(), player.getZ(), 0, 0);

        player.world.spawnEntity(golem);
        return false;
    }
}
