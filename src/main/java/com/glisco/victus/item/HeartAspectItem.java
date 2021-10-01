package com.glisco.victus.item;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.network.VictusParticleEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeartAspectItem extends EdibleItem {

    private static final Map<HeartAspect.Type, HeartAspectItem> HEART_ASPECT_ITEMS = new HashMap<>();

    private final HeartAspect.Type aspectType;

    public HeartAspectItem(HeartAspect.Type aspectType) {
        super(new Settings().group(Victus.VICTUS_GROUP).food(new FoodComponent.Builder().alwaysEdible().hunger(4).saturationModifier(.5f).build()).rarity(Rarity.UNCOMMON).maxCount(1));
        this.aspectType = aspectType;

        HeartAspectItem.HEART_ASPECT_ITEMS.put(aspectType, this);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        final var playerStack = user.getStackInHand(hand);
        final var aspectComponent = Victus.ASPECTS.get(user);

        if (!aspectComponent.acceptsNew()) return TypedActionResult.pass(playerStack);

        user.setCurrentHand(hand);
        return TypedActionResult.success(playerStack);
    }

    @Override
    void onEaten(ItemStack stack, World world, PlayerEntity eater) {
        final var aspectComponent = Victus.ASPECTS.get(eater);
        aspectComponent.addAspect(aspectType.factory().apply(eater));

        VictusParticleEvents.dispatchHeartParticles((ServerWorld) world, (ServerPlayerEntity) eater, false);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText(this.getTranslationKey(stack) + ".description").formatted(Formatting.DARK_GRAY));
        tooltip.add(Text.of(""));
        tooltip.add(new TranslatableText("text.victus.recharge_duration", aspectType.standardRechargeDuration() / 20f).formatted(Formatting.BLUE));
    }

    public static HeartAspectItem getItem(HeartAspect.Type type) {
        return HeartAspectItem.HEART_ASPECT_ITEMS.get(type);
    }
}
