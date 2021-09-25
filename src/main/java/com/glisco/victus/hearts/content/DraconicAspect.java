package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.util.VictusAreaEffectCloudExtension;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;

public class DraconicAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("draconic"), 11, 10, DraconicAspect::new);

    public DraconicAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean handleBreak(DamageSource source, float damage, float originalHealth) {
        var areaEffectCloud = new AreaEffectCloudEntity(this.player.world, this.player.getX(), this.player.getY(), this.player.getZ());

        areaEffectCloud.setOwner(this.player);
        ((VictusAreaEffectCloudExtension)areaEffectCloud).markIgnoreOwner();
        areaEffectCloud.setParticleType(ParticleTypes.DRAGON_BREATH);

        areaEffectCloud.setRadius(2f);
        areaEffectCloud.setDuration(150);
        areaEffectCloud.setRadiusGrowth((7f - areaEffectCloud.getRadius()) / areaEffectCloud.getDuration());
        areaEffectCloud.addEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 1));

        player.world.spawnEntity(areaEffectCloud);
        return false;
    }
}
