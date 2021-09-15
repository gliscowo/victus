package com.glisco.victus.mixin;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.HeartAspectComponent;
import com.glisco.victus.hearts.content.PotionAspect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionItem.class)
public class PotionItemMixin {

    @Inject(method = "finishUsing", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isClient:Z"), cancellable = true)
    private void onConsume(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        PlayerEntity player = user instanceof PlayerEntity ? (PlayerEntity) user : null;
        if (player == null) return;

        var potion = PotionUtil.getPotion(stack);
        if (potion == Potions.EMPTY || potion.getEffects().isEmpty()) return;

        final var potionAspect = findFirstEmptyPotionAspect(Victus.ASPECTS.get(player));
        if (potionAspect == null) return;

        potionAspect.setPotion(potion);

        player.incrementStat(Stats.USED.getOrCreateStat((PotionItem) (Object) this));
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }

        if (!player.getAbilities().creativeMode) {
            if (stack.isEmpty()) {
                cir.setReturnValue(new ItemStack(Items.GLASS_BOTTLE));
            }

            player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
        }

        world.emitGameEvent(user, GameEvent.DRINKING_FINISH, user.getCameraBlockPos());
        cir.setReturnValue(stack);
    }

    @Nullable
    private static PotionAspect findFirstEmptyPotionAspect(HeartAspectComponent component) {
        for (int i = 0; i < component.effectiveSize(); i++) {
            if (component.getAspect(i) instanceof PotionAspect aspect && aspect.getPotion() == Potions.EMPTY) return aspect;
        }
        return null;
    }

}
