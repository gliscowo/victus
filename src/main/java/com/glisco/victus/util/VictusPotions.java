package com.glisco.victus.util;

import com.glisco.victus.Victus;
import com.glisco.victus.mixin.BrewingRecipeRegistryInvoker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.registry.Registry;

public class VictusPotions {

    public static final NbtCompound HEARTBLEED_NBT;

    public static final Potion HEARTBLEED = new Potion(new StatusEffectInstance(VictusStatusEffects.HEARTBLEED, 400));
    public static final Potion RESURGENCE = new Potion(new StatusEffectInstance(VictusStatusEffects.RESURGENCE, 400));
    public static final Potion LONG_RESURGENCE = new Potion(new StatusEffectInstance(VictusStatusEffects.RESURGENCE, 800));
    public static final Potion STRONG_RESURGENCE = new Potion(new StatusEffectInstance(VictusStatusEffects.RESURGENCE, 200, 1));

    public void afterFieldProcessing() {
        BrewingRecipeRegistryInvoker.victus_register(Potions.HARMING, Items.VINE, HEARTBLEED);
        BrewingRecipeRegistryInvoker.victus_register(Potions.REGENERATION, Items.MAGMA_CREAM, RESURGENCE);
        BrewingRecipeRegistryInvoker.victus_register(RESURGENCE, Items.REDSTONE, LONG_RESURGENCE);
        BrewingRecipeRegistryInvoker.victus_register(RESURGENCE, Items.GLOWSTONE, STRONG_RESURGENCE);
    }

    public static void register(){
        Registry.register(Registry.POTION, Victus.id("heartbleed"), HEARTBLEED);
        Registry.register(Registry.POTION, Victus.id("resurgence"), RESURGENCE);
        Registry.register(Registry.POTION, Victus.id("long_resurgence"), LONG_RESURGENCE);
        Registry.register(Registry.POTION, Victus.id("strong_resurgence"), STRONG_RESURGENCE);
    }

    static {
        HEARTBLEED_NBT = new NbtCompound();
        HEARTBLEED_NBT.putString("Potion", "victus:heartbleed");
    }
}
