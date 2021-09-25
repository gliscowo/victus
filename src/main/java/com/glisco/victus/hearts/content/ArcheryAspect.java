package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.Vec3d;

public class ArcheryAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("archery"), 14, 10, ArcheryAspect::new);

    public ArcheryAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public boolean handleBreak(DamageSource source, float damage, float originalHealth) {
        for (int i = 0; i < 9; i++) {
            if (i == 4) continue;

            var arrow = new ArrowEntity(EntityType.ARROW, player.world);
            Vec3d arrowPos = player.getPos().add(1, 1, 1).subtract(i % 3, 0, i / 3);
            Vec3d arrowVelocity = arrowPos.subtract(player.getPos().add(0, 1, 0)).multiply(.75);

            arrow.updatePositionAndAngles(arrowPos.x, arrowPos.y, arrowPos.z, 0, 45);
            arrow.setVelocity(arrowVelocity);

            player.world.spawnEntity(arrow);
        }
        return false;
    }
}
