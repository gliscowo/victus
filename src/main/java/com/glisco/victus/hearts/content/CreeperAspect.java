package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.util.SuicideDamageSource;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.minecraft.world.explosion.Explosion;

public class CreeperAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("creeper"), 6, 10, CreeperAspect::new);

    public CreeperAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak(DamageSource source, float damage, float originalHealth) {
        player.getServer().execute(() -> {
            player.world.createExplosion(null, SuicideDamageSource.create(), new EntityExplosionBehavior(player), player.getX(), player.getY(), player.getZ(), 3f, false, Explosion.DestructionType.NONE);
        });
    }

}
