package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.explosion.EntityExplosionBehavior;

public class CreeperAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("creeper"), 6, 200, 0x53BC5E, CreeperAspect::new);
    private static final RegistryKey<DamageType> SUICIDE_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Victus.id("suicide"));

    public CreeperAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public boolean handleBreak(DamageSource source, float damage, float originalHealth) {
        player.getServer().execute(() -> {
            player.world.createExplosion(
                    null,
                    new DamageSource(player.world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getEntry(SUICIDE_DAMAGE_TYPE).get()),
                    new EntityExplosionBehavior(player),
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    3f,
                    false,
                    World.ExplosionSourceType.NONE
            );
        });
        return false;
    }

}
