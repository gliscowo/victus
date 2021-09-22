package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;

public class ArcheryAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("archery"), 14, 10, ArcheryAspect::new);

    public ArcheryAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak() {
        double speed = 0.5;
        int pitch = 45;
       // for (int i = 0; i < 8; i++) {
        //    ArrowEntity arrow = new ArrowEntity(EntityType.ARROW, player.world);


        //}
        ArrowEntity arrow1 = new ArrowEntity(EntityType.ARROW, player.world);
        ArrowEntity arrow2 = new ArrowEntity(EntityType.ARROW, player.world);
        ArrowEntity arrow3 = new ArrowEntity(EntityType.ARROW, player.world);
        ArrowEntity arrow4 = new ArrowEntity(EntityType.ARROW, player.world);
        ArrowEntity arrow5 = new ArrowEntity(EntityType.ARROW, player.world);
        ArrowEntity arrow6 = new ArrowEntity(EntityType.ARROW, player.world);
        ArrowEntity arrow7 = new ArrowEntity(EntityType.ARROW, player.world);
        ArrowEntity arrow8 = new ArrowEntity(EntityType.ARROW, player.world);
        arrow1.updatePositionAndAngles(player.getX()+1, player.getY()+1, player.getZ(),0, pitch);
        player.world.spawnEntity(arrow1);
        arrow1.setVelocity(speed,0,0);
        arrow2.updatePositionAndAngles(player.getX()-1, player.getY()+1, player.getZ(),0, pitch);
        player.world.spawnEntity(arrow2);
        arrow2.setVelocity(-speed,0,0);
        arrow3.updatePositionAndAngles(player.getX(), player.getY()+1, player.getZ()+1,0, pitch);
        player.world.spawnEntity(arrow3);
        arrow3.setVelocity(0,0,speed);
        arrow4.updatePositionAndAngles(player.getX(), player.getY()+1, player.getZ()-1,0, pitch);
        player.world.spawnEntity(arrow4);
        arrow4.setVelocity(0,0,-speed);
        arrow5.updatePositionAndAngles(player.getX()+1, player.getY()+1, player.getZ()+1,0, pitch);
        player.world.spawnEntity(arrow5);
        arrow5.setVelocity(speed,0,speed);
        arrow6.updatePositionAndAngles(player.getX()-1, player.getY()+1, player.getZ()+1,0, pitch);
        player.world.spawnEntity(arrow6);
        arrow6.setVelocity(-speed,0,speed);
        arrow7.updatePositionAndAngles(player.getX()+1, player.getY()+1, player.getZ()-1,0, pitch);
        player.world.spawnEntity(arrow7);
        arrow7.setVelocity(speed,0,-speed);
        arrow8.updatePositionAndAngles(player.getX()-1, player.getY()+1, player.getZ()-1,0, pitch);
        player.world.spawnEntity(arrow8);
        arrow8.setVelocity(-speed,0,-speed);
    }
}
