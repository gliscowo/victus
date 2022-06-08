package com.glisco.victus.util;

import com.glisco.victus.mixin.BrewingRecipeRegistryInvoker;
import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.registry.Registry;

public class VictusPotions implements AutoRegistryContainer<Potion> {

    public static final Potion HEARTBLEED = new Potion(new StatusEffectInstance(VictusStatusEffects.HEARTBLEED, 400));
    public static final Potion RESURGENCE = new Potion(new StatusEffectInstance(VictusStatusEffects.RESURGENCE, 400));
    public static final Potion LONG_RESURGENCE = new Potion(new StatusEffectInstance(VictusStatusEffects.RESURGENCE, 800));
    public static final Potion STRONG_RESURGENCE = new Potion(new StatusEffectInstance(VictusStatusEffects.RESURGENCE, 200, 1));

    @Override
    public void afterFieldProcessing() {
        BrewingRecipeRegistryInvoker.victus_register(Potions.HARMING, Items.GLOW_LICHEN, HEARTBLEED);
        BrewingRecipeRegistryInvoker.victus_register(Potions.REGENERATION, Items.MAGMA_CREAM, RESURGENCE);
        BrewingRecipeRegistryInvoker.victus_register(RESURGENCE, Items.REDSTONE, LONG_RESURGENCE);
        BrewingRecipeRegistryInvoker.victus_register(RESURGENCE, Items.GLOWSTONE, STRONG_RESURGENCE);
    }

    public static ItemStack createHeartbleedPotion() {
        var stack = new ItemStack(Items.SPLASH_POTION);
        PotionUtil.setPotion(stack, HEARTBLEED);
        return stack;
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
