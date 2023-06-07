package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.util.VictusStatusEffects;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;

public class DraconicAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("draconic"), 11, 100, 0xFF55FF, DraconicAspect::new);
    public static final int IGNORE_OWNER_FLAG = 0x1;

    public DraconicAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean handleBreak(DamageSource source, float damage, float originalHealth) {
        var areaEffectCloud = new AreaEffectCloudEntity(this.player.getWorld(), this.player.getX(), this.player.getY(), this.player.getZ());

        Victus.ENTITY_FLAGS.get(areaEffectCloud).setFlag(IGNORE_OWNER_FLAG);

        areaEffectCloud.setOwner(this.player);
        areaEffectCloud.setParticleType(ParticleTypes.DRAGON_BREATH);

        areaEffectCloud.setRadius(2f);
        areaEffectCloud.setDuration(150);
        areaEffectCloud.setRadiusGrowth((7f - areaEffectCloud.getRadius()) / areaEffectCloud.getDuration());
        areaEffectCloud.addEffect(new StatusEffectInstance(VictusStatusEffects.TRUE_DAMAGE, 1, 0));

        player.getWorld().spawnEntity(areaEffectCloud);
        return false;
    }
}
