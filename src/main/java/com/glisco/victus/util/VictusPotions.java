package com.glisco.victus.util;

import com.glisco.owo.registration.reflect.AutoRegistryContainer;
import com.glisco.victus.mixin.BrewingRecipeRegistryInvoker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.registry.Registry;

public class VictusPotions implements AutoRegistryContainer<Potion> {

    public static final NbtCompound HEARTBLEED_POTION_NBT;

    public static final Potion HEARTBLEED = new Potion(new StatusEffectInstance(VictusStatusEffects.HEARTBLEED, 400));
    public static final Potion RESURGENCE = new Potion(new StatusEffectInstance(VictusStatusEffects.RESURGENCE, 400));
    public static final Potion LONG_RESURGENCE = new Potion(new StatusEffectInstance(VictusStatusEffects.RESURGENCE, 800));

    @Override
    public void afterFieldProcessing() {
        BrewingRecipeRegistryInvoker.victus_register(Potions.HARMING, Items.GLOW_LICHEN, HEARTBLEED);
        BrewingRecipeRegistryInvoker.victus_register(Potions.REGENERATION, Items.MAGMA_CREAM, RESURGENCE);
        BrewingRecipeRegistryInvoker.victus_register(RESURGENCE, Items.REDSTONE, LONG_RESURGENCE);
    }

    static {
        HEARTBLEED_POTION_NBT = new NbtCompound();
        HEARTBLEED_POTION_NBT.putString("Potion", "victus:heartbleed");
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
