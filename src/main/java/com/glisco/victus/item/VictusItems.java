package com.glisco.victus.item;

import com.glisco.owo.registration.reflect.ItemRegistryContainer;
import com.glisco.victus.hearts.content.*;
import net.minecraft.item.Item;

public class VictusItems implements ItemRegistryContainer {

    public static final Item GRILLED_HEART_ASPECT = new HeartAspectItem(GrilledAspect.TYPE);
    public static final Item BUNDLE_HEART_ASPECT = new HeartAspectItem(BundleAspect.TYPE);
    public static final Item CREEPER_HEART_ASPECT = new HeartAspectItem(CreeperAspect.TYPE);
    public static final Item DIAMOND_HEART_ASPECT = new HeartAspectItem(DiamondAspect.TYPE);
    public static final Item LIGHT_HEART_ASPECT = new HeartAspectItem(LightAspect.TYPE);
    public static final Item OCEAN_HEART_ASPECT = new HeartAspectItem(OceanAspect.TYPE);
    public static final Item TOTEM_HEART_ASPECT = new HeartAspectItem(TotemAspect.TYPE);
    public static final Item POTION_HEART_ASPECT = new HeartAspectItem(PotionAspect.TYPE);
    public static final Item ARCHERY_HEART_ASPECT = new HeartAspectItem(ArcheryAspect.TYPE);
    public static final Item BLAZING_HEART_ASPECT = new HeartAspectItem(BlazingAspect.TYPE);
    public static final Item DRACONIC_HEART_ASPECT = new HeartAspectItem(DraconicAspect.TYPE);
    public static final Item EMERALD_HEART_ASPECT = new HeartAspectItem(EmeraldAspect.TYPE);
    public static final Item EVOKING_HEART_ASPECT = new HeartAspectItem(EvokingAspect.TYPE);
    public static final Item GOLDEN_HEART_ASPECT = new HeartAspectItem(GoldenAspect.TYPE);
    public static final Item ICY_HEART_ASPECT = new HeartAspectItem(IcyAspect.TYPE);
    public static final Item IRON_HEART_ASPECT = new HeartAspectItem(IronAspect.TYPE);
    public static final Item LAPIS_HEART_ASPECT = new HeartAspectItem(LapisAspect.TYPE);
    public static final Item SWEET_HEART_ASPECT = new HeartAspectItem(SweetAspect.TYPE);

    public static final Item VOID_HEART_ASPECT = new VoidAspectItem();
    public static final Item BROKEN_HEART = new BrokenHeartItem();
    public static final Item BLANK_HEART_ASPECT = new BlankAspectItem();

}
