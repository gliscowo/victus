package com.glisco.victus.mixin;

import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpellcastingIllagerEntity.class)
public interface SpellcastingIllagerEntityAccessor {

    @Accessor("spellTicks")
    void victus_setSpellTicks(int spellTicks);

    @Invoker("getCastSpellSound")
    SoundEvent victus_invokeGetCastSpellSound();

}
