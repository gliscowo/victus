package com.glisco.victus;

import com.glisco.victus.hearts.HeartAspectComponent;
import com.glisco.victus.hearts.HeartAspectRegistry;
import com.glisco.victus.item.EdibleItem;
import com.glisco.victus.item.VictusItemGroup;
import com.glisco.victus.item.VictusItems;
import com.glisco.victus.network.VictusPackets;
import com.glisco.victus.network.VictusParticleEvents;
import com.glisco.victus.util.EntityFlagComponent;
import com.glisco.victus.util.VictusPotions;
import com.glisco.victus.util.VictusStatusEffects;
import com.mojang.brigadier.arguments.FloatArgumentType;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import io.wispforest.owo.Owo;
import io.wispforest.owo.ops.LootOps;
import io.wispforest.owo.registration.reflect.FieldRegistrationHandler;
import io.wispforest.owo.util.TagInjector;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.loot.LootTables;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Victus implements ModInitializer, EntityComponentInitializer {

    private static final Logger LOGGER = LogManager.getLogger("Victus");

    public static final String MOD_ID = "victus";

    public static final ComponentKey<HeartAspectComponent> ASPECTS = ComponentRegistry.getOrCreate(id("aspects"), HeartAspectComponent.class);
    public static final ComponentKey<EntityFlagComponent> ENTITY_FLAGS = ComponentRegistry.getOrCreate(id("flags"), EntityFlagComponent.class);

    public static final VictusItemGroup VICTUS_GROUP = new VictusItemGroup();

    @Override
    public void onInitialize() {
        HeartAspectRegistry.registerDefaults();
        FieldRegistrationHandler.register(VictusItems.class, MOD_ID, false);
        FieldRegistrationHandler.register(VictusStatusEffects.class, MOD_ID, false);
        FieldRegistrationHandler.register(VictusPotions.class, MOD_ID, false);

        FieldRegistrationHandler.process(VictusItems.class, (item, s, field) -> {
            if (!(item instanceof EdibleItem)) return;
            TagInjector.inject(Registry.ITEM, new Identifier("origins", "ignore_diet"), item);
        }, false);

        VICTUS_GROUP.initialize();

        VictusPackets.registerServerListeners();
        VictusParticleEvents.initialize();

        LootOps.injectItem(VictusItems.BROKEN_HEART, .75f, LootTables.BURIED_TREASURE_CHEST);
        LootOps.injectItemStack(VictusPotions.createHeartbleedPotion(), .6f, LootTables.SIMPLE_DUNGEON_CHEST, LootTables.ABANDONED_MINESHAFT_CHEST);

        if (!Owo.DEBUG) return;

        CommandRegistrationCallback.EVENT.register((dispatcher, access, environment) ->
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
        registry.registerForPlayers(ASPECTS, HeartAspectComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerFor(Entity.class, ENTITY_FLAGS, entity -> new EntityFlagComponent());
    }
}
