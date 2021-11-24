package com.glisco.victus.hearts.content;

import io.wispforest.owo.util.VectorRandomUtils;
import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.util.VictusVexExtension;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorldAccess;

public class EvokingAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("evoking"), 12, 100, 0x48545F, EvokingAspect::new);

    public EvokingAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean handleBreak(DamageSource source, float damage, float originalHealth) {

        for (int i = 0; i < 3; i++) {
            var vex = new VexEntity(EntityType.VEX, player.world);
            ((VictusVexExtension) vex).replaceTargetGoal(player);

            Vec3d vexPos = VectorRandomUtils.getRandomOffsetSpecific(player.world, player.getPos().add(0, 2, 0), 2, 1, 2);
            vex.updatePositionAndAngles(vexPos.x, vexPos.y, vexPos.z, 0, 0);

            vex.initialize(((ServerWorldAccess) player.world), player.world.getLocalDifficulty(new BlockPos(vexPos)), SpawnReason.MOB_SUMMONED, null, null);
            vex.setLifeTicks(250);

            player.world.spawnEntity(vex);
            vex.playSpawnEffects();
        }

        player.world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.PLAYERS, 1, 1);

        return false;
    }
}
