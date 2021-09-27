package com.glisco.victus.mixin;

import com.glisco.victus.Victus;
import com.glisco.victus.hearts.content.DraconicAspect;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(AreaEffectCloudEntity.class)
public class AreaEffectCloudEntityMixin {

    @Shadow
    @Nullable
    private LivingEntity owner;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD)
    private void removeOwner(CallbackInfo ci, boolean bl, float f, List<StatusEffectInstance> list, List<Entity> entities) {
        if (!Victus.ENTITY_FLAGS.get(this).flagSet(DraconicAspect.IGNORE_OWNER_FLAG)) return;
        entities.remove(owner);
    }
}
