package com.glisco.victus.mixin;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspect;
import com.glisco.victus.hearts.HeartAspectComponent;
import com.glisco.victus.hearts.content.LapisAspect;
import com.glisco.victus.network.VictusPackets;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin {

    @Shadow private int amount;

    @Inject(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;chooseEquipmentWith(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/LivingEntity;Ljava/util/function/Predicate;)Ljava/util/Map$Entry;"), cancellable = true)
    private void healIfAspectPresent(PlayerEntity player, CallbackInfo ci) {
        if (!(player instanceof ServerPlayerEntity)) return;
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

        if (serverPlayer.getMaxHealth() - serverPlayer.getHealth() < 1f) return;
        if (this.amount < 3) return;

        final HeartAspectComponent aspects = Victus.ASPECTS.get(player);
        if (!aspects.hasAspect(LapisAspect.TYPE, HeartAspect.IS_ACTIVE)) return;

        final int lapisIndex = aspects.findFirstIndex(LapisAspect.TYPE, HeartAspect.IS_ACTIVE);
        aspects.getAspect(lapisIndex).onBroken(DamageSource.OUT_OF_WORLD, 0, player.getHealth());
        VictusPackets.sendAspectBreak(serverPlayer, lapisIndex, false);

        player.heal(1);

        if (amount <= 3) this.amount = 0;
        else this.amount -= 3;
    }

}
