package com.glisco.victus.network;

import com.glisco.victus.Victus;
import io.wispforest.owo.ops.WorldOps;
import io.wispforest.owo.particles.ClientParticles;
import io.wispforest.owo.particles.systems.ParticleSystem;
import io.wispforest.owo.particles.systems.ParticleSystemController;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class VictusParticleEvents {

    private static final ParticleSystemController CONTROLLER = new ParticleSystemController(Victus.id("particles"));

    public static final ParticleSystem<Void> BLAZING_FLAMES = CONTROLLER.register(Void.class, (world, pos, data) -> {
        ClientParticles.setParticleCount(50);
        ClientParticles.randomizeVelocity(.15);
        ClientParticles.spawn(ParticleTypes.FLAME, world, pos, 3);

        WorldOps.playSound(world, new BlockPos(pos), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS);
    });

    public static final ParticleSystem<Boolean> HEART_PARTICLES = CONTROLLER.register(Boolean.class, (world, pos, broken) -> {
        ClientParticles.setVelocity(new Vec3d(0, .1, 0));
        ClientParticles.setParticleCount(20);
        ClientParticles.spawn(broken ? ParticleTypes.ANGRY_VILLAGER : ParticleTypes.HEART, world, pos.add(0, 1, 0), 1.5);

        WorldOps.playSound(world, new BlockPos(pos), broken ? SoundEvents.ENTITY_BLAZE_HURT : SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.PLAYERS, 1, 0);
    });

    public static final ParticleSystem<Void> CONVERT_ASPECT = CONTROLLER.register(Void.class, (world, pos, data) -> {
        ClientParticles.setParticleCount(20);
        ClientParticles.spawn(ParticleTypes.POOF, world, pos, .35);
    });

    public static void initialize() {}

}
