package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.item.VictusItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

public class TotemAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("totem"), 0, 3000, 0xFFD16D, TotemAspect::new);

    private boolean hadTotem = false;

    public TotemAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public boolean handleBreak(DamageSource source, float damage, float originalHealth) {
        final var inventory = player.getInventory();

        if (inventory.contains(new ItemStack(Items.TOTEM_OF_UNDYING))) {
            player.setHealth(player.getHealth() + 15);
            inventory.remove(stack -> stack.isOf(Items.TOTEM_OF_UNDYING), 1, player.playerScreenHandler.getCraftingInput());

            this.hadTotem = true;
        } else {
            player.setHealth(player.getHealth() + 5);

            this.hadTotem = false;
        }

        player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_TOTEM_USE, player.getSoundCategory(), 1.0F, 1.0F);

        Victus.ASPECTS.sync(player);
        return true;
    }

    @Override
    protected int getRechargeDuration() {
        return hadTotem ? 200 : getType().standardRechargeDuration();
    }

    @Override
    protected void readCustomData(NbtCompound nbt) {
        this.hadTotem = nbt.getBoolean("HadTotem");
    }

    @Override
    protected void writeCustomData(NbtCompound nbt) {
        nbt.putBoolean("HadTotem", hadTotem);
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected void handleBreakClient() {
        MinecraftClient.getInstance().gameRenderer.showFloatingItem(new ItemStack(VictusItems.TOTEM_HEART_ASPECT));
        MinecraftClient.getInstance().particleManager.addEmitter(player, ParticleTypes.TOTEM_OF_UNDYING, 30);
    }
}
