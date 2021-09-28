package com.glisco.victus.network;

import com.glisco.owo.particles.ClientParticles;
import com.glisco.owo.particles.ServerParticles;
import com.glisco.owo.util.VectorRandomUtils;
import com.glisco.owo.util.VectorSerializer;
import com.glisco.victus.Victus;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class VictusParticleEvents {

    public static final Identifier BLAZING_FLAMES = Victus.id("blazing_flames");
    public static final Identifier HEART_PARTICLES = Victus.id("void_heart_particles");

    public static void registerClientListeners() {
        ServerParticles.registerClientSideHandler(BLAZING_FLAMES, (client, pos, data) -> {
            Vec3d playerPos = VectorSerializer.get(data.readNbt(), "PlayerPos").add(0, 1, 0);
            client.execute(() -> {
                for (int i = 0; i < 10; i++) {
                    ClientParticles.setParticleCount(5);
                    ClientParticles.setVelocity(VectorRandomUtils.getRandomOffset(client.world, Vec3d.ZERO, .15));
                    ClientParticles.spawn(ParticleTypes.FLAME, client.world, playerPos, 3);
                }

                client.world.playSound(pos, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1, 1, true);
            });
        });

        ServerParticles.registerClientSideHandler(HEART_PARTICLES, (client, pos, data) -> {
            var entityId = data.readVarInt();
            var broken = data.readBoolean();
            client.execute(() -> {
                ClientParticles.setVelocity(new Vec3d(0, .1, 0));
                ClientParticles.setParticleCount(20);
                ClientParticles.spawn(broken ? ParticleTypes.ANGRY_VILLAGER : ParticleTypes.HEART, client.world, client.world.getEntityById(entityId).getPos().add(0, 1, 0), 1.5);

                client.world.playSound(pos, broken ? SoundEvents.ENTITY_BLAZE_HURT : SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.PLAYERS, 1, 0, true);
            });
        });
    }

    public static void dispatchHeartParticles(ServerWorld world, ServerPlayerEntity player, boolean broken) {
        ServerParticles.issueEvent(world, player.getBlockPos(), HEART_PARTICLES, byteBuf -> {
            byteBuf.writeVarInt(player.getId());
            byteBuf.writeBoolean(broken);
        });
    }

}
