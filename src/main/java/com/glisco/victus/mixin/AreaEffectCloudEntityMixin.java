package com.glisco.victus.mixin;

import com.glisco.victus.util.VictusAreaEffectCloudExtension;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(AreaEffectCloudEntity.class)
public class AreaEffectCloudEntityMixin implements VictusAreaEffectCloudExtension {

    @Shadow
    @Nullable
    private LivingEntity owner;

    @Unique
    private boolean ignoreOwner = false;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD)
    private void removeOwner(CallbackInfo ci, boolean bl, float f, List<StatusEffectInstance> list, List<Entity> entities) {
        if (!this.ignoreOwner) return;
        entities.remove(owner);
    }

    @Override
    public void markIgnoreOwner() {
        this.ignoreOwner = true;
    }
}
