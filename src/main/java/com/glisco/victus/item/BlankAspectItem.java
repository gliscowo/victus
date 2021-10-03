package com.glisco.victus.item;

import com.glisco.victus.Victus;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ItemScatterer;

class BlankAspectItem extends Item {
    public BlankAspectItem() {super(new Settings().group(Victus.VICTUS_GROUP));}

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        if (!entity.isOnFire()) return;
        entity.world.playSound(null, entity.getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL, 1, 1);
        ItemScatterer.spawn(entity.world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(VictusItems.VOID_HEART_ASPECT));
    }
}
