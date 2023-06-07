package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class ArcheryAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("archery"), 14, 40, 0x71413B, ArcheryAspect::new);

    public ArcheryAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public boolean handleBreak(DamageSource source, float damage, float originalHealth) {

        var entities = player.getWorld().getEntitiesByClass(LivingEntity.class, new Box(player.getBlockPos()).expand(3), (p) -> p != player && !(p instanceof TameableEntity tameable && tameable.isOwner(player)));

        for (int i = 0; i < 3; i++) {
            if (entities.size() < 1) return false;
            var entity = entities.remove(player.getWorld().random.nextInt(entities.size()));

            var arrow = new ArrowEntity(EntityType.ARROW, player.getWorld());
            Vec3d arrowVelocity = entity.getPos().subtract(player.getPos()).multiply(.25);
            Vec3d arrowPos = player.getPos().add(arrowVelocity.multiply(.25f)).add(0, player.getEyeHeight(player.getPose()), 0);

            arrow.updatePositionAndAngles(arrowPos.x, arrowPos.y, arrowPos.z, 0, 45);
            arrow.setVelocity(arrowVelocity);

            arrow.setPunch(2);

            player.getWorld().spawnEntity(arrow);
        }

        return false;
    }
}
