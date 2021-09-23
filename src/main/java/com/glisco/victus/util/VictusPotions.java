package com.glisco.victus.util;

import com.glisco.owo.registration.reflect.AutoRegistryContainer;
import com.glisco.victus.mixin.BrewingRecipeRegistryInvoker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.registry.Registry;

public class VictusPotions implements AutoRegistryContainer<Potion> {

    public static final Potion HEARTBLEED = new Potion(new StatusEffectInstance(VictusStatusEffects.HEARTBLEED, 400));

    @Override
    public void afterFieldProcessing() {
        BrewingRecipeRegistryInvoker.victus_register(Potions.AWKWARD, Items.REDSTONE, HEARTBLEED);
    }

    @Override
    public Registry<Potion> getRegistry() {
        return Registry.POTION;
    }

    @Override
    public Class<Potion> getTargetFieldType() {
        return Potion.class;
    }
}
