package com.glisco.victus;

import com.glisco.owo.registration.AutoRegistryContainer;
import com.glisco.victus.hearts.HeartAspectComponent;
import com.glisco.victus.hearts.HeartAspectRegistry;
import com.glisco.victus.item.VictusItems;
import com.glisco.victus.util.VictusPotions;
import com.glisco.victus.util.VictusStatusEffects;
import com.mojang.brigadier.arguments.FloatArgumentType;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Victus implements ModInitializer, EntityComponentInitializer {

    private static final Logger LOGGER = LogManager.getLogger("Victus");

    public static final String MOD_ID = "victus";
    public static final ComponentKey<HeartAspectComponent> ASPECTS = ComponentRegistry.getOrCreate(id("aspects"), HeartAspectComponent.class);
    public static final ItemGroup VICTUS_GROUP = FabricItemGroupBuilder.build(id("victus"), () -> new ItemStack(VictusItems.TOTEM_HEART_ASPECT));

    @Override
    public void onInitialize() {
        HeartAspectRegistry.registerDefaults();
        AutoRegistryContainer.register(VictusItems.class, MOD_ID);
        AutoRegistryContainer.register(VictusStatusEffects.class, MOD_ID);
        AutoRegistryContainer.register(VictusPotions.class, MOD_ID);

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) ->
                dispatcher.register(CommandManager.literal("damage").then(CommandManager.argument("amount", FloatArgumentType.floatArg()).executes(context -> {
                    context.getSource().getPlayer().damage(DamageSource.OUT_OF_WORLD, FloatArgumentType.getFloat(context, "amount"));
                    return 0;
                }))));
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(ASPECTS, HeartAspectComponent::new, RespawnCopyStrategy.NEVER_COPY);
    }
}
