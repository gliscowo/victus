package com.glisco.victus.network;

import io.wispforest.owo.ops.WorldOps;
import io.wispforest.owo.particles.ClientParticles;
import io.wispforest.owo.particles.ServerParticles;
import com.glisco.victus.Victus;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.ItemEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class VictusParticleEvents {

    public static final Identifier BLAZING_FLAMES = Victus.id("blazing_flames");
    public static final Identifier HEART_PARTICLES = Victus.id("void_heart_particles");
    public static final Identifier CONVERT_ASPECT = Victus.id("convert_aspect");

    @Environment(EnvType.CLIENT)
    public static class Client {

        public static void registerClientListeners() {
            ServerParticles.registerClientSideHandler(BLAZING_FLAMES, (client, pos, data) -> {
                client.execute(() -> {
                    ClientParticles.setParticleCount(50);
                    ClientParticles.randomizeVelocity(.15);
                    ClientParticles.spawn(ParticleTypes.FLAME, client.world, pos, 3);

                    WorldOps.playSound(client.world, new BlockPos(pos), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS);
                });
            });

            ServerParticles.registerClientSideHandler(HEART_PARTICLES, (client, pos, data) -> {
                var broken = data.readBoolean();
                client.execute(() -> {
                    ClientParticles.setVelocity(new Vec3d(0, .1, 0));
                    ClientParticles.setParticleCount(20);
                    ClientParticles.spawn(broken ? ParticleTypes.ANGRY_VILLAGER : ParticleTypes.HEART, client.world, pos.add(0, 1, 0), 1.5);

                    WorldOps.playSound(client.world, new BlockPos(pos), broken ? SoundEvents.ENTITY_BLAZE_HURT : SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.PLAYERS, 1, 0);
                });
            });

            ServerParticles.registerClientSideHandler(CONVERT_ASPECT, (client, pos, data) -> {
                client.execute(() -> {
                    ClientParticles.setParticleCount(20);
                    ClientParticles.spawn(ParticleTypes.POOF, client.world, pos, .35);
                });
            });
        }
    }

    public static void dispatchHeartParticles(ServerWorld world, ServerPlayerEntity player, boolean broken) {
        ServerParticles.issueEvent(world, player.getPos(), HEART_PARTICLES, byteBuf -> {
            byteBuf.writeBoolean(broken);
        });
    }

    public static void dispatchPoofParticles(ServerWorld world, ItemEntity item) {
        ServerParticles.issueEvent(world, item.getPos(), CONVERT_ASPECT);
    }

}
