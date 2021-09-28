package com.glisco.victus.item;

import com.glisco.victus.Victus;
import com.glisco.victus.network.VictusParticleEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class VoidAspectItem extends EdibleItem {

    public VoidAspectItem() {
        super(new Settings().group(Victus.VICTUS_GROUP).food(new FoodComponent.Builder().alwaysEdible().build()).maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (Victus.ASPECTS.get(user).empty()) return TypedActionResult.pass(user.getStackInHand(hand));
        return super.use(world, user, hand);
    }

    @Override
    void onEaten(ItemStack stack, World world, PlayerEntity eater) {
        Victus.ASPECTS.get(eater).removeAspect();
        eater.damage(DamageSource.MAGIC, eater.getHealth() + 1 - (Victus.ASPECTS.get(eater).effectiveSize() + 1) * 2);

        VictusParticleEvents.dispatchHeartParticles((ServerWorld) world, (ServerPlayerEntity) eater, true);
    }
}
