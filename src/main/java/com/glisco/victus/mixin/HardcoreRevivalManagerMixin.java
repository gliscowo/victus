package com.glisco.victus.mixin;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.hearts.content.TotemAspect;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.blay09.mods.hardcorerevival.HardcoreRevivalManager", remap = false)
public class HardcoreRevivalManagerMixin {

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "knockout", at = @At("HEAD"), cancellable = true)
    private void dontKnockout(PlayerEntity player, DamageSource source, CallbackInfo info) {
        if (!Victus.ASPECTS.get(player).hasAspect(TotemAspect.TYPE, HeartAspect::active)) return;
        info.cancel();
    }

}
