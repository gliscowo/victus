package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.item.VictusItems;
import io.wispforest.owo.particles.ClientParticles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class LightAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("light"), 5, 1200, 0xFFFFFF, LightAspect::new);

    public LightAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public boolean handleBreak(DamageSource source, float damage, float originalHealth) {
        if (!source.isIn(DamageTypeTags.IS_FALL)) return false;

        this.player.setHealth(originalHealth);
        player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_TOTEM_USE, player.getSoundCategory(), 1.0F, 2.0F);
        return true;
    }

    @Override
    protected void handleBreakClient() {
        MinecraftClient.getInstance().gameRenderer.showFloatingItem(new ItemStack(VictusItems.LIGHT_HEART_ASPECT));

        ClientParticles.setParticleCount(40);
        ClientParticles.setVelocity(new Vec3d(0, .1, 0));
        ClientParticles.spawn(ParticleTypes.POOF, player.getWorld(), player.getPos().add(0, 1, 0), 3);

        MinecraftClient.getInstance().particleManager.addEmitter(player, ParticleTypes.POOF, 10);
    }
}
