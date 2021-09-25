package com.glisco.victus.network;

import com.glisco.owo.particles.ClientParticles;
import com.glisco.owo.particles.ServerParticles;
import com.glisco.owo.util.VectorRandomUtils;
import com.glisco.owo.util.VectorSerializer;
import com.glisco.victus.Victus;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class VictusParticleEvents {

    public static final Identifier BLAZING_FLAMES = Victus.id("blazing_flames");

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
    }

}
