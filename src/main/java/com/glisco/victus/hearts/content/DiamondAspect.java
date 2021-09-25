package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class DiamondAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("diamond"), 1, 10, ALWAYS_UPDATE, DiamondAspect::new);

    private static final Multimap<EntityAttribute, EntityAttributeModifier> MODIFIER;

    static {
        var builder = ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder();
        builder.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(UUID.fromString("9d8f11d1-2a1c-41d4-a6d8-d4243094f461"), "Diamond Heart Aspect Armor", 6, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(UUID.fromString("faaee684-c4ba-44c0-86e2-db836c4a3267"), "Diamond Heart Aspect Armor Toughness", 3, EntityAttributeModifier.Operation.ADDITION));
        MODIFIER = builder.build();
    }

    public DiamondAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    @Override
    public void update() {
        player.getAttributes().addTemporaryModifiers(MODIFIER);
    }

    @Override
    protected boolean handleBreak(DamageSource source, float damage, float originalHealth) {
        player.getAttributes().removeModifiers(MODIFIER);
        return false;
    }
}
