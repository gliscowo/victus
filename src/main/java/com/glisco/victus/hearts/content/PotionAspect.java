package com.glisco.victus.hearts.content;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.hearts.OverlaySpriteProvider;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;

public class PotionAspect extends HeartAspect implements OverlaySpriteProvider {

    public static final Type TYPE = new Type(Victus.id("potion"), 7, 20, 0xFFFFFF, PotionAspect::new);

    private Potion potion = Potions.EMPTY;

    public PotionAspect(PlayerEntity player) {
        super(player, TYPE);
    }

    public Potion getPotion() {
        return potion;
    }

    public void setPotion(Potion potion) {
        this.potion = potion;
        Victus.ASPECTS.sync(player);
    }

    @Override
    public boolean handleBreak(DamageSource source, float damage, float originalHealth) {
        List<StatusEffectInstance> list = potion.getEffects();

        for (StatusEffectInstance statusEffectInstance : list) {
            if (statusEffectInstance.getEffectType().isInstant()) {
                statusEffectInstance.getEffectType().applyInstantEffect(player, player, player, statusEffectInstance.getAmplifier(), 1.0D);
            } else {
                player.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
            }
        }

        this.setPotion(Potions.EMPTY);
        return false;
    }

    @Override
    protected void readCustomData(NbtCompound nbt) {
        this.potion = Registries.POTION.get(new Identifier(nbt.getString("Potion")));
    }

    @Override
    protected void writeCustomData(NbtCompound nbt) {
        nbt.putString("Potion", Registries.POTION.getId(this.potion).toString());
    }

    @Override
    public int getOverlayIndex() {
        return 8;
    }

    @Override
    public int getOverlayTint() {
        return potion == Potions.EMPTY ? 0xFFFFFF : PotionUtil.getColor(potion);
    }
}
