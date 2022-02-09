package com.glisco.victus.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class EdibleItem extends Item {

    public EdibleItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient && user instanceof PlayerEntity player) onEaten(stack, world, player);
        return super.finishUsing(stack, world, user);
    }

    abstract void onEaten(ItemStack stack, World world, PlayerEntity eater);

}
