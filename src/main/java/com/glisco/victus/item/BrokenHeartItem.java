package com.glisco.victus.item;

import com.glisco.victus.Victus;
import io.wispforest.owo.itemgroup.OwoItemSettings;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BrokenHeartItem extends EdibleItem {

    public BrokenHeartItem() {
        super(new OwoItemSettings().group(Victus.VICTUS_GROUP).food(new FoodComponent.Builder().alwaysEdible().build()).maxCount(1));
    }

    @Override
    void onEaten(ItemStack stack, World world, PlayerEntity eater) {
        var aspects = Victus.ASPECTS.get(eater);
        while (!aspects.empty()) {
            eater.dropItem(Victus.ASPECTS.get(eater).removeAspect());
        }

        eater.damage(world.getDamageSources().magic(), 15f);
    }
}
