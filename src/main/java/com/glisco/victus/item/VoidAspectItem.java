package com.glisco.victus.item;

import com.glisco.victus.Victus;
import com.glisco.victus.network.VictusParticleEvents;
import io.wispforest.owo.itemgroup.OwoItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class VoidAspectItem extends EdibleItem {

    public VoidAspectItem() {
        super(new OwoItemSettings().group(Victus.VICTUS_GROUP).fireproof().food(new FoodComponent.Builder().alwaysEdible().build()).maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (Victus.ASPECTS.get(user).empty()) return TypedActionResult.pass(user.getStackInHand(hand));
        return super.use(world, user, hand);
    }

    @Override
    public Text getName(ItemStack stack) {
        return VictusItems.coloredTranslationWithPrefix(getTranslationKey(stack), 0xff2d2d);
    }

    @Override
    void onEaten(ItemStack stack, World world, PlayerEntity eater) {
        Victus.ASPECTS.get(eater).removeAspect();
        eater.damage(world.getDamageSources().magic(), eater.getHealth() + 1 - (Victus.ASPECTS.get(eater).effectiveSize() + 1) * 2);

        VictusParticleEvents.HEART_PARTICLES.spawn(world, eater.getPos(), true);
    }
}
