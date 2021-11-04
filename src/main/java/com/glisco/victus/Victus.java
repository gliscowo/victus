package com.glisco.victus;

import com.glisco.victus.hearts.HeartAspectComponent;
import com.glisco.victus.hearts.HeartAspectRegistry;
import com.glisco.victus.item.VictusItems;
import com.glisco.victus.network.VictusPackets;
import com.glisco.victus.util.EntityFlagComponent;
import com.glisco.victus.util.VictusPotions;
import com.glisco.victus.util.VictusStatusEffects;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetNbtLootFunction;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Victus implements ModInitializer, EntityComponentInitializer {

    private static final Logger LOGGER = LogManager.getLogger("Victus");

    public static final String MOD_ID = "victus";

    public static final ComponentKey<HeartAspectComponent> ASPECTS = ComponentRegistry.getOrCreate(id("aspects"), HeartAspectComponent.class);
    public static final ComponentKey<EntityFlagComponent> ENTITY_FLAGS = ComponentRegistry.getOrCreate(id("flags"), EntityFlagComponent.class);

    public static final ItemGroup VICTUS_GROUP = FabricItemGroupBuilder.build(id("victus"), () -> new ItemStack(VictusItems.VOID_HEART_ASPECT));

    @Override
    public void onInitialize() {
        HeartAspectRegistry.registerDefaults();

        VictusItems.register();
        VictusStatusEffects.register();
        VictusPotions.register();

        VictusPackets.registerServerListeners();

        LootTableLoadingCallback.EVENT.register((resourceManager, manager, id, supplier, setter) -> {
            if (LootTables.BURIED_TREASURE_CHEST.equals(id)) {
                supplier.withPool(LootPool.builder()
                        .with(ItemEntry.builder(VictusItems.BROKEN_HEART).conditionally(RandomChanceLootCondition.builder(.75f)))
                        .build());
            } else if ( LootTables.SIMPLE_DUNGEON_CHEST.equals(id) || LootTables.ABANDONED_MINESHAFT_CHEST.equals(id)) {
                supplier.withPool(LootPool.builder()
                        .with(ItemEntry.builder(Items.SPLASH_POTION)
                                .apply(SetNbtLootFunction.builder(VictusPotions.HEARTBLEED_NBT))
                                .conditionally(RandomChanceLootCondition.builder(.6f)))
                        .build());
            }
        });
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
