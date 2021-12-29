package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.UUID;

public class DiamondAspect extends HeartAspect {

    public static final Type TYPE = new Type(Victus.id("diamond"), 1, 50, 0x00D4C9, ALWAYS_UPDATE, DiamondAspect::new);

    private final Multimap<EntityAttribute, EntityAttributeModifier> modifiers;

    public DiamondAspect(PlayerEntity player) {
        super(player, TYPE);

        modifiers = HashMultimap.create(2, 1);
        modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(UUID.randomUUID(), "Diamond Heart Aspect Armor", 4, EntityAttributeModifier.Operation.ADDITION));
        modifiers.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(UUID.randomUUID(), "Diamond Heart Aspect Armor Toughness", 1, EntityAttributeModifier.Operation.ADDITION));
    }

    @Override
    protected void readCustomData(NbtCompound nbt) {
        readModifier(nbt, EntityAttributes.GENERIC_ARMOR, "Armor");
        readModifier(nbt, EntityAttributes.GENERIC_ARMOR_TOUGHNESS, "ArmorToughness");
    }

    private void readModifier(NbtCompound nbt, EntityAttribute attribute, String key) {
        if (nbt.contains(key, NbtElement.COMPOUND_TYPE)) {
            var modifierNbt = nbt.getCompound(key);
            this.modifiers.removeAll(attribute);
            this.modifiers.put(attribute, EntityAttributeModifier.fromNbt(modifierNbt));
        }
    }

    @Override
    protected void writeCustomData(NbtCompound nbt) {
        writeFirstModifier(nbt, EntityAttributes.GENERIC_ARMOR, "Armor");
        writeFirstModifier(nbt, EntityAttributes.GENERIC_ARMOR_TOUGHNESS, "ArmorToughness");
    }

    private void writeFirstModifier(NbtCompound nbt, EntityAttribute attribute, String key) {
        modifiers.get(attribute).stream().findAny().ifPresent(modifier -> {
            nbt.put(key, modifier.toNbt());
        });
    }

    @Override
    public void update() {
        player.getAttributes().addTemporaryModifiers(modifiers);
    }

    @Override
    protected boolean handleBreak(DamageSource source, float damage, float originalHealth) {
        player.getAttributes().removeModifiers(modifiers);
        return false;
    }
}
