package com.glisco.victus.item;

import com.glisco.owo.registration.AutoRegistryContainer;
import com.glisco.victus.Victus;
import com.glisco.victus.hearts.content.*;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class VictusItems implements AutoRegistryContainer<Item> {

    public static final Item BLANK_HEART_ASPECT = new Item(new Item.Settings().group(Victus.VICTUS_GROUP));

    public static final Item GRILLED_HEART_ASPECT = new HeartAspectItem(GrilledAspect.TYPE);
    public static final Item BUNDLE_HEART_ASPECT = new HeartAspectItem(BundleAspect.TYPE);
    public static final Item CREEPER_HEART_ASPECT = new HeartAspectItem(CreeperAspect.TYPE);
    public static final Item DIAMOND_HEART_ASPECT = new HeartAspectItem(DiamondAspect.TYPE);
    public static final Item LIGHT_HEART_ASPECT = new HeartAspectItem(LightAspect.TYPE);
    public static final Item OCEAN_HEART_ASPECT = new HeartAspectItem(OceanAspect.TYPE);
    public static final Item TOTEM_HEART_ASPECT = new HeartAspectItem(TotemAspect.TYPE);
    public static final Item POTION_HEART_ASPECT = new HeartAspectItem(PotionAspect.TYPE);

    public static final Item BROKEN_HEART = new BrokenHeartItem();

    @Override
    public Registry<Item> getRegistry() {
        return Registry.ITEM;
    }

    @Override
    public Class<Item> getRegisteredType() {
        return Item.class;
    }
}
