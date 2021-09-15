package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.item.VictusItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

public class TotemAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("totem"), 0, 60, TotemAspect::new);

    public TotemAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void handleBreak() {
        player.setHealth(player.getHealth() + 15);
        player.world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_TOTEM_USE, player.getSoundCategory(), 1.0F, 1.0F);
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected void handleBreakClient() {
        MinecraftClient.getInstance().gameRenderer.showFloatingItem(new ItemStack(VictusItems.TOTEM_HEART_ASPECT));
        MinecraftClient.getInstance().particleManager.addEmitter(player, ParticleTypes.TOTEM_OF_UNDYING, 30);
    }
}
